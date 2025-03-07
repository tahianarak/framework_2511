package mg.ituprom16.util;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import mg.ituprom16.mapping.*;
import mg.ituprom16.annotation.*;
import mg.ituprom16.affloader.*;
import mg.ituprom16.exception.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import mg.ituprom16.session.*;
import java.text.SimpleDateFormat;
import com.google.gson.*;

public class ControllerUtil{

    public static String restitute(String cheminRessource){
        String str=cheminRessource.split("http://localhost:8080/")[1];
        String[] temp=str.split("/");
       String  ans="";
        for(int i=1;i<temp.length;i++){
            ans=ans+"/"+temp[i];
        }
        return ans;
    }

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

    public static Object[] getObjectToUseAsParameter(Method method,Map<String,String> inputs,HttpSession session,HttpServletRequest request)throws Exception{
        Parameter[] methodParams=method.getParameters();
        Map<String,Vector<String>> inputsPerObjects=ControllerUtil.triParObject(inputs);
        Object[] objectToUseAsParameter=new Object[methodParams.length];
        int count=0;
        for(int k=0;k<methodParams.length;k++){
            if(methodParams[k].isAnnotationPresent(Match.class))
            {
                 Match annotation=methodParams[k].getAnnotation(Match.class);
                if(inputs.size()!=0)
                {
                    if(inputsPerObjects.get(annotation.param()).elementAt(0).equals("simple")==false)
                    {
                            Vector<String> listeAttributsClasse=inputsPerObjects.get(annotation.param());
                            objectToUseAsParameter[count]=ControllerUtil.buildObjectFromForAnnoted(methodParams[k],listeAttributsClasse,inputs);
                            count++;
                    }
                    else
                    {
                        String value=inputs.get(methodParams[k].getAnnotation(Match.class).param());
                        if(methodParams[k].getType().getSimpleName().equals("int"))
                        {
                            objectToUseAsParameter[count]=Integer.valueOf(value).intValue();
                        }
                        else if(methodParams[k].getType().getSimpleName().equals("double"))
                        {
                            objectToUseAsParameter[count]=Double.valueOf(value).doubleValue();
                        }
                        else if(methodParams[k].getType().getSimpleName().equals("String"))
                        {
                            objectToUseAsParameter[count]=value;
                        }
                        else if(methodParams[k].getType().getSimpleName().equals("Date"))
                        {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            
                            objectToUseAsParameter[count]=dateFormat.parse(value);
                        }
                    count++;
                    }
                }
                else if(methodParams[k].getType().getSimpleName().equals("Part"))
                {
                    objectToUseAsParameter[count]=request.getPart(methodParams[k].getAnnotation(Match.class).param());
                    count++;
                }                    
            }
            else if(!methodParams[k].isAnnotationPresent(Match.class))
            {
                if(methodParams[k].getType().getSimpleName().equals("MySession")){
                    objectToUseAsParameter[count]=new MySession(session);
                    count++;
                }
                else{
                     throw new InvalidTypeException("ETU002511:"+"annotation non presente pour le parametre n"+k+" de la fonction ");
                }
            }
        }
        return objectToUseAsParameter;
    }
    public static Object buildObjectByName(Parameter param,String paramName,Vector<String> inputsPerObjects,Map<String,String> inputs)throws Exception{
        Class type=param.getType();
        Constructor builder =type.getConstructor(new Class[0]);
        Object built=builder.newInstance(new Object[0]);
        InputsFormatException inputsException=new InputsFormatException();
        for(int i=0;i<inputsPerObjects.size();i++){
            Field attribut=type.getDeclaredField(ControllerUtil.getFieldName(type,inputsPerObjects.elementAt(i)));
            if(attribut.isAccessible())
            {
                try{
                    String tempValue=inputs.get(paramName+":"+inputsPerObjects.elementAt(i));
                    ControllerUtil.setter(type,attribut,tempValue,built,paramName+":"+inputsPerObjects.elementAt(i));
                }
                catch(TypeFormatException e)
                {
                    inputsException.addException(e);
                }
                
            }
            else{
                try{
                    attribut.setAccessible(true);
                    String tempValue=inputs.get(paramName+":"+inputsPerObjects.elementAt(i));
                    ControllerUtil.setter(type,attribut,tempValue,built,paramName+":"+inputsPerObjects.elementAt(i));
                    attribut.setAccessible(false);
                }
                catch(TypeFormatException e)
                {
                    inputsException.addException(e);
                }
                
            } 
          
        }
        if(inputsException.getLsException().size()!=0)
        {
            throw inputsException;
        }
        return built;
    }

    public static String getFieldName(Class type,String supposedName){
        Field[] lsField=type.getDeclaredFields();
        for(int i=0;i<lsField.length;i++){
            if(lsField[i].isAccessible()){
                if(lsField[i].isAnnotationPresent(FieldMatcher.class)){ 
                    FieldMatcher annotation =lsField[i].getAnnotation(FieldMatcher.class);
                    if(annotation.name().equals(supposedName))
                    {   
                        return lsField[i].getName();
                    }
                }   
            }
            else{
                lsField[i].setAccessible(true);
                if(lsField[i].isAnnotationPresent(FieldMatcher.class)){ 
                    FieldMatcher annotation =lsField[i].getAnnotation(FieldMatcher.class);
                    if(annotation.name().equals(supposedName))
                    {   
                        return lsField[i].getName();
                    }
                } 
                lsField[i].setAccessible(false);
            }
           
        }
        return supposedName;
    } 
    public static Object buildObjectFromForAnnoted(Parameter param,Vector<String> inputsPerObjects,Map<String,String> inputs)throws Exception{
        Class type=param.getType();
        Constructor builder =type.getConstructor(new Class[0]);
        Object built=builder.newInstance(new Object[0]);
        InputsFormatException inputsException=new InputsFormatException();
        for(int i=0;i<inputsPerObjects.size();i++){
            Field attribut=type.getDeclaredField(ControllerUtil.getFieldName(type,inputsPerObjects.elementAt(i)));
            if(attribut.isAccessible())
            {
                try{
                    String tempValue=inputs.get(param.getAnnotation(Match.class).param()+":"+inputsPerObjects.elementAt(i));
                    ControllerUtil.setter(type,attribut,tempValue,built,param.getAnnotation(Match.class).param()+":"+inputsPerObjects.elementAt(i));
                }
                catch(TypeFormatException e)
                {
                    inputsException.addException(e);
                }
               
            }
            else{
                try{
                    attribut.setAccessible(true);
                    String tempValue=inputs.get(param.getAnnotation(Match.class).param()+":"+inputsPerObjects.elementAt(i));
                    ControllerUtil.setter(type,attribut,tempValue,built,param.getAnnotation(Match.class).param()+":"+inputsPerObjects.elementAt(i));
                    attribut.setAccessible(false);
                }
                catch(TypeFormatException e)
                {
                    inputsException.addException(e);
                }
                
            } 
        }
        if(inputsException.getLsException().size()!=0)
        {
            throw inputsException;
        }
        return built;
    }
    public static Vector<Method> getDeclaredMethodsByName(Class type,String methodsName){
        Vector<Method> lsAns=new Vector<Method>();
        Method[] lsMethod=type.getDeclaredMethods();
        for(int i=0;i<lsMethod.length;i++){
            if(lsMethod[i].getName().equals(methodsName))   
            lsAns.add(lsMethod[i]); 
        }
        return lsAns;
    }
  
    public static void setter(Class type,Field attribut,String value,Object built,String valueName)throws Exception{
        Class[] parameterTypes=new Class[1];
        parameterTypes[0]=attribut.getType();
        FieldVerifier.verify(attribut,value,valueName);
        try{
            if(attribut.getType().getSimpleName().equals("Date")){
                Object[] lsParams=new Object[1];
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                lsParams[0]=dateFormat.parse(value);
                Method toUse=type.getDeclaredMethod("set"+Capitalizer.capitalizeFirst(attribut.getName()),parameterTypes);
                toUse.invoke(built,lsParams);
                    return;
            }
            if(attribut.getType().getSimpleName().equals("String")){
                Object[] lsParams=new Object[1];
                lsParams[0]=value;
                Method toUse=type.getDeclaredMethod("set"+Capitalizer.capitalizeFirst(attribut.getName()),parameterTypes);
                toUse.invoke(built,lsParams);
                return;
            }
            if(attribut.getType().getSimpleName().equals("int")){
                Object[] lsParams=new Object[1];
                lsParams[0]=Integer.valueOf(value).intValue();
                Method toUse=type.getDeclaredMethod("set"+Capitalizer.capitalizeFirst(attribut.getName()),parameterTypes);
                toUse.invoke(built,lsParams);
                return;
            }
            if(attribut.getType().getSimpleName().equals("double")){
                Object[] lsParams=new Object[1];
                lsParams[0]=Double.valueOf(value).doubleValue();
                Method toUse=type.getDeclaredMethod("set"+Capitalizer.capitalizeFirst(attribut.getName()),parameterTypes);
                toUse.invoke(built,lsParams);
                return;
            }
        }
        catch(IllegalArgumentException e){
            throw new IllegalArgumentException("une valeur que vous avez envoyez,n'a pas le bon type ou format");
        }
        
    }
    public static void setter(Field attribut,String value,Object built)throws Exception{

        if(attribut.getType().getSimpleName().equals("String")){
            attribut.set(built,value);
            return;
        }
        try{
             attribut.setInt(built,Integer.valueOf(value).intValue());
             return;
        }
        catch(Exception e)
        {
            attribut.setDouble(built,Double.valueOf(value).doubleValue());
             return;
        }
    }


      public static Map<String,Vector<String>> triParObject(Map<String, String> inputs)throws Exception{
        Map<String,Vector<String>> ansTries=new HashMap();
        Vector<String> tabKeys=ControllerUtil.getKeys(inputs);
        for(int i=0;i<tabKeys.size();i++){
            String tempKey=((tabKeys.elementAt(i)).split(":"))[0];
            Vector<String> tempValues=new Vector<String>();
            if(!ansTries.containsKey(tempKey)){
                try
                {
                    tempValues.add((tabKeys.elementAt(i).split(":"))[1]); 
                    for(int j=i+1;j<tabKeys.size();j++){
                        if(tabKeys.elementAt(j).split(":")[0].equals(tempKey)){
                            tempValues.add((tabKeys.elementAt(j).split(":"))[1]);
                        } 
                    }
                }
                catch(Exception e){
                    tempValues.add("simple");    
                }
                ansTries.put(tempKey,tempValues);
            }
        }
        return ansTries;
    }

    public static Vector<String> getKeys(Map<String, String> inputs)throws Exception{
        Set<String> keys = inputs.keySet();
        Vector<String> tabKeys=new Vector<String>();
        for (String key : keys){
            tabKeys.add(key);
        }
        return tabKeys;
    }
    public static Method getMethodToUse(String cheminRessource,String verb,Method[] lsMethod)throws Exception{
            for(int i=0;i<lsMethod.length;i++){
                if(lsMethod[i].isAnnotationPresent(URL.class)){
                    URL annotation=lsMethod[i].getAnnotation(URL.class);
                    if(annotation.valeur().equals(cheminRessource) && ControllerUtil.getVerb(lsMethod[i]).equals(verb)){
                        return lsMethod[i];        
                    }
                }
            }
            return null;
    }

    public static void setSessionAttribute(Object toUse,HttpSession session)throws Exception{
        Field[] lsfield=toUse.getClass().getDeclaredFields();
        for(int i=0;i<lsfield.length;i++){
            if(lsfield[i].getClass().getSimpleName().equals("MySession")){
                lsfield[i].set(toUse,new MySession(session));
                break;
            }
        }
    }

    public static Object invokeMethodAfterException(Map<String, Mapping> urlDispo,String cheminRessource,Map<String, String> inputs,HttpSession session,ServletContext context,String verb)throws Exception
    {   
           for(int i=0;i<urlDispo.size();i++){
                if(urlDispo.get(cheminRessource)!=null){
                    Mapping truereal=urlDispo.get(cheminRessource);
                    
                    LocationMapping real=null;
                    HashSet<LocationMapping> liste=truereal.getLsLocationMapping();
                    for(LocationMapping temp : liste)
                    {

                       if(verb.equals(temp.getVerb())){
                            real=temp;    
                            break;
                       } 
                    }

                    if(real==null){
                         throw new PageNotFoundException("erreur 500->bad request,this method is not supported for this url");
                    }
                   
                    Class classToUse=Class.forName(truereal.getClassName());
                    AuthVerifier.verify(classToUse,session,context);
                    Method[] lsMethod=classToUse.getMethods();
                    Method methodToUse=ControllerUtil.getMethodToUse(cheminRessource,verb,lsMethod);
                    AuthVerifier.verify(methodToUse,session,context);
                    Parameter[] methodParams=methodToUse.getParameters();
                    Object temp=classToUse.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    ControllerUtil.setSessionAttribute(temp,session);
                    Object ans= methodToUse.invoke(temp,new Object[0]);
                    temp=null;

                    if(methodToUse.isAnnotationPresent(RestApi.class))
                    {
                        if(ans instanceof ModelView)
                        {
                            ans=((ModelView)(ans)).getData();
                        }
                    }
                    else if(ControllerUtil.verifyType(ans)==1){
                            throw new InvalidTypeException("<h1>Type de retour de la fonction associé à l'url non valide </h1>");
                    }

                   
                    return ans;
                }
                else{
                        throw new PageNotFoundException("<h1>Erreur 404 ,cet url n'est pas disponible</h1>");
                }
            }
            return null;
        }       
        
    public static Object invokeMethod(Map<String, Mapping> urlDispo,String cheminRessource,Map<String, String> inputs,HttpSession session,HttpServletRequest request, HttpServletResponse response)throws Exception
    {   
        Method globalMethod=null;
        try
        {
           for(int i=0;i<urlDispo.size();i++){
                if(urlDispo.get(cheminRessource)!=null){
                    Mapping truereal=urlDispo.get(cheminRessource);
                    
                    LocationMapping real=null;
                    HashSet<LocationMapping> liste=truereal.getLsLocationMapping();
                    for(LocationMapping temp : liste)
                    {

                       if(request.getMethod().equals(temp.getVerb())){
                            real=temp;    
                            break;
                       } 
                    }

                    if(real==null){
                         throw new PageNotFoundException("erreur 500->bad request,this method is not supported for this url");
                    }
                    
                    Class classToUse=Class.forName(truereal.getClassName());
                    AuthVerifier.verify(classToUse,session,request.getServletContext());
                    Method[] lsMethod=classToUse.getMethods();
                    Method methodToUse=ControllerUtil.getMethodToUse(cheminRessource,request.getMethod(),lsMethod);
                    AuthVerifier.verify(methodToUse,session,request.getServletContext());
                    globalMethod=methodToUse;
                    Parameter[] methodParams=methodToUse.getParameters();
                    Object[] methodAttributs=ControllerUtil.getObjectToUseAsParameter(methodToUse,inputs,session,request);
                    for(int d=0;d<methodAttributs.length;d++){
                        System.out.println(methodAttributs[d].toString());
                    }
                    Object temp=classToUse.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    ControllerUtil.setSessionAttribute(temp,session);
                    Object ans= methodToUse.invoke(temp,methodAttributs);
                    temp=null;

                    if(methodToUse.isAnnotationPresent(RestApi.class))
                    {
                        if(ans instanceof ModelView)
                        {
                            ans=((ModelView)(ans)).getData();
                        }
                    }
                    else if(ControllerUtil.verifyType(ans)==1){
                            throw new InvalidTypeException("<h1>Type de retour de la fonction associé à l'url non valide </h1>");
                    }

                   
                    return ans;
                }
                else{
                        throw new PageNotFoundException("<h1>Erreur 404 ,cet url n'est pas disponible</h1>");
                }
            }
        }    
        catch(InputsFormatException e)
        {
            String cheminressource2="";

            String contextPath = request.getContextPath();
            if(globalMethod.isAnnotationPresent(URLAfterException.class))
            {
                cheminressource2=globalMethod.getAnnotation(URLAfterException.class).valeur();
            }
            else
            {
                cheminressource2=restitute(request.getHeader("referer"));
            }
            ModelView mv=(ModelView)ControllerUtil.invokeMethodAfterException(urlDispo, cheminressource2, inputs,session,request.getServletContext(),"GET");
             mv.addObject(e.getLsException().get(0).getParamName()+"_errors",e.getLsException().get(0).getMessage()+"<script>window.history.pushState({}, \"\", \""+contextPath+cheminressource2+"\");console.log(\"voila\");</script>");
            for(int i=1;i<e.getLsException().size();i++)
            {
                 mv.addObject(e.getLsException().get(i).getParamName()+"_errors",e.getLsException().get(i).getMessage());
            }
            return mv;
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
                if(metTemp[j].isAnnotationPresent(URL.class))
                {
                    URL annotation = metTemp[j].getAnnotation(URL.class);  
                    if(!urlDispo.containsKey(annotation.valeur()))
                    {
                        Mapping urlAssociateMethod=new Mapping(lsController.elementAt(i).getName());
                        urlAssociateMethod.addLocationMapping(metTemp[j].getName(),ControllerUtil.getVerb(metTemp[j]));
                        for(int k=j+1; k<metTemp.length; k++)
                        {
                            if(metTemp[k].isAnnotationPresent(URL.class) && metTemp[k].getAnnotation(URL.class).valeur().equals(annotation.valeur()))
                            {   
                                if(ControllerUtil.getVerb(metTemp[k]).equals(ControllerUtil.getVerb(metTemp[j])))
                                {
                                   throw new Exception("Erreur !vous ne pouvez pas avoir deux memes url avec des verbes pareils");
                                }
                               /* if(metTemp[k].getName().equals(metTemp[j].getName()))
                                {
                                   throw new Exception("Erreur !vous ne pouvez pas avoir deux fonctions de meme nom et de meme url");
                                }*/
                                 urlAssociateMethod.addLocationMapping(metTemp[k].getName(),ControllerUtil.getVerb(metTemp[k]));
                                
                            }   
                        }
                        urlDispo.put(annotation.valeur(),urlAssociateMethod);
                    }
                }
            }
           
        }
        return urlDispo;
    } 
    public static String getVerb(Method method){
            if(method.isAnnotationPresent(Get.class)){
                return "GET"; 
            }
            else if(method.isAnnotationPresent(Post.class))
            { 
                return "POST";
            }
            return "GET"   ; 
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

