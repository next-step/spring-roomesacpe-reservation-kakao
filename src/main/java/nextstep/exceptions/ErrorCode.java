package nextstep.exceptions;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 예약입니다."),
    RESERVATION_DUPLICATED(HttpStatus.CONFLICT, "이미 예약된 테마이거나 날짜/시간 입니다."),
    TIME_INVALID(HttpStatus.BAD_REQUEST, "예약이 불가능한 시간입니다."),
    INPUT_PARAMETER_INVALID(HttpStatus.BAD_REQUEST, "잘못된 입력 입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
