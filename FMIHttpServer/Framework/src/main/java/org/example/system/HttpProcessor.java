package org.example.system;

import org.example.entities.ControllerMeta;
import org.example.entities.RequestInfo;
import org.example.stereotypes.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class HttpProcessor {
    private ApplicationLoader appLoader = ApplicationLoader.getInstance();

    public ResponseMessage executeController(RequestInfo httpRequest) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String httpMethod = httpRequest.getHttpMethod();
        String httpRequestEndpoint = httpRequest.getHttpEndpoint();
        ControllerMeta controllerMethodReference = null;

        for (RequestInfo contollerRequestInfo : this.appLoader.getControllerTable().keySet()) {
            if (contollerRequestInfo.isProcessable(httpMethod, httpRequestEndpoint)) {
                controllerMethodReference = this.appLoader.getController(contollerRequestInfo);
                httpRequest.setPathVariables(contollerRequestInfo.getPathVariables());
                break;
            }
        }

        if (controllerMethodReference == null) {
            return new ResponseMessage("No provided controller for this request", 500);
        }

        Class currentClass = controllerMethodReference.getClassReference();
        String methodName = controllerMethodReference.getMethodName();
        Class<?>[] methodSigniture = this.buildMethodParameterTypes(controllerMethodReference);
        Object[] arguments = this.buildMethodArguments(controllerMethodReference, httpRequest);

        var controllerInstance = currentClass.getDeclaredConstructor().newInstance();
        this.processAutowiredServices(controllerInstance);

        Object returnMessage = currentClass.getMethod(methodName, methodSigniture).invoke(controllerInstance, arguments);
        if (String.class.isInstance(returnMessage)) {
            return new ResponseMessage((String) returnMessage);
        }

        if (ResponseMessage.class.isInstance(returnMessage)) {
            return (ResponseMessage) returnMessage;
        }

        return new ResponseMessage("Something went wrong", 500);
    }

    private void processAutowiredServices(Object rootInstance) throws IllegalAccessException {
        // to add DI - go through all the fields in the controller and check for @Autowired
        Field[] fieldCollection = rootInstance.getClass().getDeclaredFields();
        for (Field field : fieldCollection) {
            if (field.isAnnotationPresent(org.example.stereotypes.Autowired.class)) {
                Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
                boolean isSingleton = autowiredAnnotation.isSingleton();

                // get field Type and search for it in already found DIs materials (Injectables)
                field.setAccessible(true);
                Class injectableMaterial = field.getType();
                var injectableInstance = this.appLoader.getInjectable(injectableMaterial, isSingleton);

                field.set(rootInstance, injectableInstance);

                processAutowiredServices(injectableInstance);
            }
        }
    }

    private Class<?>[] buildMethodParameterTypes(ControllerMeta controllerMeta) {
        HashMap<Integer, String> pathVariableIndex = controllerMeta.getPathVariableIndex();
        Class<?>[] parameterTypes = new Class<?>[pathVariableIndex.size()];

        for (int index : pathVariableIndex.keySet()) {
            parameterTypes[index] = int.class;
        }

        return parameterTypes;
    }

    private Object[] buildMethodArguments(ControllerMeta controllerMeta, RequestInfo requestInfo) {
        HashMap<Integer, String> pathVariableIndex = controllerMeta.getPathVariableIndex();
        HashMap<String, String> pathVariables = requestInfo.getPathVariables();
        Object[] arguments = new Object[pathVariableIndex.size()];

        for (int index : pathVariableIndex.keySet()) {
            String variableName = pathVariableIndex.get(index);
            String variableValue = pathVariables.get(variableName);

            if (variableValue != null) {
                arguments[index] = Integer.parseInt(variableValue);
            }
        }

        return arguments;
    }
}
