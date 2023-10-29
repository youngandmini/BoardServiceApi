package goorm.boardapi.repository;

import goorm.boardapi.domain.Post;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    EntityManager em;

    @Test
    void findByIdWithComment() {
        Post post = new Post("","");
        em.persist(post);
        System.out.println(post.getId());
        em.flush();
        em.clear();

        Optional<Post> findPost = postRepository.findByIdWithComment(post.getId());
        Assertions.assertThat(findPost.isPresent()).isTrue();
    }
}