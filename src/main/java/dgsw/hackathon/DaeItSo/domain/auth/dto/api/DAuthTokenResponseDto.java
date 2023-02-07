package dgsw.hackathon.DaeItSo.domain.auth.dto.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DAuthTokenResponseDto {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private String expiresIn;
}
