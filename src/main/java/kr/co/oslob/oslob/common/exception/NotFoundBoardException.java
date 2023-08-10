package kr.co.oslob.oslob.common.exception;

import lombok.Getter;

public class NotFoundBoardException extends CustomException{

    @Getter
    private final ErrorCode errorCode;

    @Getter
    private final Long id;

    public NotFoundBoardException(ErrorCode errorCode, Long id){
        super(errorCode);
        this.errorCode = errorCode;
        this.id = id;
    }
}
