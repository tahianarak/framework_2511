package mg.ituprom16;


import java.text.*;
import java.util.*;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;



public class FrontController extends HttpServlet
{
   private void processing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        ServletOutputStream out = response.getOutputStream();
        String url = request.getRequestURL().toString();

        out.write(("The requested URL is: " + url).getBytes());

        out.close();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
					processing(request,response);
	}	
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
					processing(request,response);
	}	
}