package minskim2.JHP_World.config.login.exception;

import minskim2.JHP_World.global.exception.CustomException;
import minskim2.JHP_World.global.exception.ErrorCode;

public class LoginException extends CustomException {
    protected LoginException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static class LoginFailedException extends LoginException {
        public LoginFailedException() {
            super(ErrorCode.LOGIN_FAILED);
        }
    }
}
