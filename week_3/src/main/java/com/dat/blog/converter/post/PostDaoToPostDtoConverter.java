package com.dat.blog.converter.post;

import com.dat.blog.converter.base.Converter;
import com.dat.blog.dto.PostDTO;
import com.dat.blog.models.Comment;
import com.dat.blog.models.Post;
import com.dat.blog.models.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostDaoToPostDtoConverter extends Converter <Post, PostDTO>{
    @Override
    public PostDTO convert(Post source){
        PostDTO postDTO = new PostDTO();

        postDTO.setId(source.getId());
        postDTO.setUserId(source.getUser().getId());

        List<Integer> tagsId = new ArrayList<>();

        for(Tag tag : source.getTags()){
            tagsId.add(tag.getId());
        }

        postDTO.setTagsId(tagsId);

//        List<Integer> commenstId = new ArrayList<>();
//
//        for(Comment comment : source.getComments()){
//            tagsId.add(comment.getId());
//        }

//        postDTO.setTagsId(commenstId);

        return postDTO;
    }

}