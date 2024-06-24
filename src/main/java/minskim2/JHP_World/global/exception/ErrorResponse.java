package minskim2.JHP_World.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
    private int code;
    private String message;
    private Map<String, String> property;

    public static ErrorResponse from(final CustomException customException) {
        return new ErrorResponse(customException.getCode(), customException.getMessage(), customException.getProperty());
    }
}
