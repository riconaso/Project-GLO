package com.corso.ProjectGLO.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {

    private Long postId;
    private String subRedditName;
    private String postName;
    private String url;
    private String description;
}
