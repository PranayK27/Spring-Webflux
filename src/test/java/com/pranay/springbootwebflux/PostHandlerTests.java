package com.pranay.springbootwebflux;

import com.pranay.springbootwebflux.dto.PostDto;
import com.pranay.springbootwebflux.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostHandlerTests {

    @Autowired
    private WebTestClient webTestClient;

    //@MockBean
    @Autowired
    private PostService postService;

    @Test
    public void testCreatePost() throws Exception {

        PostDto post = new PostDto();
        post.setTitle("Blog Post 1");
        post.setDescription("Blog Post 1 Description");
        post.setBody("Blog Post 1 Body");

        // when - action or behaviour that we are going test
        webTestClient.post().uri("/api/posts/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(post), PostDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.title").isEqualTo(post.getTitle())
                .jsonPath("$.description").isEqualTo(post.getDescription())
                .jsonPath("$.body").isEqualTo(post.getBody());
    }

    @Test
    public void testGetAllPosts() {
        webTestClient.get().uri("/api/posts")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(PostDto.class)
                .consumeWith(System.out::println);
    }

    @Test
    public void testUpdateEmployee() throws Exception {

        PostDto post = new PostDto();
        post.setTitle("Blog Post 1");
        post.setDescription("Blog Post 1 Description");
        post.setBody("Blog Post 1 Body");

        PostDto updatedPost = new PostDto();
        updatedPost.setTitle("Blog Post 1 updated");
        updatedPost.setDescription("Blog Post 1 Description updated");
        updatedPost.setBody("Blog Post 1 Body updated");

        PostDto savedPost = postService.save(post).block();

        webTestClient.put()
                .uri("api/posts/{id}", Collections.singletonMap("id", savedPost.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(updatedPost), PostDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.title").isEqualTo(updatedPost.getTitle())
                .jsonPath("$.description").isEqualTo(updatedPost.getDescription())
                .jsonPath("$.body").isEqualTo(updatedPost.getBody());
    }

    @Test
    public void testDeletePost() {
        PostDto post = new PostDto();
        post.setTitle("Blog Post 2");
        post.setDescription("Blog Post 2 Description");
        post.setBody("Blog Post 2 Body");

        PostDto savedPost = postService.save(post).block();

        webTestClient.delete()
                .uri("/api/posts/{id}", Collections.singletonMap("id",  savedPost.getId()))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);
    }
}
