package kr.co.oslob.oslob.common.exception;

import lombok.Getter;

public class NotFoundReplyException extends CustomException{

    @Getter
    private final ErrorCode errorCode;

    @Getter
    private final Long id;

    public NotFoundReplyException(ErrorCode errorCode, Long id) {
        super(errorCode);
        this.errorCode = errorCode;
        this.id = id;
    }
}
