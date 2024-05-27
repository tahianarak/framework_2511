package mg.ituprom16.mapping;

public class Mapping {

    private String className;
    private String methodName;

    // Constructeur par défaut
    public Mapping() {
        this("", "");
    }

    // Constructeur avec paramètres
    public Mapping(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }

    // Getter pour className
    public String getClassName() {
        return className;
    }

    // Setter pour className
    public void setClassName(String className) {
        this.className = className;
    }

    // Getter pour methodName
    public String getMethodName() {
        return methodName;
    }

    // Setter pour methodName
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}