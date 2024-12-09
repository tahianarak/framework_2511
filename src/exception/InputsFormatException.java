package mg.ituprom16.exception;

import jakarta.servlet.*;
import java.util.List;
import java.util.ArrayList;

public class InputsFormatException extends ServletException
{
    List<TypeFormatException> lsException=new ArrayList<>();
    public void addException(TypeFormatException e)
    {
        this.lsException.add(e);
    }
    public List<TypeFormatException> getLsException()
    {
        return lsException;
    }
}