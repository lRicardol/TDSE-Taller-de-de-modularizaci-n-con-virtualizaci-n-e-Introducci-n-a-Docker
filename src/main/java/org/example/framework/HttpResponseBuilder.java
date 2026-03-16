package org.example.framework;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Responsible for building a valid HTTP response message.
 */
public class HttpResponseBuilder {

    public static byte[] build(Response response, String body) {

        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);

        StringBuilder httpResponse = new StringBuilder();

        httpResponse.append("HTTP/1.1 ")
                .append(response.getStatus())
                .append(" ")
                .append(response.getStatusMessage())
                .append("\r\n");

        httpResponse.append("Date: ")
                .append(ZonedDateTime.now()
                        .format(DateTimeFormatter.RFC_1123_DATE_TIME))
                .append("\r\n");

        httpResponse.append("Server: MicroFramework/1.0\r\n");
        httpResponse.append("Content-Length: ")
                .append(bodyBytes.length)
                .append("\r\n");

        for (var entry : response.getHeaders().entrySet()) {
            httpResponse.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\r\n");
        }

        httpResponse.append("\r\n");

        byte[] headerBytes =
                httpResponse.toString().getBytes(StandardCharsets.UTF_8);

        byte[] fullResponse =
                new byte[headerBytes.length + bodyBytes.length];

        System.arraycopy(headerBytes, 0,
                fullResponse, 0, headerBytes.length);

        System.arraycopy(bodyBytes, 0,
                fullResponse, headerBytes.length, bodyBytes.length);

        return fullResponse;
    }
}