package minskim2.JHP_World.domain.member.exception;


import minskim2.JHP_World.global.exception.CustomException;
import minskim2.JHP_World.global.exception.ErrorCode;

public class MemberException extends CustomException {
    protected MemberException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static class MemberNotFoundException extends MemberException {
        public MemberNotFoundException() {
            super(ErrorCode.MEMBER_NOT_FOUND);
        }
    }
}
