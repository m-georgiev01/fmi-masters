package org.example.system;

import org.example.entities.ControllerMeta;
import org.example.entities.RequestInfo;
import org.example.stereotypes.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;

public class ApplicationLoader {
    private static ApplicationLoader instance = null;
    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    private HashMap<RequestInfo, ControllerMeta> controllerLookUpTable = new HashMap<RequestInfo, ControllerMeta>();
    private HashMap<Class, Object> injectableLookUpTable = new HashMap<Class, Object>();

    private ApplicationLoader() {
    }

    public static ApplicationLoader getInstance() {
        if (instance == null) {
            instance = new ApplicationLoader();
        }

        return instance;
    }

    public HashMap<RequestInfo, ControllerMeta> getControllerTable() {
        return controllerLookUpTable;
    }

    public ControllerMeta getController(RequestInfo requestInfo) {
        return this.controllerLookUpTable.get(requestInfo);
    }

    public Object getInjectable(Class classKey, boolean asSingleton) {
        if (asSingleton) {
            return this.getInjectable(classKey);
        }

        if (this.injectableLookUpTable.containsKey(classKey)) {
            return this.getInjectableInstance(classKey);
        }

        return null;
    }

    // As Singleton bu default
    private Object getInjectable(Class classKey) {
        Object resultInstance = this.injectableLookUpTable.get(classKey);

        if (resultInstance == null) {
            resultInstance = this.getInjectableInstance(classKey);
            this.injectableLookUpTable.put(classKey, resultInstance);
        }

        return resultInstance;
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

        if (currentClass.isAnnotationPresent(org.example.stereotypes.Injectable.class)) {
            this.parseInjectable(currentClass);
        }
    }

    private void parseController(Class currentClass) {
        Method[] controllerClassMethodCollection = currentClass.getMethods();

        for (Method method : controllerClassMethodCollection) {
            if (method.isAnnotationPresent(org.example.stereotypes.GetMapping.class)) {
                GetMapping annotation = method.getAnnotation(org.example.stereotypes.GetMapping.class);
                String methodEndpoint = annotation.value();
                String methodName = method.getName();

                // check for PathVariables
                Parameter[] methodParametersCollection = method.getParameters();
                HashMap<Integer, String> pathVariableIndex = new HashMap<>();

                for (int i = 0; i < methodParametersCollection.length; i++) {
                    Parameter parameter = methodParametersCollection[i];

                    if (parameter.isAnnotationPresent(org.example.stereotypes.PathVariable.class)) {
                        PathVariable pathVariable = parameter.getAnnotation(org.example.stereotypes.PathVariable.class);
                        pathVariableIndex.put(i, pathVariable.value());
                    }
                }

                this.controllerLookUpTable.put(
                        new RequestInfo("GET", methodEndpoint),
                        new ControllerMeta(currentClass, methodName, pathVariableIndex)
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

    private void parseInjectable(Class currentClass) {
        this.injectableLookUpTable.put(currentClass, null);
    }

    private Object getInjectableInstance(Class classKey) {
        try {
            return classKey.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}