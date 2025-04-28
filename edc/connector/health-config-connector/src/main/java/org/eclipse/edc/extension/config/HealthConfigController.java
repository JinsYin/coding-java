package org.eclipse.edc.extension.config;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.edc.spi.monitor.Monitor;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HealthConfigController {

    private final Monitor monitor;
    private final String logPrefix;

    public HealthConfigController(Monitor monitor, String logPrefix) {
        this.monitor = monitor;
        this.logPrefix = logPrefix;
    }

    @GET
    @Path("/health")
    public String check() {
        monitor.info(String.format("%s :: It's ok", logPrefix));
        return "{\"result\":\"ok\"}";
    }
}
