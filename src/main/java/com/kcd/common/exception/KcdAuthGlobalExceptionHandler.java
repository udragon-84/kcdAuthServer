package com.kcd.common.exception;

import com.kcd.api.response.KcdAuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class KcdAuthGlobalExceptionHandler {

    /**
     * 파라메터로 넘어오는 값 Validation Exception 처리 핸들러
     * @param ex 필드 속성 에러 정보
     * @return {@link ResponseEntity <KcdAuthResponse>}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<KcdAuthResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        // 각 필드의 에러 메시지를 수집
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        log.error("KcdAuthGlobalExceptionHandler.handleValidationExceptions: {}", errors);

        KcdAuthResponse<Map<String, String>> response = new KcdAuthResponse<>(
                Boolean.FALSE,  // 응답이 실패했으므로 false
                HttpStatus.BAD_REQUEST,  // 상태 코드
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",  // 메시지
                errors  // 에러 정보
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Auth 관련 도메인 Exception 처리 핸들러
     * @param ex auth 도메인 에러 정보
     * @return {@link ResponseEntity<KcdAuthResponse>}
     */
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<KcdAuthResponse<String>> authDomainHandleException(AuthException ex) {
        log.error("KcdAuthGlobalExceptionHandler.authDomainHandleException:", ex);
        KcdAuthResponse<String> response = new KcdAuthResponse<>(
                Boolean.FALSE,  // 실패 응답
                HttpStatus.INTERNAL_SERVER_ERROR,  // 상태 코드 500
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "KcdAuthServer AuthException Error",  // 메시지
                ex.getMessage()  // 에러 세부 정보
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 그 외 나머지 Exception 처리
     * @param ex Exception 에러 정보
     * @return {@link ResponseEntity<KcdAuthResponse>}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<KcdAuthResponse<String>> handleException(Exception ex) {
        log.error("KcdAuthGlobalExceptionHandler.handleException", ex);
        KcdAuthResponse<String> response = new KcdAuthResponse<>(
                Boolean.FALSE,  // 실패 응답
                HttpStatus.INTERNAL_SERVER_ERROR,  // 상태 코드 500
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "KcdAuthServer Domain Error",  // 메시지
                ex.getMessage()  // 에러 세부 정보
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
