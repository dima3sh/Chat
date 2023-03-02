package avtetika.com.exception.handler;

import avtetika.com.exception.ApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ApiException.class})
    protected ResponseEntity<Object> handleConflict(ApiException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex, new HttpHeaders(), ex.getHttpStatus(), request);
    }
}
