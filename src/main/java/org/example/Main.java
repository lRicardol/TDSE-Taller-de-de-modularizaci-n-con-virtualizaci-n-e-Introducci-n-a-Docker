package org.example;

import static org.example.framework.MicroFramework.*;

public class Main {

    public static void main(String[] args) {

        try {

            printBanner();

            configureServer();

            registerRoutes();

            int port = getPort();
            start(port);

            System.out.println("Server started successfully on port " + port);
            System.out.println("Open your browser at http://localhost:" + port);

        } catch (Exception e) {
            System.err.println("Failed to start server");
            e.printStackTrace();
        }
    }

    private static void configureServer() {
        staticfiles("webroot");
    }

    private static void registerRoutes() {

        get("/hello", (req, res) -> {
            res.addHeader("Content-Type", "text/plain");

            String name = req.getValues("name");

            if (name == null || name.isEmpty()) {
                name = "World";
            }

            return "Hello " + name;
        });

        get("/pi", (req, res) -> {
            res.addHeader("Content-Type", "text/plain");
            return String.valueOf(Math.PI);
        });

    }

    private static int getPort() {

        String port = System.getenv("PORT");

        if (port != null) {
            return Integer.parseInt(port);
        }

        return 8080;
    }

    private static void printBanner() {

        System.out.println("=================================");
        System.out.println("      MicroFramework Server      ");
        System.out.println("=================================");
    }
}