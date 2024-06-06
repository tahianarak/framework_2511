package mg.ituprom16;

import mg.ituprom16.util.*;
import mg.ituprom16.mapping.*;
import java.util.*;
import java.text.*;
import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import mg.ituprom16.annotation.*;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import mg.ituprom16.affloader.*;
import mg.ituprom16.exception.*;



public class FrontController extends HttpServlet
{   
    private String packageSource;
    Map<String, Mapping> urlDispo;

    public  void init() throws ServletException {
        try{
            this.packageSource = this.getInitParameter("package-source");
             getMapping();
        }
        catch(Exception e){
            e.printStackTrace();
        }     
    }
    private void  getMapping() throws Exception
    {
        ServletContext context = getServletContext();
        try{
            String classpath = context.getResource(this.packageSource).getPath();
            classpath = classpath.replace("%20", " ");
            classpath = classpath.substring(1);
            this.urlDispo=ControllerUtil.getUrlDispo(classpath,this.packageSource);
        }
        catch(Exception e){
                if(e instanceof DuplicatedUrlException || e instanceof NoControllerDetectedException){
                    throw e;
                }
                throw new Exception("Le package source spécifée n'existe pas ");
                      
        } 
    }      
    private String restitute(String cheminRessource){
        String str=cheminRessource.split("http://localhost:8080/")[1];
        String[] temp=str.split("/");
       String  ans="";
        for(int i=1;i<temp.length;i++){
            ans=ans+"/"+temp[i];
        }
        return ans;
    }
   private void processing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,Exception
   {
        response.setContentType("text/html");
        String cheminRessource = request.getRequestURL().toString();
        cheminRessource=restitute(cheminRessource);
        String toPrint=null;
        Object ans=ControllerUtil.invokeMethod(urlDispo,cheminRessource);
        if(ans instanceof String) {toPrint=(String)ans;}
        else if(ans instanceof ModelView){
            ModelView mv=(ModelView)ans;
            RequestDispatcher dispat = request.getRequestDispatcher(mv.getUrlDestination());
            Map<String,Object> data =mv.getData();
            Set<String> keys = data.keySet();
            for (String key : keys) {
                request.setAttribute(key,data.get(key));
            }	
            dispat.forward(request,response);
        }
         ServletOutputStream out = response.getOutputStream();
        if(ans ==null){
             out.write(("<h1>Erreur 404 ,cet url n'est pas disponible</h1>").getBytes());
        }
        else if(ControllerUtil.verifyType(ans)==1){
             out.write(("<h1>Type de retour de la fonction associé à l'url non valide </h1>").getBytes());
        }
        else{
           out.write(toPrint.getBytes());  
        }
        out.close();   
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        try{
            processing(request,response);
        }
        catch(Exception e){
            e.printStackTrace();
        }
					
	}	
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		try{
            processing(request,response);
        }
        catch(Exception e){
            e.printStackTrace();
        }
			
	}	
}