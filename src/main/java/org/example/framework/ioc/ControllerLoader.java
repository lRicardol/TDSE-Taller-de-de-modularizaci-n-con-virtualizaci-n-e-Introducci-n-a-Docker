package org.example.framework.ioc;

import org.example.framework.annotations.GetMapping;
import org.example.framework.annotations.RequestParam;
import org.example.framework.annotations.RestController;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads controllers dynamically using Java Reflection.
 * Detects classes annotated with @RestController and
 * maps methods annotated with @GetMapping.
 */
public class ControllerLoader {

    private static final Map<String, Handler> services = new HashMap<>();

    /**
     * Loads a controller class dynamically.
     */
    public static void loadController(String className) {

        try {

            Class<?> controllerClass = Class.forName(className);

            if (controllerClass.isAnnotationPresent(RestController.class)) {

                Object controllerInstance = controllerClass
                        .getDeclaredConstructor()
                        .newInstance();

                for (Method method : controllerClass.getDeclaredMethods()) {

                    if (method.isAnnotationPresent(GetMapping.class)) {

                        GetMapping mapping = method.getAnnotation(GetMapping.class);
                        String path = mapping.value();

                        services.put(path, new Handler(controllerInstance, method));

                        System.out.println("Endpoint cargado: " + path +
                                " -> " + method.getName());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles HTTP requests invoking controller methods.
     */
    public static String handleRequest(String path, Map<String, String> queryParams) {

        Handler handler = services.get(path);

        if (handler == null) {
            return "404 Not Found";
        }

        try {

            Method method = handler.method;
            Object controller = handler.controller;

            Parameter[] parameters = method.getParameters();
            Object[] args = new Object[parameters.length];

            for (int i = 0; i < parameters.length; i++) {

                Parameter param = parameters[i];

                if (param.isAnnotationPresent(RequestParam.class)) {

                    RequestParam requestParam =
                            param.getAnnotation(RequestParam.class);

                    String paramName = requestParam.value();
                    String defaultValue = requestParam.defaultValue();

                    String value = queryParams.getOrDefault(paramName, defaultValue);

                    args[i] = value;
                }
            }

            Object response = method.invoke(controller, args);

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "500 Internal Server Error";
        }
    }

    /**
     * Internal handler structure.
     */
    private static class Handler {

        Object controller;
        Method method;

        Handler(Object controller, Method method) {
            this.controller = controller;
            this.method = method;
        }
    }
}