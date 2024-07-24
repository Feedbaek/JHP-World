package minskim2.JHP_World.domain.member.exception;

import minskim2.JHP_World.global.exception.CustomException;
import minskim2.JHP_World.global.exception.ErrorCode;

public class RoleException extends CustomException {
    protected RoleException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static class RoleNotFoundException extends RoleException {
        public RoleNotFoundException() {
            super(ErrorCode.ROLE_NOT_FOUND);
        }
    }
}
