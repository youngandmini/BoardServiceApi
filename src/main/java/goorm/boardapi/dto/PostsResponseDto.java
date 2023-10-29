package goorm.boardapi.dto;


import goorm.boardapi.domain.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostsResponseDto {
    private Long id;
    private String title;
    private LocalDateTime createdDate;

    public static PostsResponseDto ToDto(Post post) {
        PostsResponseDto postsResponseDto = new PostsResponseDto();
        postsResponseDto.setId(post.getId());
        postsResponseDto.setTitle(post.getTitle());
        postsResponseDto.setCreatedDate(post.getCreatedDate());
        return postsResponseDto;
    }
}
