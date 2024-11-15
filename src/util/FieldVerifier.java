package mg.ituprom16.util;

import java.lang.reflect.*;
import mg.ituprom16.annotation.modelsAnnotation.*;
import java.sql.Date;
import mg.ituprom16.exception.*;
public class FieldVerifier {
    
    public static  void verify(Field attribut,String value)throws Exception
    {
        try{
            if(attribut.isAnnotationPresent(Daty.class))
            {
                Daty annotation=attribut.getAnnotation(Daty.class);
                java.sql.Date.valueOf(value);
            }
            if(attribut.isAnnotationPresent(Numeric.class))
            {
                Numeric annotation=attribut.getAnnotation(Numeric.class);
                Double.valueOf(value);
            }
            if(attribut.isAnnotationPresent(Length.class))
            {
                Length annotation=attribut.getAnnotation(Length.class);
                int min=annotation.min();
                int max=annotation.max();
                if(value.length()<min || value.length()>max)
                {
                    throw new TypeFormatException(attribut.getName());
                }
            }
        }
        catch(Exception e)
        {
            throw new TypeFormatException(attribut.getName());
        }
       
    }
}
