package org.eclipse.edc.extension.config;

import org.eclipse.edc.runtime.metamodel.annotation.Inject;
import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;
import org.eclipse.edc.web.spi.WebService;

public class HealthExtension implements ServiceExtension {

    private static final String NAME = "Configurable Health Service";
    private static final String LOG_PREFIX = "edc.configuration.log.prefix";

    @Inject
    private WebService webService;

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void initialize(ServiceExtensionContext context) {
        String logPrefix = context.getSetting(LOG_PREFIX, "health");
        webService.registerResource(new HealthController(context.getMonitor(), logPrefix));
    }
}
