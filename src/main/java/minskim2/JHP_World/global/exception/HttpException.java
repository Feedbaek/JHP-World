package minskim2.JHP_World.global.exception;

import java.util.Map;

public class HttpException extends CustomException {
    protected HttpException(ErrorCode errorCode) {
        super(errorCode);
    }
    protected HttpException(ErrorCode errorCode, Map<String, String> property) {
        super(errorCode, property);
    }

    public static class BadRequestException extends HttpException {
        public BadRequestException() {
            super(ErrorCode.BAD_REQUEST);
        }
        public BadRequestException(Map<String, String> property) {
            super(ErrorCode.BAD_REQUEST, property);
        }
    }

    public static class UnauthorizedException extends HttpException {
        public UnauthorizedException() {
            super(ErrorCode.UNAUTHORIZED);
        }
        public UnauthorizedException(Map<String, String> property) {
            super(ErrorCode.UNAUTHORIZED, property);
        }
    }

    public static class ForbiddenException extends HttpException {
        public ForbiddenException() {
            super(ErrorCode.FORBIDDEN);
        }
        public ForbiddenException(Map<String, String> property) {
            super(ErrorCode.FORBIDDEN, property);
        }
    }

    public static class NotFoundException extends HttpException {
        public NotFoundException() {
            super(ErrorCode.NOT_FOUND);
        }
        public NotFoundException(Map<String, String> property) {
            super(ErrorCode.NOT_FOUND, property);
        }
    }

    public static class ConflictException extends HttpException {
        public ConflictException() {
            super(ErrorCode.CONFLICT);
        }
        public ConflictException(Map<String, String> property) {
            super(ErrorCode.CONFLICT, property);
        }
    }

    public static class InternalServerException extends HttpException {
        public InternalServerException() {
            super(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        public InternalServerException(Map<String, String> property) {
            super(ErrorCode.INTERNAL_SERVER_ERROR, property);
        }
    }
}
