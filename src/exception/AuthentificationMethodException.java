package mg.ituprom16.exception;

import jakarta.servlet.*;


public class AuthentificationMethodException extends ServletException{
    public AuthentificationMethodException(String message){
            super(message);
    }
}