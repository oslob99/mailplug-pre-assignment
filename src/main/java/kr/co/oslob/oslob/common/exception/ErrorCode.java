package kr.co.oslob.oslob.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements CustomErrorCode{

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "❌ 파라미터 요청이 잘못 되었습니다."),
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "❌ 게시판을 찾을 수 없습니다."),
    NOT_POST_BOARD(HttpStatus.NOT_FOUND, "❌ 게시글을 찾을 수 없습니다."),
    NOT_FOUND_REPLY(HttpStatus.NOT_FOUND, "❌ 댓글을 찾을 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
