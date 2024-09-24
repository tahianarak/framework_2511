package mg.ituprom16.session;

import jakarta.servlet.http.*;

public class MySession{
    HttpSession session;

    public MySession(HttpSession session){
        this.session=session;
    }

    public void destroy(){
        session.invalidate();
    }

    public Object get(String key)
    {
        return session.getAttribute(key);
    }

    public void remove(String key){
        session.removeAttribute(key);
    }

    public void add(String key,Object value)
    {
        session.setAttribute(key,value);
    }
}