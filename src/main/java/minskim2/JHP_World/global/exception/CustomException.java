package minskim2.JHP_World.global.exception;

import lombok.*;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomException extends RuntimeException {

    private static final String EXCEPTION_INFO_BRACKET = "{ %s | %s }";
    private static final String CODE_MESSAGE = " code: %d, message: %s ";
    private static final String PROPERTY_VALUE = "%s=%s";
    private static final String VALUE_DELIMITER = "; ";
    private static final String RESPONSE_MESSAGE = "%d %s";

    private final ErrorCode errorCode;
    private final Map<String, Object> property;


    public static CustomException of(ErrorCode errorCode, Map<String, Object> property) {
        return new CustomException(errorCode, property);
    }
    public static CustomException of(ErrorCode errorCode) {
        return new CustomException(errorCode, Map.of());
    }

    /**
     * property를 포함한 에러 로그를 반환한다.
     * */
    public String getErrorInfoLog() {
        final String codeMessage = String.format(CODE_MESSAGE, errorCode.getCode(), errorCode.getMessage());
        final String errorPropertyValue = getPropertyToString();

        return String.format(EXCEPTION_INFO_BRACKET, codeMessage, errorPropertyValue);
    }

    /**
     * property를 문자열로 반환한다.
     * */
    public String getPropertyToString() {
        return property.entrySet()
                .stream()
                .map(entry -> String.format(PROPERTY_VALUE, entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(VALUE_DELIMITER));
    }

    /**
     * 에러 응답을 반환한다. property는 포함되지 않는다.
     * */
    public String getErrorResponse() {
        return String.format(RESPONSE_MESSAGE, errorCode.getCode(), errorCode,getMessage());
    }
}
