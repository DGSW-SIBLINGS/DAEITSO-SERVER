package dgsw.hackathon.DaeItSo.domain.auth.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DAuthApiRequestDto {
    private String code;
    private String client_id;
    private String client_secret;
}
