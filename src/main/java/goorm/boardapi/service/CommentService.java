package goorm.boardapi.service;


import goorm.boardapi.domain.Comment;
import goorm.boardapi.domain.CommentStatus;
import goorm.boardapi.domain.Post;
import goorm.boardapi.domain.PostStatus;
import goorm.boardapi.dto.NewCommentRequestDto;
import goorm.boardapi.dto.PostResponseDto;
import goorm.boardapi.dto.UpdateCommentRequestDto;
import goorm.boardapi.repository.CommentRepository;
import goorm.boardapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


/**
 * PostService와 통합됨
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostResponseDto saveComment(NewCommentRequestDto requestDto) {
        Post findPost = postRepository.findByIdWithComment(requestDto.getPostId()).orElseThrow(() -> new RuntimeException("잘못된 댓글 요청"));
        if (findPost.getStatus() == PostStatus.DELETED) {
            throw new RuntimeException("잘못된 댓글 요청");
        }
        commentRepository.save(new Comment(requestDto.getText(), findPost));
        return PostResponseDto.toDto(findPost);
    }

    public PostResponseDto updateComment(UpdateCommentRequestDto requestDto) {
        Post findPost = postRepository.findByIdWithComment(requestDto.getPostId()).orElseThrow(() -> new RuntimeException("잘못된 댓글 수정 요청"));
        if (findPost.getStatus() == PostStatus.DELETED) {
            throw new RuntimeException("잘못된 댓글 수정 요청");
        }
        Comment targetComment = commentRepository.findById(requestDto.getCommentId()).orElseThrow(() -> new RuntimeException("잘못된 댓글 수정 요청"));
        if (targetComment.getStatus() == CommentStatus.DELETED) {
            throw new RuntimeException("잘못된 댓글 수정 요청");
        }

        targetComment.changeText(requestDto.getText());
        return PostResponseDto.toDto(findPost);
    }

    public PostResponseDto deleteComment(Long postId, Long commentId) {
        Post findPost = postRepository.findByIdWithComment(postId).orElseThrow(() -> new RuntimeException("잘못된 댓글 삭제 요청"));
        if (findPost.getStatus() == PostStatus.DELETED) {
            throw new RuntimeException("잘못된 댓글 삭제 요청");
        }
        Comment targetComment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("잘못된 댓글 삭제 요청"));
        if (targetComment.getStatus() == CommentStatus.DELETED) {
            throw new RuntimeException("잘못된 댓글 삭제 요청");
        }
        targetComment.deleteComment();
        return PostResponseDto.toDto(findPost);
    }

}
