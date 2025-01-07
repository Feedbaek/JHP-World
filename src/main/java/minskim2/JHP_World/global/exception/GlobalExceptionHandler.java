package minskim2.JHP_World.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static ResponseEntity<ErrorResponse> getBadResponseEntity(String errors) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.badRequest(errors));
    }

    private static ResponseEntity<ErrorResponse> getResponseEntity(CustomException e) {
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.from(e));
    }


    // 기존 유효성 검사 예외 핸들러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return getBadResponseEntity(errors.toString());
    }

    // 새로운 타입 미스매치 예외 핸들러
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        Map<String, String> errors = Map.of(ex.getName(), ex.getMessage());

        return getBadResponseEntity(errors.toString());
    }

    // 잘못된 형식의 데이터 전송
    @ExceptionHandler(HttpMessageConversionException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageConversionException(final HttpMessageConversionException e) {
        // 잘못된 형식의 데이터 형태 출력
        log.error(e.getMessage());

        return getBadResponseEntity(e.getMessage());
    }


    /** 여기부터는 CustomException 처리 핸들러 */

    @ExceptionHandler({
            CustomException.class,
    })
    public ResponseEntity<ErrorResponse> handleGlobalBadRequestException(final CustomException e) {

        // 500 에러는 로그로 남기기
        if (e.getErrorCode().getHttpStatus().equals(INTERNAL_SERVER_ERROR)) {
            log.error(e.getErrorInfoLog());
        }
        return getResponseEntity(e);
    }

}
