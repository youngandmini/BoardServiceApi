package goorm.boardapi.repository;


import goorm.boardapi.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p left join fetch p.comments c where p.id=:id")   //
    Optional<Post> findByIdWithComment(@Param("id") Long id);


    @Query("select p from Post p where p.status = goorm.boardapi.domain.PostStatus.POSTED order by p.createdDate desc")
    Page<Post> findPostedPosts(Pageable pageable);
}
