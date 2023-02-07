package dgsw.hackathon.DaeItSo.domain.auth.service;

import dgsw.hackathon.DaeItSo.domain.auth.dto.api.*;
import dgsw.hackathon.DaeItSo.domain.auth.dto.res.TokenResponseDto;
import dgsw.hackathon.DaeItSo.domain.user.service.UserService;
import dgsw.hackathon.DaeItSo.global.config.jwt.JwtUtil;
import dgsw.hackathon.DaeItSo.global.config.properties.DodamProperties;
import dgsw.hackathon.DaeItSo.global.config.webclient.WebClientUtil;
import dgsw.hackathon.DaeItSo.global.config.webclient.parser.HeaderParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final WebClientUtil webClientUtil;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final DodamProperties dodamProperties;


    public TokenResponseDto getToken(DAuthApiRequestDto dAuthApiRequestDto) {

        DAuthTokenResponseDto dAuthTokenResponseDto = webClientUtil.post(
                dodamProperties.getDauth(),
                dAuthApiRequestDto,
                DAuthTokenResponseDto.class
        ).getBody();

        return getUserInfo(dAuthTokenResponseDto);
    }

    public TokenResponseDto getUserInfo(DAuthTokenResponseDto dto) {

        DAuthUserInfoResponseDto infoResponseDto = webClientUtil.get(
                dodamProperties.getOpenDodam(),
                HeaderParser.builder()
                        .type("Authorization")
                        .value("Bearer " + dto.getAccessToken()).build(),
                DAuthUserInfoResponseDto.class
        ).getBody();

        Optional.ofNullable(infoResponseDto.getData().getProfileImage()).ifPresent(
            s -> { if (s.contains(".null")) infoResponseDto.getData().setProfileImgNull(); });

        userService.save(infoResponseDto.getData().toEntity());

        return createUserToken(infoResponseDto.getData());
    }

    private TokenResponseDto createUserToken(DAuthUserInfoDataResponseDto DAuthUserInfoDataResponseDto) {
        return TokenResponseDto.builder()
                .accessToken(jwtUtil.generateAccessToken(DAuthUserInfoDataResponseDto.getEmail()))
                .refreshToken(jwtUtil.generateRefreshToken(DAuthUserInfoDataResponseDto.getEmail()))
                .build();
    }
}
