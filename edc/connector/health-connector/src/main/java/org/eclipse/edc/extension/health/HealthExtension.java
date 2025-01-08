package org.eclipse.edc.extension.health;

import org.eclipse.edc.runtime.metamodel.annotation.Inject;
import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;
import org.eclipse.edc.web.spi.WebService;

public class HealthExtension implements ServiceExtension {
    public static final String NAME = "Health Service";

    /**
     * 注入来自 jersey-core 扩展通过 `context.registerService()` 注册的 WebService，其实现类为 JerseyRestService
     */
    @Inject
    private WebService webService;

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void initialize(ServiceExtensionContext context) {
        webService.registerResource(new HealthController(context.getMonitor()));
    }
}
