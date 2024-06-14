package mg.ituprom16.exception;

import jakarta.servlet.*;


public class DuplicatedUrlException extends ServletException{
    public DuplicatedUrlException(String message){
            super(message);
    }
}