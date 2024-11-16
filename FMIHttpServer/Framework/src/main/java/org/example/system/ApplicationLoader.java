package org.example.system;

import org.example.entities.ControllerMeta;
import org.example.entities.RequestInfo;
import org.example.stereotypes.DeleteMapping;
import org.example.stereotypes.GetMapping;
import org.example.stereotypes.PostMapping;
import org.example.stereotypes.PutMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ApplicationLoader {
    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    private HashMap<RequestInfo, ControllerMeta> controllerLookUpTable = new HashMap<RequestInfo, ControllerMeta>();

    public String executeController(RequestInfo httpRequest) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String httpMethod = httpRequest.getHttpMethod();
        String httpEndpoint = httpRequest.getHttpEndpoint();

        ControllerMeta controllerMethodReference = this.controllerLookUpTable.get(new RequestInfo(httpMethod, httpEndpoint));
        if (controllerMethodReference == null) {
            return "";
        }

        Class currentClass = controllerMethodReference.getClassReference();
        String methodName = controllerMethodReference.getMethodName();

        var controllerInstance = currentClass.getDeclaredConstructor().newInstance();

        return (String) currentClass
                .getMethod(methodName)
                .invoke(controllerInstance);
    }

    public void findAllClasses(String packageName) throws IOException, ClassNotFoundException {
        InputStream classLoaderStream = this.classLoader.getResourceAsStream(packageName.replace('.', '/'));
        BufferedReader classReader = new BufferedReader(new InputStreamReader(classLoaderStream));

        String packageReference;
        while ((packageReference = classReader.readLine()) != null) {
            if (!packageReference.contains(".class")) {
                findAllClasses(packageName + "." + packageReference);
            } else if (packageReference.contains(".class")) {
                this.classParser(packageReference, packageName);
            }
        }
    }

    private void classParser(String packageReference, String packageName) throws ClassNotFoundException {

        String className = packageReference.replace(".class", "");
        String fullClassName = packageName + "." + className;
        var currentClass = Class.forName(fullClassName);

        if (currentClass.isAnnotationPresent(org.example.stereotypes.Controller.class)) {
            this.parseController(currentClass);
        }
    }

    private void parseController(Class currentClass) {
        Method[] controllerClassMethodCollection = currentClass.getMethods();

        for (Method method : controllerClassMethodCollection) {
            if (method.isAnnotationPresent(org.example.stereotypes.GetMapping.class)) {
                GetMapping annotation = method.getAnnotation(org.example.stereotypes.GetMapping.class);
                String methodEndpoint = annotation.value();
                String methodName = method.getName();

                this.controllerLookUpTable.put(
                        new RequestInfo("GET", methodEndpoint),
                        new ControllerMeta(currentClass, methodName)
                );
            }

            if (method.isAnnotationPresent(org.example.stereotypes.PostMapping.class)) {
                PostMapping annotation = method.getAnnotation(org.example.stereotypes.PostMapping.class);
                String methodEndpoint = annotation.value();
                String methodName = method.getName();

                this.controllerLookUpTable.put(
                        new RequestInfo("POST", methodEndpoint),
                        new ControllerMeta(currentClass, methodName)
                );
            }

            if (method.isAnnotationPresent(org.example.stereotypes.PutMapping.class)) {
                PutMapping annotation = method.getAnnotation(org.example.stereotypes.PutMapping.class);
                String methodEndpoint = annotation.value();
                String methodName = method.getName();

                this.controllerLookUpTable.put(
                        new RequestInfo("PUT", methodEndpoint),
                        new ControllerMeta(currentClass, methodName)
                );
            }

            if (method.isAnnotationPresent(org.example.stereotypes.DeleteMapping.class)) {
                DeleteMapping annotation = method.getAnnotation(org.example.stereotypes.DeleteMapping.class);
                String methodEndpoint = annotation.value();
                String methodName = method.getName();

                this.controllerLookUpTable.put(
                        new RequestInfo("DELETE", methodEndpoint),
                        new ControllerMeta(currentClass, methodName)
                );
            }
        }
    }
}