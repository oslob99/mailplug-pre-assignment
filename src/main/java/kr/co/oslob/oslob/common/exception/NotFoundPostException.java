package kr.co.oslob.oslob.common.exception;

import lombok.Getter;

public class NotFoundPostException extends CustomException{

    @Getter
    private final ErrorCode errorCode;

    @Getter
    private final Long id;

    public NotFoundPostException(ErrorCode errorCode, Long id) {
        super(errorCode);
        this.errorCode = errorCode;
        this.id = id;
    }
}
