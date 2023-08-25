package com.pranay.springbootwebflux.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Post {
    @Id
    private String id;
    private String title;
    private String description;
    private String body;
    @Field(name = "created_on")
    private LocalDateTime createdOn;
    @Field(name = "updated_on")
    private LocalDateTime updatedOn;
}
