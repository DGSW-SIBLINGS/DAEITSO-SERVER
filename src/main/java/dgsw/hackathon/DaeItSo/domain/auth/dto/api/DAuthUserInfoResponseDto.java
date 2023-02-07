package dgsw.hackathon.DaeItSo.domain.auth.dto.api;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DAuthUserInfoResponseDto {

    private String message;
    private DAuthUserInfoDataResponseDto data;
}
