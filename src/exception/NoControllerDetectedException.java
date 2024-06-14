package mg.ituprom16.exception;

import jakarta.servlet.*;

public class NoControllerDetectedException extends ServletException{
    public NoControllerDetectedException(String message){
            super(message);
    }
}