package minskim2.JHP_World.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@ToString
@AllArgsConstructor
public class ErrorResponse {
    private final int code;
    private final String message;


    public static ErrorResponse from(final CustomException customException) {
        ErrorCode errorCode = customException.getErrorCode();
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage());
    }

    public static ErrorResponse badRequest(final String message) {
        return new ErrorResponse(BAD_REQUEST.value(), message);
    }
}
