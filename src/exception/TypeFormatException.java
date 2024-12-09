package mg.ituprom16.exception;

import jakarta.servlet.*;
import java.util.List;
import java.util.ArrayList;

public class TypeFormatException extends ServletException
{
    String paramName;
    List<String> errors;
    public String getParamName()
    {
        return this.paramName;
    }
    public String getMessage(){
        String ans = "le parametre "+paramName+" contient les erreurs suivantes:";
        for(int i=0;i<errors.size();i++)
        {
            ans=ans+errors.get(i);
            if(i+1!=errors.size())
            {
                 ans=ans+",";
            }
           
        }
        return ans ;
    }
    public TypeFormatException(String paramName,List<String> errors)
    {
        this.paramName=paramName;
        this.errors=errors;
    }
   
}