package web.exception;

import lombok.Getter;

@Getter
public class DataBaseException extends RuntimeException {

    private final ErrorTypes errorType;

    public DataBaseException(ErrorTypes errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }
}
