package goorm.boardapi.repository;

import goorm.boardapi.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    @Modifying
    @Query("update Comment c set c.status = goorm.boardapi.domain.CommentStatus.DELETED where c.post.id=:postId")
    int bulkDeleteComment(@Param("postId") Long postId);

}
