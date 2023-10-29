package goorm.boardapi.dto;

import lombok.Data;

@Data
public class NewCommentRequestDto {

    private Long postId;
    private String text;

}
