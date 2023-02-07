package dgsw.hackathon.DaeItSo.domain.auth.controller;

import dgsw.hackathon.DaeItSo.domain.auth.dto.req.DAuthClientRequestDto;
import dgsw.hackathon.DaeItSo.domain.auth.dto.res.AccessTokenDto;
import dgsw.hackathon.DaeItSo.domain.auth.dto.res.TokenResponseDto;
import dgsw.hackathon.DaeItSo.domain.auth.service.AuthService;
import dgsw.hackathon.DaeItSo.global.annotation.CheckToken;
import dgsw.hackathon.DaeItSo.global.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Callable<ResponseEntity<DataResponse<TokenResponseDto>>> resCode(@RequestBody DAuthClientRequestDto dAuthClientRequestDto){
        TokenResponseDto token = authService.getToken(dAuthClientRequestDto.getCode());
        return () -> { return DataResponse.ok("인증 성공", token);};
    }

    @CheckToken
    @GetMapping("/refreshToken")
    public ResponseEntity<DataResponse<AccessTokenDto>> getAccessToken(@RequestAttribute("accessToken") AccessTokenDto accessTokenDto) {
        return DataResponse.ok("토큰 생성 성공", accessTokenDto);
    }
}

