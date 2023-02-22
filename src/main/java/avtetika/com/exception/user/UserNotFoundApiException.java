package avtetika.com.exception.user;

import avtetika.com.exception.ApiException;

public class UserNotFoundApiException extends ApiException {

    private static final String EXCEPTION_TEXT = "User not found";

    public UserNotFoundApiException() {
        super();
        this.message = EXCEPTION_TEXT;
    }
}
