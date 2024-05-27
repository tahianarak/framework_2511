package mg.ituprom16.util;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import mg.ituprom16.mapping.*;
import mg.ituprom16.annotation.*;

public class ControllerUtil{


    public static  Map<String, Mapping> getUrlDispo(String classpath,String packageSource)throws Exception
    {
        Vector<Class> lsController=ControllerUtil.getControllers(classpath,packageSource);
        Map<String, Mapping> urlDispo = new HashMap<>();
        for(int i=0;i<lsController.size();i++){
            Method[] metTemp=lsController.elementAt(i).getDeclaredMethods();
            for(int j=0;j<metTemp.length;j++){
                if(metTemp[j].isAnnotationPresent(Get.class)){
                    Get annotation = metTemp[j].getAnnotation(Get.class);
                    urlDispo.put(annotation.valeur(),new Mapping(lsController.elementAt(i).getName(),metTemp[j].getName()));
                }
            }
        }
        return urlDispo;
    } 
    public static Vector<Class> getControllers(String classpath,String packageSource)throws Exception
    {
        File classpathDirectory = new File(classpath);
        Vector<Class> lsController=new Vector<Class>();
        for (File file : classpathDirectory.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".class"))
            {
                String className = file.getName().substring(0, file.getName().length() - 6);
                Class clazz = Thread.currentThread().getContextClassLoader().loadClass(packageSource.split("classes/")[1].replace("/", ".")+"."+className);
                if(clazz.isAnnotationPresent(Controller.class))
                {
                    lsController.add(clazz);
                }
            }
        }
        return lsController;
    }

    

}

