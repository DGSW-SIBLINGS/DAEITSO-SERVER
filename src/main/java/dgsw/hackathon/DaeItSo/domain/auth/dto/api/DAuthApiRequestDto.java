package dgsw.hackathon.DaeItSo.domain.auth.dto.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DAuthApiRequestDto {
    private String code;
    private String client_id;
    private String client_secret;
}
