package mg.ituprom16.util;


import mg.ituprom16.annotation.authentification.*;
import mg.ituprom16.exception.*;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class AuthVerifier
{
     public static HashMap<String, Integer> mapRoles(String data) {
    
        HashMap<String, Integer> roles = new HashMap<>();
        
    
        String[] lines = data.split("\n");
        
   
        for (String line : lines) {
            String[] parts = line.split(" ");
            String key = parts[0];  
            int value = Integer.parseInt(parts[1]); 
            roles.put(key, value);  
        }
        
        return roles;
    }
    public static void verify(Method function,HttpSession session,ServletContext context)throws Exception
    {
        HashMap<String,Integer> roles=(HashMap<String,Integer>)context.getAttribute("roles");
       // System.out.println(roles.toString());
        String sessionRoleName=(String)context.getAttribute("sessionRoleName");
        String sessionStatusName=(String)context.getAttribute("sessionStatusName");
        if(function.isAnnotationPresent(Authentified.class))
        {
            if(session.getAttribute(sessionStatusName)==null)
            {
                throw new AuthentificationMethodException("vous n'etes pas connectes");
            }
            if((String)session.getAttribute(sessionRoleName)!=null)
            {
                String sessionAuth=(String)session.getAttribute(sessionRoleName);


                if(sessionAuth.equals("Authentified")==false && roles.get(sessionAuth)<function.getAnnotation(Authentified.class).level()){
                    throw new AuthentificationMethodException("vous n'avez pas accès a cette methdode");
                }
            }
        }
        else if(function.isAnnotationPresent(Free.class))
        {

        }
        else if(function.isAnnotationPresent(ConfiguredAuth.class))
        {
            if(session.getAttribute(sessionStatusName)==null)
            {
                throw new AuthentificationMethodException("vous n'etes pas connectes");
            }
            String sessionAuth=(String)session.getAttribute(sessionRoleName);
            String annotationValue=function.getAnnotation(ConfiguredAuth.class).value();
            int functionLevel=roles.get(sessionAuth);
            int annotationLevel=roles.get(annotationValue);    
            System.out.println(functionLevel+" "+annotationLevel);

            if(sessionAuth==null || functionLevel<annotationLevel){
                throw new AuthentificationMethodException("vous n'avez pas accès a cette methdode");
            }
        }
    }
}