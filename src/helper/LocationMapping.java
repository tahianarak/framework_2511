package mg.ituprom16.mapping;

import java.util.Objects;

public class LocationMapping {

    private String methodName;
    private String verb;


    @Override
    public int hashCode(){
        return Objects.hash(methodName,verb);
    } 

    @Override
    public boolean equals(Object o)
    {
        if(o instanceof LocationMapping){
            LocationMapping temp=(LocationMapping)o;
            if(temp.methodName.equals(this.methodName)){
                return true;
            }
            if(temp.verb.equals(this.verb)){
                return true;
            }
        }
        return false;
    }


    // Constructeur par défaut
    public LocationMapping() {
    }

    // Constructeur avec paramètres
    public LocationMapping(String methodName, String verb) {
        this.methodName = methodName;
        this.verb = verb;
    }

    // Getter pour methodName
    public String getMethodName() {
        return methodName;
    }

    // Setter pour methodName
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    // Getter pour verb
    public String getVerb() {
        return verb;
    }

    // Setter pour verb
    public void setVerb(String verb) {
        this.verb = verb;
    }



}
