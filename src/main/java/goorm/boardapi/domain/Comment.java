package goorm.boardapi.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseEntity {

    public Comment(String text, Post post) {
        this.text = text;
        this.post = post;
        this.status = CommentStatus.COMMENTED;
        post.getComments().add(this);
    }

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    public void deleteComment() {
        this.status = CommentStatus.DELETED;
    }

    @Version
    Integer version;

    public void changeText(String text) {
        this.text = text;
    }
}