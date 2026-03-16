package org.example.framework;

import java.util.HashMap;
import java.util.Map;

/**
 * Router is responsible for registering and resolving
 * REST routes inside the microframework.
 */
public class Router {

    // Map: HTTP Method -> (Path -> RouteHandler)
    private Map<String, Map<String, RouteHandler>> routes = new HashMap<>();

    /**
     * Registers a new GET route.
     */
    public void addGetRoute(String path, RouteHandler handler) {
        routes
                .computeIfAbsent("GET", k -> new HashMap<>())
                .put(path, handler);
    }

    /**
     * Returns the RouteHandler associated with
     * the given method and path.
     */
    public RouteHandler getRoute(String method, String path) {

        Map<String, RouteHandler> methodRoutes = routes.get(method);

        if (methodRoutes != null) {
            return methodRoutes.get(path);
        }

        return null;
    }

    /**
     * Checks if a route exists.
     */
    public boolean routeExists(String method, String path) {
        Map<String, RouteHandler> methodRoutes = routes.get(method);
        return methodRoutes != null && methodRoutes.containsKey(path);
    }
}