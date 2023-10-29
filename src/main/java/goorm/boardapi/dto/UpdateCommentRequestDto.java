package goorm.boardapi.dto;


import lombok.Data;

@Data
public class UpdateCommentRequestDto {

    private Long postId;
    private Long commentId;
    private String text;
}
