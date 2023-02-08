package dgsw.hackathon.DaeItSo.domain.comment.domain.entity;

import dgsw.hackathon.DaeItSo.domain.comment.domain.dto.CommentUpdateDto;
import dgsw.hackathon.DaeItSo.domain.comment.domain.enums.UpdateStatus;
import dgsw.hackathon.DaeItSo.domain.post.domain.entity.Post;
import dgsw.hackathon.DaeItSo.domain.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UpdateStatus updateStatus;

    @Builder
    public Comment(User user, Post post, String content, UpdateStatus updateStatus) {
        this.user = user;
        this.post = post;
        this.content = content;
        this.updateStatus = UpdateStatus.NOT_UPDATE;
    }

    public void update(CommentUpdateDto commentUpdateDto) {
        this.content = commentUpdateDto.getContent();
        this.updateStatus = UpdateStatus.UPDATE;
    }
}
