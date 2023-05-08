package com.empik.githubservice.error;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import feign.FeignException.NotFound;

@ControllerAdvice
class ControllerExceptionHandler {

    private static final Logger LOG = getLogger(lookup().lookupClass());

    @ExceptionHandler(value = NotFound.class)
    public ResponseEntity<Object> handleFeignNotFound(NotFound notFound) {
        ErrorResponse errorResponse = new ErrorResponse();
        LOG.info("not found from feign with uuid: {}", errorResponse.uuid(), notFound);
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ErrorResponse> handleAllOtherExceptions(RuntimeException runtimeException) {
        ErrorResponse errorResponse = new ErrorResponse();
        LOG.error("exception occurs with uuid: {}", errorResponse.uuid(), runtimeException);
        return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
    }
}
