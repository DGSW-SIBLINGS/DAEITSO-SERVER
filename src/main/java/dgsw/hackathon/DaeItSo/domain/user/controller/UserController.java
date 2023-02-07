package dgsw.hackathon.DaeItSo.domain.user.controller;

import dgsw.hackathon.DaeItSo.domain.user.domain.entity.User;
import dgsw.hackathon.DaeItSo.domain.user.dto.UserInfoResponseDto;
import dgsw.hackathon.DaeItSo.domain.user.service.UserService;
import dgsw.hackathon.DaeItSo.global.annotation.CheckToken;
import dgsw.hackathon.DaeItSo.global.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CheckToken
    @GetMapping("/info")
    public ResponseEntity<DataResponse<UserInfoResponseDto>> getUserInfo(@RequestAttribute User user) {
        return DataResponse.ok("유저 정보 조회 성공", new UserInfoResponseDto(user));
    }
}
