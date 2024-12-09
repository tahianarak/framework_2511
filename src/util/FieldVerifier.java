package mg.ituprom16.util;

import java.lang.reflect.*;
import mg.ituprom16.annotation.modelsAnnotation.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import mg.ituprom16.exception.*;
public class FieldVerifier
{
    
    public static  void verify(Field attribut,String value,String nameOfValue)throws Exception
    {   
        List<String> errors=new ArrayList<>();
            if(attribut.isAnnotationPresent(Daty.class))
            {
                try{
                    Daty annotation=attribut.getAnnotation(Daty.class);
                    java.sql.Date.valueOf(value);
                }
                catch(Exception e){
                    errors.add("doit etre de type date");
                }
                
            }
            if(attribut.isAnnotationPresent(Numeric.class))
            {   
                try
                {
                    Numeric annotation=attribut.getAnnotation(Numeric.class);
                    Double.valueOf(value);
                }
                catch(Exception e)
                {
                    errors.add("doit etre numerique");
                }
                
            }
            if(attribut.isAnnotationPresent(Length.class))
            {
                Length annotation=attribut.getAnnotation(Length.class);
                int min=annotation.min();
                int max=annotation.max();
                if(value.length()<min || value.length()>max)
                {
                    errors.add("doit etre compris entre "+min +"et "+max);
                }
            }
        if(errors.size()!=0){
            throw new TypeFormatException(nameOfValue,errors);
        }    
        
    }
}

