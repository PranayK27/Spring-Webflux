package com.pranay.springbootwebflux.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    private String id;
    private String title;
    private String description;
    private String body;
}
