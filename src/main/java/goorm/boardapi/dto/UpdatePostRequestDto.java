package goorm.boardapi.dto;


import lombok.Data;

@Data
public class UpdatePostRequestDto {

    private Long id;
    private String title;
    private String content;

}
