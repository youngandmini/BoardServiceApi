package goorm.boardapi.service;

import goorm.boardapi.domain.Comment;
import goorm.boardapi.domain.CommentStatus;
import goorm.boardapi.domain.Post;
import goorm.boardapi.domain.PostStatus;
import goorm.boardapi.dto.*;
import goorm.boardapi.exception.BadRequestException;
import goorm.boardapi.repository.CommentRepository;
import goorm.boardapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostResponseDto savePost(NewPostRequestDto newPostRequest) {
        Post post = requestToPost(newPostRequest);
        Post savePost = postRepository.save(post);
        return PostResponseDto.toDto(savePost);
    }

    public PostResponseDto updatePost(UpdatePostRequestDto updatePostRequest) {
        Post findPost = postRepository.findByIdWithComment(updatePostRequest.getId()).orElseThrow(() -> new BadRequestException("잘못된 수정 요청"));
        if (findPost.getStatus() == PostStatus.DELETED) {
            throw new BadRequestException("잘못된 수정 요청");
        }
        findPost.updatePost(updatePostRequest.getTitle(), updatePostRequest.getContent());
        return PostResponseDto.toDto(findPost);
    }

    public void deletePost(Long postId) {
        Post findPost = postRepository.findByIdWithComment(postId).orElseThrow(() -> new BadRequestException("잘못된 삭제 요청"));
        if (findPost.getStatus() == PostStatus.DELETED) {
            throw new BadRequestException("잘못된 삭제 요청");
        }
        findPost.deletePost();
        findPost.getComments().forEach(Comment::deleteComment);
    }

    public PostResponseDto findPost(Long postId) {
        Post findPost = postRepository.findByIdWithComment(postId).orElseThrow(() -> new BadRequestException("잘못된 삭제 요청"));
        if (findPost.getStatus() == PostStatus.DELETED) {
            throw new BadRequestException("잘못된 조회 요청");
        }
        return PostResponseDto.toDto(findPost);

    }

    public PostResponseDto saveComment(NewCommentRequestDto requestDto) {
        Post findPost = postRepository.findByIdWithComment(requestDto.getPostId()).orElseThrow(() -> new BadRequestException("잘못된 댓글 요청"));
        if (findPost.getStatus() == PostStatus.DELETED) {
            throw new BadRequestException("잘못된 댓글 요청");
        }
        commentRepository.save(new Comment(requestDto.getText(), findPost));
        return PostResponseDto.toDto(findPost);
    }

    public PostResponseDto updateComment(UpdateCommentRequestDto requestDto) {
        Post findPost = postRepository.findByIdWithComment(requestDto.getPostId()).orElseThrow(() -> new BadRequestException("잘못된 댓글 수정 요청"));
        if (findPost.getStatus() == PostStatus.DELETED) {
            throw new BadRequestException("잘못된 댓글 수정 요청");
        }
        Comment targetComment = commentRepository.findById(requestDto.getCommentId()).orElseThrow(() -> new BadRequestException("잘못된 댓글 수정 요청"));
        if (targetComment.getStatus() == CommentStatus.DELETED) {
            throw new BadRequestException("잘못된 댓글 수정 요청");
        }

        targetComment.changeText(requestDto.getText());
        return PostResponseDto.toDto(findPost);
    }

    public PostResponseDto deleteComment(Long postId, Long commentId) {
        Post findPost = postRepository.findByIdWithComment(postId).orElseThrow(() -> new BadRequestException("잘못된 댓글 삭제 요청"));
        if (findPost.getStatus() == PostStatus.DELETED) {
            throw new BadRequestException("잘못된 댓글 삭제 요청");
        }
        Comment targetComment = commentRepository.findById(commentId).orElseThrow(() -> new BadRequestException("잘못된 댓글 삭제 요청"));
        if (targetComment.getStatus() == CommentStatus.DELETED) {
            throw new BadRequestException("잘못된 댓글 삭제 요청");
        }
        targetComment.deleteComment();
        return PostResponseDto.toDto(findPost);
    }

    public Page<PostsResponseDto> findPosts(Pageable pageable) {
        Page<Post> findPosts = postRepository.findPostedPosts(pageable);
        return findPosts.map(PostsResponseDto::ToDto);
    }

    private Post requestToPost(NewPostRequestDto newPostRequest) {
        return new Post(newPostRequest.getTitle(),newPostRequest.getContent());
    }
}
