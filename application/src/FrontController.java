package mg.ituprom16;

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



public class FrontController extends HttpServlet
{   
    private String packageSource;
    Vector<Class> lsController;

    public void init() throws ServletException {
        try{
            this.packageSource = this.getInitParameter("package-source");
             getControllers();
        }
        catch(Exception e){
            e.printStackTrace();
        }     
    }
    private void  getControllers()throws Exception
    {
        ServletContext context = getServletContext();
        String classpath = context.getResource(this.packageSource).getPath();
        classpath = classpath.replace("%20", " ");
        classpath = classpath.substring(1);
        File classpathDirectory = new File(classpath);
        lsController=new Vector<Class>();
        for (File file : classpathDirectory.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".class"))
            {
                String className = file.getName().substring(0, file.getName().length() - 6);
                Class clazz = Thread.currentThread().getContextClassLoader().loadClass(this.packageSource.split("classes/")[1].replace("/", ".")+"."+className);
                if(clazz.isAnnotationPresent(Controller.class))
                {
                    lsController.add(clazz);
                }
            }
        }
    }      

   private void processing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,Exception
   {
        response.setContentType("text/plain");
        ServletOutputStream out = response.getOutputStream();
        String toPrint="";
        for(int i=0;i<lsController.size();i++){
            toPrint=toPrint+lsController.elementAt(i).getName()+"\n";
        }
        out.write((toPrint).getBytes());

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