package mg.ituprom16.util;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import mg.ituprom16.mapping.*;
import mg.ituprom16.annotation.*;
import mg.ituprom16.affloader.*;
import mg.ituprom16.exception.*;

public class ControllerUtil{

    public static int verifyType(Object o){
        if(o instanceof ModelView){
            return 0;
        }
        else if(o instanceof String){
            return 0;
        }
        else{
            return 1;
        }
    }
    public static Object invokeMethod(Map<String, Mapping> urlDispo,String cheminRessource)throws Exception{
            
           for(int i=0;i<urlDispo.size();i++){
                if(urlDispo.get(cheminRessource)!=null){
                    Mapping real=urlDispo.get(cheminRessource);
                    Class classToUse=Class.forName(real.getClassName());
                    Method methodToUse=classToUse.getDeclaredMethod(real.getMethodName(),new Class[0]);
                    Object temp=classToUse.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    return methodToUse.invoke(temp,new Object[0]);
                }
            }
            return null;
    }
    public static  Map<String, Mapping> getUrlDispo(String classpath,String packageSource)throws Exception
    {
        Vector<Class> lsController=ControllerUtil.getControllers(classpath,packageSource);
        Map<String, Mapping> urlDispo = new HashMap<>();
        for(int i=0;i<lsController.size();i++){
            Method[] metTemp=lsController.elementAt(i).getDeclaredMethods();
            for(int j=0;j<metTemp.length;j++){
                if(metTemp[j].isAnnotationPresent(Get.class)){
                    Get annotation = metTemp[j].getAnnotation(Get.class);
                    if(urlDispo.containsKey(annotation.valeur())){
                        throw new DuplicatedUrlException("plusieurs url de meme valeur pour: "+annotation.valeur());
                    }
                    else
                    {
                        urlDispo.put(annotation.valeur(),new Mapping(lsController.elementAt(i).getName(),metTemp[j].getName()));
                    }
                    
                }
            }
        }
        return urlDispo;
    } 
    public static Vector<Class> getControllers(String classpath,String packageSource)throws Exception
    {
        File classpathDirectory = new File(classpath);
        if (!classpathDirectory.exists()) {
             throw new Exception("ce package n'existe pas");
        }
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
        if(lsController.size()==0){
             throw new NoControllerDetectedException("ce package ne contient aucun controller");
        }
        return lsController;
    }

    

}

