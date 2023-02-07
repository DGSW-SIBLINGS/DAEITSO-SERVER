package dgsw.hackathon.DaeItSo.global.handler;

import dgsw.hackathon.DaeItSo.global.error.CustomError;
import dgsw.hackathon.DaeItSo.global.error.ErrorCode;
import dgsw.hackathon.DaeItSo.global.error.ErrorResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomError.class)
    protected ResponseEntity handleCustomException(CustomError e) {
        return ErrorResponseEntity.responseEntity(e.getErrorCode());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity handleNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(404)
                .body(ErrorResponseEntity.builder()
                        .status(ErrorCode.NOT_FOUND.getHttpStatus().value())
                        .code(ErrorCode.NOT_FOUND.name())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity handleException(Exception e) {
        log.error(e.toString());
        return ResponseEntity
                .status(500)
                .body(ErrorResponseEntity.builder()
                        .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus().value())
                        .code(ErrorCode.INTERNAL_SERVER_ERROR.name())
                        .message(e.getMessage())
                        .build());
    }
}
