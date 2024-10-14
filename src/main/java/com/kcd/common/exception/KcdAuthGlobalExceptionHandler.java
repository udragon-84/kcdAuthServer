package com.kcd.common.exception;

import com.kcd.api.response.KcdAuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
     * 회원 중복 에러 Exception 처리 핸들러
     * @param ex auth 도메인 에러 정보
     * @return {@link ResponseEntity<KcdAuthResponse>}
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<KcdAuthResponse<String>> userAlreadyExistsHandleException(UserAlreadyExistsException ex) {
        log.error("KcdAuthGlobalExceptionHandler.userAlreadyExistsHandleException:", ex);
        KcdAuthResponse<String> response = new KcdAuthResponse<>(
                Boolean.FALSE,  // 실패 응답
                HttpStatus.INTERNAL_SERVER_ERROR,  // 상태 코드 500
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "KcdAuthServer UserAlreadyExistsException Error",  // 메시지
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

    /**
     * Controller api 호출 시 권한 Exception 처리
     * @param ex Exception 에러 정보
     * @return {@link ResponseEntity<KcdAuthResponse>}
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<KcdAuthResponse<String>> accessDeniedException(AuthorizationDeniedException ex, HttpServletRequest request) {
        // HTTP 요청에서 URI 정보 추출
        String requestUri = request.getRequestURI();  // 요청 URI

        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        currentAuth.getAuthorities().forEach(auth -> log.error("KcdAuthGlobalExceptionHandler.accessDeniedException Exception currentAuthority : {}", auth.getAuthority()));
        log.error("KcdAuthGlobalExceptionHandler.accessDeniedException - URI: {}, Error: {}", requestUri, ex.getMessage());

        KcdAuthResponse<String> response = new KcdAuthResponse<>(
                Boolean.FALSE,  // 실패 응답
                HttpStatus.FORBIDDEN,  // 상태 코드 500
                HttpStatus.FORBIDDEN.value(),
                "Access denied in for URI: " + requestUri,
                ex.getMessage() // 에러 세부 정보
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    /**
     * EncryptionException 암호화 처리 Exception
     * @param ex EncryptionException 에러 정보
     * @return {@link ResponseEntity<KcdAuthResponse>}
     */
    @ExceptionHandler(EncryptionFailedException.class)
    public ResponseEntity<KcdAuthResponse<String>> handleEncryptionException(EncryptionFailedException ex) {
        log.error("GlobalExceptionHandler.handleEncryptionException", ex);

        KcdAuthResponse<String> response = new KcdAuthResponse<>(
                Boolean.FALSE,  // 실패 응답
                HttpStatus.INTERNAL_SERVER_ERROR,  // 상태 코드 400
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "암호화 처리 중 오류가 발생했습니다.",  // 사용자 메시지
                ex.getMessage()  // 에러 세부 정보
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * DecryptionException 복호화 처리 Exception
     * @param ex DecryptionException 에러 정보
     * @return {@link ResponseEntity<KcdAuthResponse>}
     */
    @ExceptionHandler(DecryptionFailedException.class)
    public ResponseEntity<KcdAuthResponse<String>> handleDecryptionException(DecryptionFailedException ex) {
        log.error("GlobalExceptionHandler.handleDecryptionException", ex);

        KcdAuthResponse<String> response = new KcdAuthResponse<>(
                Boolean.FALSE,  // 실패 응답
                HttpStatus.INTERNAL_SERVER_ERROR,  // 상태 코드 400
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "복호화 처리 중 오류가 발생했습니다.",  // 사용자 메시지
                ex.getMessage()  // 에러 세부 정보
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
