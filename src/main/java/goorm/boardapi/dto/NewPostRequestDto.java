package goorm.boardapi.dto;


import lombok.Data;


@Data
public class NewPostRequestDto {

    private String title;
    private String content;

}
