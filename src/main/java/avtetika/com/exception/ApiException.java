package avtetika.com.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonPropertyOrder({"httpStatus", "message"})
@JsonRootName("err")
@Getter
public class ApiException extends RuntimeException {

    @JsonProperty("httpStatus")
    protected HttpStatus httpStatus;

    @JsonProperty("errCode")
    protected Integer errCode;

    @JsonProperty("message")
    protected String message;

    public ApiException() {
        super();
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public ApiException(int code) {
        this.httpStatus = HttpStatus.valueOf(code);
        this.errCode = code;
    }

    public ApiException(int code, String message) {
        httpStatus = HttpStatus.valueOf(code);
        this.message = message;
        this.errCode = code;
    }

    public ApiException(String message) {
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.message = message;
        this.errCode = 400;
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
