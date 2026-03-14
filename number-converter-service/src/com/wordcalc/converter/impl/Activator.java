package com.wordcalc.converter.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.wordcalc.converter.api.NumberWordConverterService;

public class Activator implements BundleActivator {

    private ServiceRegistration<NumberWordConverterService> serviceRegistration;

    @Override
    public void start(BundleContext context) {
        NumberWordConverterService converterService = new NumberWordConverterServiceImpl();
        serviceRegistration = context.registerService(
                NumberWordConverterService.class,
                converterService,
                null
        );
    }

    @Override
    public void stop(BundleContext context) {
        if (serviceRegistration != null) {
            serviceRegistration.unregister();
        }
    }
}
