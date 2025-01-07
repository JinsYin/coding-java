package org.eclipse.edc.extension.health;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.edc.spi.monitor.Monitor;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HealthController {

    private final Monitor monitor;

    public HealthController(Monitor monitor) {
        this.monitor = monitor;
    }

    @GET
    @Path("health")
    public String check() {
        monitor.info("This is a health request");
        return "{\"response\": \"yes\"}";
    }
}
