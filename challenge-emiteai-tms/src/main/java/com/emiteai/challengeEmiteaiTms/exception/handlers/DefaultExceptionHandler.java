package com.emiteai.challengeEmiteaiTms.exception.handlers;


import com.emiteai.challengeEmiteaiTms.dto.ErrorResponseDto;
import com.emiteai.challengeEmiteaiTms.exception.FailedOrderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler({FailedOrderException.class})
    public ResponseEntity<?> genericException(FailedOrderException ex) {
        log.error(ex.getMessage(), ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    public ResponseEntity<ErrorResponseDto> buildResponse(final HttpStatus statusCode, final Exception exception) {
        final ErrorResponseDto errorResponse = null;
                new ErrorResponseDto(
                        "Failed",
                        List.of(exception.getLocalizedMessage() != null ? exception.getLocalizedMessage() : ""));
        return new ResponseEntity<>(errorResponse, statusCode);
    }
}
