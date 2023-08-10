package kr.co.oslob.oslob.common.exception;

import org.springframework.http.HttpStatus;

public interface CustomErrorCode {

    HttpStatus getHttpStatus();
    String getMessage();
}
