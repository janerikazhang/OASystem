package restService.application;

import restService.Example;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class RestApplication extends Application
{

    HashSet<Object> singletons = new HashSet<Object>();

    public RestApplication()
    {
        //singletons.add(new Example());
        
    }

    @Override
    public Set<Class<?>> getClasses()
    {
        HashSet<Class<?>> set = new HashSet<Class<?>>();
        set.add(Example.class);
        return set;
    }

//    @Override
//    public Set<Object> getSingletons()
//    {
//        return null;
//    }
}