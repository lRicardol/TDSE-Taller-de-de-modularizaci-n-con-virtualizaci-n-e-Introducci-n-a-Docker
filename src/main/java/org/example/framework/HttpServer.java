package org.example.framework;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Core HTTP server.
 * Responsible only for:
 * - Accepting connections
 * - Delegating parsing
 * - Delegating routing
 * - Delegating static file handling
 * - Delegating response building
 */
public class HttpServer {

    private int port;
    private Router router;
    private StaticFileHandler staticFileHandler;

    public HttpServer(int port, Router router, String staticFolder) {
        this.port = port;
        this.router = router;
        this.staticFileHandler = new StaticFileHandler(staticFolder);
    }

    public void start() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server running on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {

        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                OutputStream out = clientSocket.getOutputStream()
        ) {

            Request request = HttpRequestParser.parse(in);

            if (request == null) {
                clientSocket.close();
                return;
            }

            Response response = new Response();
            String body = null;

            RouteHandler handler =
                    router.getRoute(request.getMethod(), request.getPath());

            if (handler != null) {
                body = handler.handle(request, response);
            }
            else {
                body = staticFileHandler.handle(request.getPath(), response);
            }

            if (body == null) {
                response.setStatus(404, "Not Found");
                body = "<h1>404 Not Found</h1>";
                response.addHeader("Content-Type", "text/html");
            }

            byte[] httpResponse =
                    HttpResponseBuilder.build(response, body);

            out.write(httpResponse);
            out.flush();

            clientSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}