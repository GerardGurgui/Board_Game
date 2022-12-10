package GerardGurgui.GameBoard.exceptions.customizedExceptions;

import org.springframework.http.HttpStatus;

public class PlayerOutOfLimitsException extends RuntimeException{

    private HttpStatus httpStatus;
    private String message;

    public PlayerOutOfLimitsException(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public PlayerOutOfLimitsException(String message, HttpStatus httpStatus, String message2){
        super(message);
        this.httpStatus = httpStatus;
        this.message = message2;
    }

    public HttpStatus getHttpStatus(){
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

}