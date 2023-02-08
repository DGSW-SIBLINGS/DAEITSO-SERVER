package dgsw.hackathon.DaeItSo.domain.comment.domain.dto;

import dgsw.hackathon.DaeItSo.domain.comment.domain.entity.Comment;
import dgsw.hackathon.DaeItSo.domain.user.domain.StdInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private Long userId;
    private String content;
    private String userName;
    private String profileUrl;
    private StdInfo stdInfo;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;

    @Builder
    public CommentResponseDto(Comment comment) {
        this.userId = comment.getUser().getId();
        this.commentId = comment.getId();
        this.userName = comment.getUser().getName();
        this.profileUrl = comment.getUser().getProfileImage();
        this.stdInfo = comment.getUser().getStdInfo();
        this.createDateTime = comment.getCreatedDate();
        this.updateDateTime = comment.getModifiedDate();
        this.content = comment.getContent();
    }
}
