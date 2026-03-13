package com.wordcalc.converter.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.wordcalc.converter.api.NumberWordConverterService;

public class Activator implements BundleActivator {

    private ServiceRegistration<NumberWordConverterService> serviceRegistration;

    @Override
    public void start(BundleContext context) {
        System.out.println("number-converter-service started");

        NumberWordConverterService converterService = new NumberWordConverterServiceImpl();
        serviceRegistration = context.registerService(
                NumberWordConverterService.class,
                converterService,
                null
        );

        System.out.println("NumberWordConverterService registered");
    }

    @Override
    public void stop(BundleContext context) {
        System.out.println("number-converter-service stopped");

        if (serviceRegistration != null) {
            serviceRegistration.unregister();
            System.out.println("NumberWordConverterService unregistered");
        }
    }
}
