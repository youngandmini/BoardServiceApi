package goorm.boardapi.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseEntity {

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
        this.status = PostStatus.POSTED;
    }

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @Version
    Integer version;

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void deletePost() {
        this.status = PostStatus.DELETED;
    }
}
