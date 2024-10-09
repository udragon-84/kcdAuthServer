package com.kcd.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@Getter
@Schema(description = "Kcd 인증/인가 응답 객체", name = "KcdAuthResponse<T>")
public class KcdAuthResponse<T> {

    @Schema(description = "응답 성공 여부", example = "true")
    private final Boolean result;

    @Schema(description = "응답 HttpStatus", example = "Ok")
    private final HttpStatus httpStatus;

    @Schema(description = "응답 에러 코드", example = "")
    private final Integer errorCode;

    @Schema(description = "응답 메시지", example = "success")
    private final String message;

    @Schema(description = "응답 객체 정보 (T Type Parameter)", example = "Object")
    private final T data;

    public KcdAuthResponse(T data) {
        this(Boolean.TRUE, HttpStatus.OK, null, "success", data);
    }
}
