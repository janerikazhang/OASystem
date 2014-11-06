package restService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
		return "{\"returnString\":\"hello world\"}";
	}
	
	@POST
	@Path("/testGrid")
	@Produces("text/plain; charset=utf-8")
	public String getgriddata(String data)
	{
		System.out.println(data);
		return "{\"page\":1,\"total\":239,\"rows\":[{\"id\":\"ZW\",\"cell\":[\"<input type='checkbox'/>\",\"Zimbabwe\",\"Zimbabwe\",\"ZWE\",\"716\"]},{\"id\":\"ZM\",\"cell\":[\"ZM\",\"Zambia\",\"Zambia\",\"ZMB\",\"894\"]},{\"id\":\"YE\",\"cell\":[\"YE\",\"Yemen\",\"Yemen\",\"YEM\",\"887\"]},{\"id\":\"EH\",\"cell\":[\"EH\",\"Western Sahara\",\"Western Sahara\",\"ESH\",\"732\"]},{\"id\":\"WF\",\"cell\":[\"WF\",\"Wallis and Futuna\",\"Wallis and Futuna\",\"WLF\",\"876\"]},{\"id\":\"VI\",\"cell\":[\"VI\",\"Virgin Islands, U.s.\",\"Virgin Islands, U.s.\",\"VIR\",\"850\"]},{\"id\":\"VG\",\"cell\":[\"VG\",\"Virgin Islands, British\",\"Virgin Islands, British\",\"VGB\",\"92\"]},{\"id\":\"VN\",\"cell\":[\"VN\",\"Viet Nam\",\"Viet Nam\",\"VNM\",\"704\"]},{\"id\":\"VE\",\"cell\":[\"VE\",\"Venezuela\",\"Venezuela\",\"VEN\",\"862\"]},{\"id\":\"VU\",\"cell\":[\"VU\",\"Vanuatu\",\"Vanuatu\",\"VUT\",\"548\"]}]}";
	}
}