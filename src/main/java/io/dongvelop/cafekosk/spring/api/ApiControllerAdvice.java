package io.dongvelop.cafekosk.spring.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@ControllerAdvice
@RestControllerAdvice
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResponse<Object> bindException(BindException e) {
        final ObjectError errorMessage = e.getBindingResult().getAllErrors().get(0);
        return ApiResponse.of(
                HttpStatus.BAD_REQUEST,
                errorMessage.getDefaultMessage(),
                null
        );
    }
}
