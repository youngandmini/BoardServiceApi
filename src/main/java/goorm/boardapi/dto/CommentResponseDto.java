package goorm.boardapi.dto;


import goorm.boardapi.domain.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {

    private Long commentId;
    private String text;
    private LocalDateTime createdDate;

    public static CommentResponseDto toDto(Comment comment) {
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setCommentId(comment.getId());
        commentResponseDto.setText(comment.getText());
        commentResponseDto.setCreatedDate(comment.getCreatedDate());
        return commentResponseDto;
    }
}
