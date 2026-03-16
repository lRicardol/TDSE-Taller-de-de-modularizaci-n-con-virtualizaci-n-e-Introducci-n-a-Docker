package org.example.framework;

/**
 * Functional interface used to define REST routes
 * using lambda expressions inside the microframework.
 */
@FunctionalInterface
public interface RouteHandler {

    /**
     * Handles an HTTP request and returns the response body.
     *
     * @param req  Request object containing HTTP request data
     * @param res  Response object used to configure HTTP response
     * @return     String body that will be sent to the client
     */
    String handle(Request req, Response res);

}