package mg.ituprom16.exception;

import jakarta.servlet.*;

public class InvalidTypeException extends ServletException{
    public InvalidTypeException(String message){
            super(message);
    }
}