package minskim2.JHP_World.config.login.exception;

import minskim2.JHP_World.global.exception.CustomException;
import minskim2.JHP_World.global.exception.ErrorCode;

public class JwtCustomException extends CustomException {
    protected JwtCustomException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static class JwtExpiredException extends JwtCustomException {
        public JwtExpiredException() {
            super(ErrorCode.JWT_EXPIRED);
        }
    }

    public static class JwtInvalidException extends JwtCustomException {
        public JwtInvalidException() {
            super(ErrorCode.JWT_INVALID);
        }
    }
}
