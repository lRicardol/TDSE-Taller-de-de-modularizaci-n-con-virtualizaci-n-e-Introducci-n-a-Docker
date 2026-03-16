package org.example.framework.controllers;

import org.example.framework.annotations.GetMapping;
import org.example.framework.annotations.RequestParam;
import org.example.framework.annotations.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Example controller demonstrating the use of annotations
 * similar to Spring Boot controllers.
 */
@RestController
public class GreetingController {

    private static final String template = "Hola %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
     * Example endpoint:
     * http://localhost:35000/greeting?name=Carlos
     */
    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(value = "name", defaultValue = "World") String name) {

        long id = counter.incrementAndGet();
        return String.format(template, name) + " (#" + id + ")";
    }

    /**
     * Root endpoint
     */
    @GetMapping("/")
    public String index() {
        return "Servidor IoC funcionando correctamente 🚀";
    }
}