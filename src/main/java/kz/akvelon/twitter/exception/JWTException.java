package kz.akvelon.twitter.exception;

import org.springframework.http.HttpStatus;
//APIException
public class JWTException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public JWTException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public JWTException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
