package org.example.framework;

/**
 * Public API of the microframework.
 * Provides static methods for defining routes and configuring static files.
 */
public class MicroFramework {

    private static Router router = new Router();
    private static String staticFilesFolder = "webroot";

    /**
     * Registers a GET route using a lambda expression.
     */
    public static void get(String path, RouteHandler handler) {
        router.addGetRoute(path, handler);
    }

    /**
     * Defines the folder where static files are located.
     */
    public static void staticfiles(String folder) {
        staticFilesFolder = folder;
    }

    /**
     * Starts the HTTP server on the specified port.
     */
    public static void start(int port) {
        HttpServer server = new HttpServer(port, router, staticFilesFolder);
        server.start();
    }
}