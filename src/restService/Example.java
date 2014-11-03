package restService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * 
 * 
 * @author 李融
 */
@Path("")
public class Example
{
	
	public Example()
	{
	
	}

	/**
	 * resteasy example
	 * 
	 * @param requestStr
	 * @return
	 */
	@GET
	@Path("/helloworld")
	@Produces("text/plain; charset=utf-8")
	public String getClueList()
	{
		return "hello world";
	}
}