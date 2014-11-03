/************************************************************************
日  期：		2014-05-06
作  者:		李融
版  本：     
描  述:	    
历  史：      
 ************************************************************************/
package restService.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import restService.Example;

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