package kz.akvelon.twitter.exception;

import org.springframework.http.HttpStatus;

public class EmailSendingException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public EmailSendingException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public EmailSendingException(String message, HttpStatus status, String message1) {
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
