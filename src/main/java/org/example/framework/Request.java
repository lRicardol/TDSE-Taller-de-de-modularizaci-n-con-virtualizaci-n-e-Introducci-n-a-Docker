package org.example.framework;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an HTTP Request abstraction.
 * Encapsulates method, path, headers and query parameters.
 */
public class Request {

    private String method;
    private String path;
    private String httpVersion;

    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> queryParams = new HashMap<>();

    public Request(String method, String fullPath, String httpVersion) {
        this.method = method;
        this.httpVersion = httpVersion;

        parsePathAndQuery(fullPath);
    }

    /**
     * Splits the path and extracts query parameters.
     */
    private void parsePathAndQuery(String fullPath) {

        if (fullPath.contains("?")) {

            String[] parts = fullPath.split("\\?", 2);
            this.path = parts[0];

            String queryString = parts[1];
            String[] pairs = queryString.split("&");

            for (String pair : pairs) {
                String[] keyValue = pair.split("=", 2);
                if (keyValue.length == 2) {
                    queryParams.put(keyValue[0], keyValue[1]);
                }
            }

        } else {
            this.path = fullPath;
        }
    }

    /**
     * Adds a header to the request.
     */
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    /**
     * Returns HTTP method (GET, POST, etc.)
     */
    public String getMethod() {
        return method;
    }

    /**
     * Returns request path without query string.
     */
    public String getPath() {
        return path;
    }

    /**
     * Returns HTTP version (HTTP/1.1).
     */
    public String getHttpVersion() {
        return httpVersion;
    }

    /**
     * Returns a specific header value.
     */
    public String getHeader(String key) {
        return headers.get(key);
    }

    /**
     * Returns query parameter value.
     * Example: /hello?name=Pedro → getValues("name") = "Pedro"
     */
    public String getValues(String key) {
        return queryParams.get(key);
    }

    /**
     * Returns all query parameters.
     */
    public Map<String, String> getQueryParams() {
        return queryParams;
    }
}