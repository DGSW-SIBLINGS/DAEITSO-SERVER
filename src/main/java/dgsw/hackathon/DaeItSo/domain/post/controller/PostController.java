package dgsw.hackathon.DaeItSo.domain.post.controller;

import dgsw.hackathon.DaeItSo.domain.post.domain.enums.Category;
import dgsw.hackathon.DaeItSo.domain.post.dto.PostListResponseDto;
import dgsw.hackathon.DaeItSo.domain.post.dto.PostUpdateDto;
import dgsw.hackathon.DaeItSo.domain.post.service.PostService;
import dgsw.hackathon.DaeItSo.domain.user.domain.entity.User;
import dgsw.hackathon.DaeItSo.global.annotation.CheckToken;
import dgsw.hackathon.DaeItSo.global.response.DataResponse;
import dgsw.hackathon.DaeItSo.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dgsw.hackathon.DaeItSo.domain.post.dto.PostResponseDto;
import dgsw.hackathon.DaeItSo.domain.post.dto.PostSubmitDto;

import java.util.List;

@RestController
@RequestMapping(value = "/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @CheckToken
    @PostMapping
    public ResponseEntity<Response> submit(@RequestAttribute User user, @RequestBody PostSubmitDto postSubmitDto) {
        postService.submit(postSubmitDto, user);
        return Response.ok("게시물 등록 성공");
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<PostListResponseDto>>> readAll() {
        return DataResponse.ok("전체 게시물 조회 성공", postService.findPostAll());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<DataResponse<PostResponseDto>> readOne(@PathVariable("postId") Long postId) {
        return DataResponse.ok("단일 게시물 조회 성공", postService.findPostOne(postId));
    }

    @GetMapping("/tag/{name}")
    public ResponseEntity<DataResponse<List<PostListResponseDto>>> readAllByTag(@PathVariable("name") Category category) {
        return DataResponse.ok("카테고리로 게시물 조회 성공", postService.findPostByCategory(category));
    }

    @CheckToken
    @GetMapping("/user/{userId}")
    public ResponseEntity<DataResponse<List<PostListResponseDto>>> readAllByUser(@RequestAttribute User user) {
        return DataResponse.ok("유저 게시물 조회 성공", postService.findPostByUser(user));
    }

    @CheckToken
    @PatchMapping
    public ResponseEntity<Response> update(@RequestAttribute User user, @RequestBody PostUpdateDto postUpdateDto) {
        postService.update(postUpdateDto, user);
        return Response.ok("게시물 수정 성공");
    }

    @CheckToken
    @DeleteMapping("/{postId}")
    public ResponseEntity<Response> delete(@RequestAttribute User user, @PathVariable("postId")Long postId) {
        postService.delete(postId, user);
        return Response.ok("게시물 삭제 성공");
    }

    @GetMapping("/keyword/{word}")
    public ResponseEntity<DataResponse<List<PostResponseDto>>> search(@PathVariable("word")String keyWord) {
        return DataResponse.ok("게시물 검색 성공", postService.search(keyWord));
    }
}
