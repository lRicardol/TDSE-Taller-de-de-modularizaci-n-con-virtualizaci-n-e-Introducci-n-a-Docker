package org.example.framework;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Responsible for parsing the raw HTTP request
 * and building a Request object.
 */
public class HttpRequestParser {

    public static Request parse(BufferedReader in) throws IOException {

        String requestLine = in.readLine();
        if (requestLine == null || requestLine.isEmpty()) {
            return null;
        }

        String[] parts = requestLine.split(" ");
        if (parts.length != 3) {
            return null;
        }

        String method = parts[0];
        String fullPath = parts[1];
        String version = parts[2];

        Request request = new Request(method, fullPath, version);

        // Read headers
        String headerLine;
        while ((headerLine = in.readLine()) != null && !headerLine.isEmpty()) {

            String[] headerParts = headerLine.split(":", 2);
            if (headerParts.length == 2) {
                request.addHeader(headerParts[0].trim(),
                        headerParts[1].trim());
            }
        }

        return request;
    }
}