package com.pranay.springbootwebflux.service.impl;

import com.pranay.springbootwebflux.dto.PostDto;
import com.pranay.springbootwebflux.entity.Post;
import com.pranay.springbootwebflux.mapper.PostMapper;
import com.pranay.springbootwebflux.repository.PostReactiveRepository;
import com.pranay.springbootwebflux.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private PostReactiveRepository postReactiveRepository;
    private PostMapper postMapper;

    @Override
    public Mono<PostDto> save(PostDto postDto) {
        Post post = postMapper.mapToPost(postDto);
        post.setCreatedOn(LocalDateTime.now());
        post.setUpdatedOn(LocalDateTime.now());
        return postReactiveRepository.save(post).map(p -> {
                    postDto.setId(p.getId());
                    return postDto;
                }
        );
    }

    @Override
    public Flux<PostDto> findAllPosts() {
        return postReactiveRepository.findAll()
                .map(postMapper::mapToPostDto)
                .switchIfEmpty(Flux.empty());
    }

    public Boolean postExistsWithTitle(String title) {
        return postReactiveRepository.existsByTitle(title).block();
    }

    @Override
    public Mono<PostDto> update(PostDto postDto, String id) {
        return postReactiveRepository.findById(id)
                .flatMap(savedPost -> {
                    Post post = postMapper.mapToPost(postDto);
                    post.setId(savedPost.getId());
                    return postReactiveRepository.save(post);
                })
                .map(postMapper::mapToPostDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        return postReactiveRepository.deleteById(id);
    }
}
