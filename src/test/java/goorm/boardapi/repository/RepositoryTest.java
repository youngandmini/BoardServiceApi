package goorm.boardapi.repository;

import goorm.boardapi.domain.Comment;
import goorm.boardapi.domain.Post;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class RepositoryTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    EntityManager em;
    @Autowired
    CommentRepository commentRepository;

    @Test
    void postCommentTest() {
        Post post = new Post("테스트용", "테스트용컨텐츠");
        postRepository.save(post);
        Comment comment = new Comment("텍스트", post);
        commentRepository.save(comment);

        em.flush();
        em.clear();

        Post findPost = postRepository.findById(post.getId()).get();
        Comment findComment = commentRepository.findById(comment.getId()).get();
        findComment.changeText("텍스트 수정됨");

        assertThat(findPost.getComments().get(0) == findComment).isTrue();
        assertThat(findPost.getComments().get(0).getText()).isEqualTo("텍스트 수정됨");
    }


}
