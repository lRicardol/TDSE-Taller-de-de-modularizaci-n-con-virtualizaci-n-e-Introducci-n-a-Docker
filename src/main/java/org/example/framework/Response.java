package org.example.framework;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an HTTP Response abstraction.
 * Allows setting status code, headers and body.
 */
public class Response {

    private int status = 200;
    private String statusMessage = "OK";
    private Map<String, String> headers = new HashMap<>();
    private String body = "";

    /**
     * Sets HTTP status code and message.
     */
    public void setStatus(int status, String message) {
        this.status = status;
        this.statusMessage = message;
    }

    /**
     * Returns HTTP status code.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Returns HTTP status message.
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * Adds a header to the response.
     */
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    /**
     * Returns all headers.
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Sets response body.
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Returns response body.
     */
    public String getBody() {
        return body;
    }
}