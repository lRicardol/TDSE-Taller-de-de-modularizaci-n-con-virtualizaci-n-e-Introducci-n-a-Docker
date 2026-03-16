package org.example.framework;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Responsible for serving static files
 * from the configured static folder.
 */
public class StaticFileHandler {

    private String staticFolder;

    public StaticFileHandler(String staticFolder) {
        this.staticFolder = staticFolder;
    }

    public String handle(String path, Response response) {

        try {

            if (path.equals("/")) {
                path = "/index.html";
            }

            String fullPath = "target/classes/" + staticFolder + path;

            if (Files.exists(Paths.get(fullPath))) {

                response.addHeader("Content-Type",
                        getContentType(path));

                return new String(
                        Files.readAllBytes(Paths.get(fullPath))
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getContentType(String path) {

        if (path.endsWith(".html")) return "text/html";
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".js")) return "application/javascript";
        if (path.endsWith(".png")) return "image/png";
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
        if (path.endsWith(".gif")) return "image/gif";

        return "text/plain";
    }
}