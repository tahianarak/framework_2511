package mg.ituprom16.mapping;

import java.util.*;

public class Mapping {

    private String className;
    HashSet<LocationMapping> lsLocationMapping;




    public Mapping(String className){
        this.className=className;
        this.lsLocationMapping=new HashSet<LocationMapping>();
    }
    public void addLocationMapping(String methodName,String verb){
        lsLocationMapping.add(new LocationMapping(methodName,verb));
    }

    public String getClassName(){
        return this.className;
    }
    public HashSet<LocationMapping> getLsLocationMapping(){
        return this.lsLocationMapping;
    }
    public Mapping(String className,HashSet<LocationMapping> lsLocationMapping){
            this.className=className;
            this.lsLocationMapping=lsLocationMapping;
    }
}