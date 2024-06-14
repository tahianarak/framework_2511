package mg.ituprom16.exception;

import jakarta.servlet.*;

public class PageNotFoundException extends ServletException{
    public PageNotFoundException(String message){
            super(message);
    }
}