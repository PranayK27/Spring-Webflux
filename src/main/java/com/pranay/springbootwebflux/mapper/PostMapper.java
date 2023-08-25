package com.pranay.springbootwebflux.mapper;

import org.springframework.stereotype.Component;
import com.pranay.springbootwebflux.dto.PostDto;
import com.pranay.springbootwebflux.entity.Post;

@Component
public class PostMapper {

    public Post mapToPost(PostDto postInput) {
        return Post.builder()
                .title(postInput.getTitle())
                .description(postInput.getDescription())
                .body(postInput.getBody())
                .build();
    }

    public PostDto mapToPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .body(post.getBody())
                .build();
    }
}
