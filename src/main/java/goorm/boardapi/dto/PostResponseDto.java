package goorm.boardapi.dto;


import goorm.boardapi.domain.Comment;
import goorm.boardapi.domain.CommentStatus;
import goorm.boardapi.domain.Post;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private List<CommentResponseDto> comments;
    private LocalDateTime createdDate;

//    public PostResponseDto(Post post) {
//        this.id = post.getId();
//        this.title = post.getTitle();
//        this.content = post.getContent();
//        this.comments = post.getComments().stream().filter(c -> c.getStatus() == CommentStatus.COMMENTED).map(CommentResponseDto::toDto).collect(Collectors.toList());
//        this.createdDate = post.getCreatedDate();
//    }

    public static PostResponseDto toDto(Post post) {
        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setId(post.getId());
        postResponseDto.setTitle(post.getTitle());
        postResponseDto.setContent(post.getContent());
        postResponseDto.setComments(post.getComments().stream().filter(c -> c.getStatus() == CommentStatus.COMMENTED).map(CommentResponseDto::toDto).collect(Collectors.toList()));
        postResponseDto.setCreatedDate(post.getCreatedDate());
        return postResponseDto;
    }
}
