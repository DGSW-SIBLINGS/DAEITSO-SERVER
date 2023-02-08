package dgsw.hackathon.DaeItSo.domain.comment.domain.dto;

import dgsw.hackathon.DaeItSo.domain.comment.domain.entity.Comment;
import dgsw.hackathon.DaeItSo.domain.post.domain.entity.Post;
import dgsw.hackathon.DaeItSo.domain.user.domain.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentSubmitDto {

    @NotNull
    private Long postId;
    @NotBlank
    private String content;

    public Comment toEntity(User user, Post post, CommentSubmitDto commentSubmitDto) {
        return Comment.builder()
                .user(user)
                .post(post)
                .content(commentSubmitDto.getContent())
                .build();
    }
}
