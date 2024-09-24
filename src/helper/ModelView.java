package mg.ituprom16.affloader;

import java.util.*;

public class ModelView
{
    String urlDestination;
    Map<String,Object> data;


    public void addObject(String name,Object value){
            this.data.put(name,value);
    }  


    public ModelView(String urlDestination) {
        this.urlDestination = urlDestination;
        this.data=new HashMap<>();
    }
    
    public String getUrlDestination() {
        return urlDestination;
    }
    public void setUrlDestination(String urlDestination) {
        this.urlDestination = urlDestination;
    }  
    public Map<String, Object> getData() {
        return data;
    }
}