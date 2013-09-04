package de.ronnyfriedland.tail.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import de.ronnyfriedland.tail.lib.Tail;

/**
 * Simple servlet that provides the new logfile rows
 * 
 * @author Ronny Friedland
 */
@Path("/")
@Produces("text/plain")
public class TailServlet {

    /**
     * Get the Logfile data
     * 
     * @param url the url from where the data will be queried
     * @return the data
     * @throws Exception no exception is catched
     */
    @GET
    @Path("data")
    public String getData(final @QueryParam("url") String url) throws Exception {
        return new Tail().getAvailableData(url);
    }

}
