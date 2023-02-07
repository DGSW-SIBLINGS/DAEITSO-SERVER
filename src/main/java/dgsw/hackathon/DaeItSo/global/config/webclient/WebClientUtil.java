package dgsw.hackathon.DaeItSo.global.config.webclient;

import dgsw.hackathon.DaeItSo.global.error.CustomError;
import dgsw.hackathon.DaeItSo.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WebClientUtil {

    private final WebClientConfig webClientConfig;

    public <T> ResponseEntity<T> get(String url, Class<T> responseDtoClass) {
        return webClientConfig.webClient().method(HttpMethod.GET)
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(CustomError.of(ErrorCode.INTERNAL_SERVER_ERROR)))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(CustomError.of(ErrorCode.INTERNAL_SERVER_ERROR)))
                .toEntity(responseDtoClass)
                .block();
    }

    public <T> ResponseEntity<T> get(String url, String accessToken, Class<T> responseDtoClass) {
        return webClientConfig.webClient().method(HttpMethod.GET)
                .uri(url)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(CustomError.of(ErrorCode.INTERNAL_SERVER_ERROR)))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(CustomError.of(ErrorCode.INTERNAL_SERVER_ERROR)))
                .toEntity(responseDtoClass)
                .block();
    }

    public <T, V> ResponseEntity<T> post(String url, V requestDto, Class<T> responseDtoClass) {
        return webClientConfig.webClient().method(HttpMethod.POST)
                .uri(url)
                .bodyValue(requestDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(CustomError.of(ErrorCode.INTERNAL_SERVER_ERROR)))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(CustomError.of(ErrorCode.INTERNAL_SERVER_ERROR)))
                .toEntity(responseDtoClass)
                .block();
    }
}
