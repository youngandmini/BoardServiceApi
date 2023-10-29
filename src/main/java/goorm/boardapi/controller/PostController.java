package goorm.boardapi.controller;


import goorm.boardapi.dto.*;
import goorm.boardapi.exception.BadRequestException;
import goorm.boardapi.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    /**
     * 서버 에러에 대한 500
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String serverError(Exception e) {
        log.error("INTERNAL_SERVER_ERROR: ", e);
        return "서버에서 에러가 발생하였습니다. 잠시 후 다시 시도해주세요.";
    }

    /**
     * 잘못된 요청에 대한 400
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageConversionException.class)
    public String badJson(Exception e) {
        log.info("BAD_JSON: ", e);
        return "입력 값을 확인해주세요.";
    }

    /**
     * 잘못된 요청에 대한 400
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public String badRequest(Exception e) {
        log.info("BAD_REQUEST: ", e);
        return e.getMessage();
    }

    /**
     * 잘못된 URL에 대한 404
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String badUri(HttpServletRequest request) {
        log.info("request URL: [{}] {}", request.getMethod(), request.getRequestURL());
        return "존재하지 않는 요청입니다.";
    }

    /**
     * 잘못된 URL에 대한 404
     */
    @RequestMapping("/**")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(HttpServletRequest request) {
        log.info("request URL: [{}] {}", request.getMethod(), request.getRequestURL());
        return "존재하지 않는 요청입니다.";
    }


    @PostMapping("/new")
    public PostResponseDto savePost(@RequestBody NewPostRequestDto requestDto) {
        PostResponseDto responseDto = postService.savePost(requestDto);
        return responseDto;
    }

    @PatchMapping("/{postId}")
    public PostResponseDto updatePost(@PathVariable("postId") Long postId, @RequestBody UpdatePostRequestDto requestDto) {
        requestDto.setId(postId);
        PostResponseDto responseDto = postService.updatePost(requestDto);
        return responseDto;
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return "ok";
    }

    @GetMapping("/{postId}")
    public PostResponseDto findPost(@PathVariable("postId") Long postId) {
        PostResponseDto responseDto = postService.findPost(postId);
        return responseDto;
    }

    @GetMapping
    public Page<PostsResponseDto> findPosts(@RequestParam int page, @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PostsResponseDto> responseDtos = postService.findPosts(pageRequest);
        return responseDtos;
    }

    @PostMapping("/{postId}/comments/new")
    public PostResponseDto saveComment(@PathVariable("postId") Long postId, @RequestBody NewCommentRequestDto requestDto) {
        requestDto.setPostId(postId);
        PostResponseDto responseDto = postService.saveComment(requestDto);
        return responseDto;
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    public PostResponseDto updateComment(@PathVariable("postId") Long postId,
                                         @PathVariable("commentId") Long commentId,
                                         @RequestBody UpdateCommentRequestDto requestDto) {
        requestDto.setPostId(postId);
        requestDto.setCommentId(commentId);
        PostResponseDto responseDto = postService.updateComment(requestDto);
        return responseDto;
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public PostResponseDto updateComment(@PathVariable("postId") Long postId,
                                         @PathVariable("commentId") Long commentId) {
        PostResponseDto responseDto = postService.deleteComment(postId, commentId);
        return responseDto;
    }

}
