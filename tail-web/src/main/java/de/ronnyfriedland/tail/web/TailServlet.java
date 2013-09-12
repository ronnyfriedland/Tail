package de.ronnyfriedland.tail.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import de.ronnyfriedland.tail.lib.http.HttpCmd;
import de.ronnyfriedland.tail.lib.script.ScriptCmd;

/**
 * Simple servlet that provides the new logfile rows
 * 
 * @author Ronny Friedland
 */
@Path("/data")
@Produces("text/plain")
public class TailServlet {

    /**
     * Get the Logfile data
     * 
     * @param url the source where the logdata will be collected from
     * @return the data
     * @throws Exception no exception is catched
     */
    @GET
    @Path("http")
    public String getDataFromUrl(final @QueryParam("src") String url) {
        return new HttpCmd().getAvailableData(url);
    }

    /**
     * Get the Logfile data
     * 
     * @param script the source where the logdata will be collected from
     * @return the data
     * @throws Exception no exception is catched
     */
    @GET
    @Path("script")
    public String getDataFromScript(final @QueryParam("src") String script) {
        return new ScriptCmd().getAvailableData(script);
    }
}
