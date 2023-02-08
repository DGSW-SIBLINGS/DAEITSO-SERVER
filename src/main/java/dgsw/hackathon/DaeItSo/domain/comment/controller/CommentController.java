package dgsw.hackathon.DaeItSo.domain.comment.controller;

import dgsw.hackathon.DaeItSo.domain.comment.domain.dto.CommentSubmitDto;
import dgsw.hackathon.DaeItSo.domain.comment.domain.dto.CommentUpdateDto;
import dgsw.hackathon.DaeItSo.domain.comment.service.CommentService;
import dgsw.hackathon.DaeItSo.domain.user.domain.entity.User;
import dgsw.hackathon.DaeItSo.global.annotation.CheckToken;
import dgsw.hackathon.DaeItSo.global.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @CheckToken
    @PostMapping
    public ResponseEntity<Response> postComment(@RequestAttribute User user, @Valid @RequestBody CommentSubmitDto commentSubmitDto) {
        commentService.submit(user, commentSubmitDto);
        return Response.ok("댓글 등록 성공");
    }

    @CheckToken
    @PatchMapping
    public ResponseEntity<Response> modifyComment(@RequestAttribute User user, @Valid @RequestBody CommentUpdateDto commentUpdateDto) {
        commentService.update(user, commentUpdateDto);
        return Response.ok("댓글 수정 성공");
    }

    @CheckToken
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Response> deleteComment(@RequestAttribute User user, @PathVariable Long commentId) {
        commentService.deleteById(user, commentId);
        return Response.ok("댓글 삭제 성공");
    }
}
