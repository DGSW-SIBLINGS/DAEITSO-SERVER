package dgsw.hackathon.DaeItSo.domain.comment.service;

import dgsw.hackathon.DaeItSo.domain.comment.domain.dto.CommentSubmitDto;
import dgsw.hackathon.DaeItSo.domain.comment.domain.dto.CommentUpdateDto;
import dgsw.hackathon.DaeItSo.domain.post.domain.entity.Post;
import dgsw.hackathon.DaeItSo.domain.post.repository.PostRepository;
import dgsw.hackathon.DaeItSo.domain.user.domain.entity.User;
import dgsw.hackathon.DaeItSo.global.error.CustomError;
import dgsw.hackathon.DaeItSo.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dgsw.hackathon.DaeItSo.domain.comment.repository.CommentRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void submit(User user, CommentSubmitDto commentSubmitDto) {
        Post post = postRepository.findById(commentSubmitDto.getPostId())
                .orElseThrow(() -> new CustomError(ErrorCode.NOT_FOUND));
        commentRepository.save(commentSubmitDto.toEntity(user, post, commentSubmitDto));
    }

    @Transactional
    public void update(User user, CommentUpdateDto commentUpdateDto) {
        commentRepository.findById(commentUpdateDto.getCommentId()).ifPresentOrElse(
                comment -> {
                    if (comment.getUser().getId().equals(user.getId())) comment.update(commentUpdateDto);
                    else throw CustomError.of(ErrorCode.WRONG_USER);
                }, () -> CustomError.of(ErrorCode.NOT_FOUND)
        );
    }

    @Transactional
    public void deleteById(User user, Long commentId) {
        commentRepository.findById(commentId).ifPresentOrElse(
                comment -> {
                    if (comment.getUser().getId().equals(user.getId())) commentRepository.deleteById(commentId);
                    else throw CustomError.of(ErrorCode.WRONG_USER);
                }, () -> CustomError.of(ErrorCode.NOT_FOUND)
        );
    }
}
