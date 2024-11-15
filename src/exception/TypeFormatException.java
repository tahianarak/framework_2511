package mg.ituprom16.exception;

import jakarta.servlet.*;

public class TypeFormatException extends ServletException{
    String attributeName;

    public void setAttributeName(String attributeName){
        this.attributeName=attributeName;
    }

    public String getMessage()
    {
        return "il y un probleme avec l'attribut:"+this.attributeName;
    }
    public TypeFormatException(String attributeName){
            setAttributeName(attributeName);
    }
}