package com.timmattison.embeddedvaadin.vaadin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.startup.ApplicationRouteRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

public class DaggerVaadinServiceInitListener implements VaadinServiceInitListener {
    private final Logger log = LoggerFactory.getLogger(DaggerVaadinServiceInitListener.class);

    public static Set<Class<? extends Component>> vaadinComponents;

    public DaggerVaadinServiceInitListener() {
    }

    @Override
    public void serviceInit(ServiceInitEvent event) {
        ApplicationRouteRegistry applicationRouteRegistry = ((ApplicationRouteRegistry) RouteConfiguration.forApplicationScope().getHandledRegistry());
        vaadinComponents.forEach(componentClass -> autoWire(applicationRouteRegistry, componentClass));
        logRoutes(applicationRouteRegistry);
    }

    protected void logRoutes(ApplicationRouteRegistry applicationRouteRegistry) {
        applicationRouteRegistry.getRegisteredRoutes()
                .forEach(routeData -> log.info("Route: [" + routeData.getUrl() + "] -> [" + routeData.getNavigationTarget().getName() + "]"));
    }

    private void autoWire(ApplicationRouteRegistry applicationRouteRegistry, Class<? extends Component> componentClass) {
        log.info("Attempting to auto-wire [" + componentClass.getName() + "]");
        getRoute(componentClass).ifPresent(route -> setRoute(applicationRouteRegistry, componentClass, route));

        if (componentClass.getAnnotation(PWA.class) != null) {
            log.info("Wiring up [" + componentClass.getName() + "] as the PWA configuration class");
            applicationRouteRegistry.setPwaConfigurationClass(componentClass);
        } else {
            log.info("[" + componentClass.getName() + "] was not a PWA configuration class");
        }
    }

    private void setRoute(ApplicationRouteRegistry applicationRouteRegistry, Class<? extends Component> componentClass, String route) {
        log.info("Setting route [" + route + "] for [" + componentClass.getName() + "]");
        applicationRouteRegistry.setRoute(route, componentClass, new ArrayList<>());
    }

    public Optional<String> getRoute(Class<? extends Component> clazz) {
        Route route = clazz.getAnnotation(Route.class);

        if (route == null) {
            log.info("Route for [" + clazz.getName() + "] is null. It is not being added to the application route registry.");
            return Optional.empty();
        }

        String routeValue = route.value();

        if (routeValue.equals(Route.NAMING_CONVENTION)) {
            log.info("Route for [" + clazz.getName() + "] is blank. It is being added to the application route registry with the value ['']");
            return Optional.of("");
        }

        log.info("Route for [" + clazz.getName() + "] is [" + routeValue + "]");
        return Optional.of(routeValue);
    }
}
