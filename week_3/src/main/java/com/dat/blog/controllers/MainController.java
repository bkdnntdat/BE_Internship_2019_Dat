package com.dat.blog.controllers;

import com.dat.blog.dao.Login;
import com.dat.blog.models.Comment;
import com.dat.blog.models.Post;
import com.dat.blog.models.Tag;
import com.dat.blog.repositories.*;
import com.dat.blog.role.User;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value = "/home")
    public String homePage(Model model, HttpSession session) {
        String username = session.getAttribute("username").toString();
        User user = userRepository.findByUsername(username);
        model.addAttribute("avt", user.getAvt());
        List<Post> posts = postRepository.findAll();

        model.addAttribute("posts", posts);
        if (user.getRole().getName().equals("ROLE_ADMIN")) return "homeadmin";
        return "home";
    }

    @RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
    public String indexPage() {
        return "login";
    }

    @RequestMapping(value = "/logining", method = RequestMethod.POST)
    public String logining(String username, String password, HttpSession session) {
        if(userRepository.findByUsername(username).getRole()==null){
            return "redirect:/login";
        }

        Login login = new Login();
        login.setUsername(username);
        login.setPassword(password);

        authenticationController.login(login);

        session.setAttribute("username", username);

        return "redirect:/home";
    }

    @RequestMapping(value = "/sendEmail", method = RequestMethod.GET)
    public void sendEmail(@RequestParam String email, String code) {

        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();

        //message.setTo(email);
        message.setTo(email);
        message.setSubject("[Mã xác nhận]");
        message.setText("Mã xác nhận của bạn là: " + code);

        // Send Message!
        emailSender.send(message);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String showSignUp() {
        return "signUp";
    }

    public boolean processing(String username, String email) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (username.equals(user.getUsername()) || email.equals(user.getEmail()) && user.getRole()!=null) {
                return false;
            }
        }
        return true;
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(String username, String password, String firstName, String lastName, String email, String phonenumber, HttpSession session, Model model) {
        if (!processing(username, email)) {
            return "404 Exception!!!";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phonenumber);

        Random random = new Random();
        String code = String.valueOf(random.nextInt(1000000));

        user.setCode(code);

        user = userRepository.save(user);

        sendEmail(email, code);

        model.addAttribute("username",username);
        model.addAttribute("password", password);

        return "confirmEmail";
    }

    @RequestMapping("/deleteUser")
    public void deleteUser(String username){
        commentRepository.deleteByUserId(userRepository.findByUsername(username).getId());
        userRepository.deleteByUsername(username);
    }

    @RequestMapping(value = "/confirmEmail", method = RequestMethod.POST)
    public String confirmEmail(@RequestParam String codeConfirm, String username, String password, HttpSession session) {
        User user = userRepository.findByUsername(username);
        if (codeConfirm.equals(user.getCode())) {
            user.setCode(null);
            user.setRole(roleRepository.findByName("ROLE_MEMBER"));
            userRepository.save(user);

            Login login = new Login();

            login.setUsername(username);
            login.setPassword(password);

            authenticationController.login(login);

            session.setAttribute("username", username);

            return "redirect:/home";
        }
        return "404 Exception!!!";
    }

    @RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
    public String forgotpassword() {
        return "forgotPassword";
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public String forgotPassword(String username, Model model) {
        User user = userRepository.findByUsername(username);
        model.addAttribute("email", user.getEmail());
        model.addAttribute("username", user.getUsername());

        Random random = new Random();
        String code = String.valueOf(random.nextInt(1000000));

        user.setCode(code);

        user = userRepository.save(user);

        sendEmail(user.getEmail(), code);

        return "confirmForgotPassword";
    }

    @RequestMapping(value = "/confirmForgotPassword", method = RequestMethod.POST)
    public String confirmForgotPassword(@RequestParam String codeConfirm, String username, Model model) {
        User user = userRepository.findByUsername(username);
        if (codeConfirm.equals(user.getCode())) {
            user.setCode(null);
            model.addAttribute("username", username);
            return "changePassword";
        }
        return "404 Exception!!!";
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String changePassword(String password, String passwordConfirm, String username, HttpSession session) {
        if (password.equals(passwordConfirm)) {
            User user = userRepository.findByUsername(username);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            userRepository.save(user);
            Login login = new Login();

            login.setUsername(username);
            login.setPassword(password);

            authenticationController.login(login);
            session.setAttribute("username", username);
            return "redirect:/home";
        }
        return "404 Exception!!!";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String loginPage(@RequestParam String username, Model model) {
        if (username != null) {
            showUser(username, model);
            model.addAttribute("posts", postRepository.findByUser(userRepository.findByUsername(username)));
            return "profile";
        } else return "redirect:/login";
    }

    public void showUser(String username, Model model) {
        User user = userRepository.findByUsername(username);

        model.addAttribute("username", "Username: " + username);
        model.addAttribute("avt", user.getAvt());
        model.addAttribute("firstname", "First name: " + user.getFirstName());
        model.addAttribute("lastname", "Last name: " + user.getLastName());
        model.addAttribute("email", "Email: " + user.getEmail());
        model.addAttribute("phone", "Phone number: " + user.getPhoneNumber());
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutSuccessfulPage(HttpSession session) {
        session.removeAttribute("username");
        return "redirect:/login";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/userManager", method = RequestMethod.GET)
    public String userInfo(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "userManager";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = loginedUser.toString();

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "403Page";
    }

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String showPost(@RequestParam int id, Model model, HttpSession session) {
        model.addAttribute("post", postRepository.findById(id).get());
        model.addAttribute("avt", userRepository.findByUsername(session.getAttribute("username").toString()).getAvt());
        return "post";
    }

    @RequestMapping(value = "/postBlog", method = RequestMethod.POST)
    public String postBlog(String title, String content, String tag, HttpSession session) {
        Post post = new Post();
        post.setUser(userRepository.findByUsername(session.getAttribute("username").toString()));
        post.setTitle(title);

        String shortContent = Jsoup.parse(content).body().text();

        if (shortContent.length() > 100)
            shortContent = shortContent.substring(0, 100);
        else
            shortContent = shortContent;

        post.setDescription(shortContent);
        post.setContent(content);
        post.setTags(addTag(tag));
        post.setTime(new Date());
        post.setTimeUpdate(post.getTime());

        postRepository.save(post);
        return "redirect:/home";
    }

    public List<Tag> addTag(String tag) {
        List<Tag> tags = new ArrayList<>();
        String[] tagsName = tag.split(",");

        for (String tagName : tagsName) {
            if (!tagRepository.findByName(tagName).isPresent()) {
                Tag tag1 = new Tag();
                tag1.setName(tagName);
                tagRepository.save(tag1);
            }
            tags.add(tagRepository.findByName(tagName).get());
        }

        return tags;
    }

    @RequestMapping(value = "/putTag", method = RequestMethod.POST)
    public String putTag(String tag, Model model) {
        if (!tagRepository.findByName(tag).isPresent()) {
            Tag tag1 = new Tag();
            tag1.setName(tag);
            tagRepository.save(tag1);
        }
        model.addAttribute("tag", tagRepository.findByName(tag));

        return "redirect:/home";
    }

    @RequestMapping(value = "/tag", method = RequestMethod.GET)
    public String findTag(@RequestParam int id, Model model, HttpSession session) {
        List<Post> posts = postRepository.findAll();

        List<Post> postList = new ArrayList<>();

        for (Post post : posts) {
            for (Tag tag : post.getTags()) {
                if (tag.getId() == id) {
                    postList.add(post);
                    break;
                }
            }
        }

        model.addAttribute("search", tagRepository.findById(id).get().getName());
        model.addAttribute("posts", postList);
        model.addAttribute("avt", userRepository.findByUsername(session.getAttribute("username").toString()).getAvt());

        return "search";
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public String addComment(String content, @RequestParam int postId, @RequestParam int userId, Model model, HttpSession session) {
        //List<Comment> comments = postRepository.findById(postId).get().getComments();
        Post post = postRepository.findById(postId).get();

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(userRepository.findByUsername(session.getAttribute("username").toString()));
        comment.setTime(new Date());
        comment = commentRepository.save(comment);
        List<Comment> comments = post.getComments();
        comments.add(comment);

        post.setComments(comments);

        postRepository.save(post);

        model.addAttribute("post", postRepository.findById(postId).get());
        model.addAttribute("avt", userRepository.findByUsername(session.getAttribute("username").toString()).getAvt());
        return "redirect:/post?id=" + postId;
    }

    @RequestMapping(value = "/deletePost", method = RequestMethod.GET)
    public String deletePost(@RequestParam int id) {
        List<Comment> comments = postRepository.findById(id).get().getComments();
        for (Comment comment : comments) {
            commentRepository.deleteById(comment.getId());
        }
        postRepository.deleteById(id);
        return "redirect:/home";
    }

    @RequestMapping(value = "/editPost", method = RequestMethod.GET)
    public String editPost(@RequestParam int id, Model model, HttpSession session) {
        model.addAttribute("post", postRepository.findById(id).get());

        String tags = "";

        for (Tag tag : postRepository.findById(id).get().getTags()) {
            tags += tag.getName() + ",";
        }

        model.addAttribute("postid", id);
        model.addAttribute("avt", userRepository.findByUsername(session.getAttribute("username").toString()).getAvt());
        model.addAttribute("tags", tags);
        return "editPost";
    }

    @RequestMapping(value = "/editPost", method = RequestMethod.POST)
    public String editPost(@RequestParam int id, String title, String content, String tag, HttpSession session) {
        Post post = postRepository.findById(id).get();
        post.setTitle(title);

        String shortContent = Jsoup.parse(content).body().text();

        if (shortContent.length() > 100)
            shortContent = shortContent.substring(0, 100);
        else
            shortContent = shortContent;

        post.setDescription(shortContent);
        post.setContent(content);
        post.setTags(addTag(tag));
        post.setTimeUpdate(new Date());

        postRepository.save(post);
        return "redirect:/post?id=" + id;
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String search(String search, Model model, HttpSession session){
        List<Post> posts = postRepository.findByContentContaining(search);
        posts.addAll(postRepository.findByTitleContaining(search));

        model.addAttribute("search",search);
        model.addAttribute("posts", posts);
        model.addAttribute("avt", userRepository.findByUsername(session.getAttribute("username").toString()).getAvt());

        return "search";
    }
}
