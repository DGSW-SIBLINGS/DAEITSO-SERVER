package dgsw.hackathon.DaeItSo.domain.post.service;

import dgsw.hackathon.DaeItSo.domain.file.service.FileService;
import dgsw.hackathon.DaeItSo.domain.post.domain.entity.Post;
import dgsw.hackathon.DaeItSo.domain.post.domain.enums.Category;
import dgsw.hackathon.DaeItSo.domain.post.dto.PostListResponseDto;
import dgsw.hackathon.DaeItSo.domain.post.dto.PostUpdateDto;
import dgsw.hackathon.DaeItSo.domain.post.repository.PostRepository;
import dgsw.hackathon.DaeItSo.domain.user.domain.entity.User;
import dgsw.hackathon.DaeItSo.global.error.CustomError;
import dgsw.hackathon.DaeItSo.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dgsw.hackathon.DaeItSo.domain.post.dto.PostResponseDto;
import dgsw.hackathon.DaeItSo.domain.post.dto.PostSubmitDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final FileService fileService;

    @Transactional
    public void submit(PostSubmitDto postSubmitDto, User user) {
        postRepository.save(postSubmitDto.toEntity(postSubmitDto, user));
    }

    public List<PostListResponseDto> findPostAll() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream()
                .map(post -> new PostListResponseDto(post))
                .collect(Collectors.toList());
    }

    public PostResponseDto findPostOne(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw CustomError.of(ErrorCode.NOT_FOUND);
        });
        return new PostResponseDto(post);
    }

    public List<PostListResponseDto> findPostByCategory(Category category) {
        return postRepository.findAllByCategory(category, Sort.by(Sort.Direction.DESC, "id")).stream()
                .map( post -> new PostListResponseDto(post))
                .collect(Collectors.toList());
    }

    public List<PostListResponseDto> findPostByUser(User user) {
        return postRepository.findAllByUser(user, Sort.by(Sort.Direction.DESC, "id")).stream()
                .map(post -> new PostListResponseDto(post))
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(PostUpdateDto postUpdateDto, User user) {
        postRepository.findById(postUpdateDto.getPostId()).ifPresentOrElse(
                post -> {
                    User postUser = post.getUser();
                    if(user.getId().equals(postUser.getId())) {
                        Optional.ofNullable(post.getImgUrl())
                                .ifPresent(url -> fileService.update(postUpdateDto.getImgUrls(), url));
                        post.updateInfo(postUpdateDto);
                    } else throw CustomError.of(ErrorCode.WRONG_USER);
                },
                () -> { throw CustomError.of(ErrorCode.NOT_FOUND);});
    }

    @Transactional
    public void delete(Long postId, User user) {
        postRepository.findById(postId).ifPresentOrElse(
                post -> {
                    User postUser = post.getUser();
                    if(user.getId().equals(postUser.getId())) {
                        postRepository.delete(post);
                        Optional.ofNullable(post.getImgUrl())
                                .ifPresent(urls -> {
                                    for (String url : List.of(urls.split("///")).stream().collect(Collectors.toList())) {
                                        fileService.delete(url);
                                    }
                                });
                    } else throw CustomError.of(ErrorCode.WRONG_USER);
                },
                () -> { throw CustomError.of(ErrorCode.NOT_FOUND);});
    }

    public List<PostResponseDto> search(String keyWord) {
        return postRepository.findByContentContaining(keyWord, Sort.by(Sort.Direction.DESC, "id") ).stream()
                .map(post -> new PostResponseDto(post)).collect(Collectors.toList());
    }
}
