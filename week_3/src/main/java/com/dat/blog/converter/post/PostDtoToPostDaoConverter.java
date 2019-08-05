package com.dat.blog.converter.post;

import com.dat.blog.converter.base.Converter;
import com.dat.blog.dto.PostDTO;
import com.dat.blog.exceptions.BadRequestException;
import com.dat.blog.models.Comment;
import com.dat.blog.models.Post;
import com.dat.blog.models.Tag;
import com.dat.blog.repositories.CommentRepository;
import com.dat.blog.repositories.TagRepository;
import com.dat.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostDtoToPostDaoConverter extends Converter<PostDTO, Post> {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Post convert(PostDTO source) {
        Post post = new Post();

        post.setId(source.getId());
        post.setUser(userRepository.findById(source.getUserId()).get());

        List<Tag> tags = new ArrayList<>();

        for (int id : source.getTagsId()) {
            if (id > 0 && tagRepository.findById(id).isPresent()) tags.add(tagRepository.findById(id).get());
            else throw new BadRequestException("Invalid tag id " + id);
        }

        post.setTags(tags);

//        List<Comment> comments = new ArrayList<>();
//
//        for (int id : source.getCommentsId()) {
//            if (id > 0 && commentRepository.findById(id).isPresent())
//                comments.add(commentRepository.findById(id).get());
//            else throw new BadRequestException("Invalid tag id " + id);
//        }
//
//        post.setComments(comments);

        return post;
    }
}
