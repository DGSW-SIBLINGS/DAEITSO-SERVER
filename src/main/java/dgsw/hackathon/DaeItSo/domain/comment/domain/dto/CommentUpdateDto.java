package dgsw.hackathon.DaeItSo.domain.comment.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentUpdateDto {
    @NotNull
    private Long commentId;
    @NotBlank
    private String content;
}
