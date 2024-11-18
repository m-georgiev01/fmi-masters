package org.example.system;

import org.example.entities.ControllerMeta;
import org.example.entities.RequestInfo;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class HttpProcessor {
    private ApplicationLoader appLoader = ApplicationLoader.getInstance();

    public String executeController(RequestInfo httpRequest) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
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
            return "Internal Server Error";
        }

        Class currentClass = controllerMethodReference.getClassReference();
        String methodName = controllerMethodReference.getMethodName();
        Class<?>[] methodSigniture = this.buildMethodParameterTypes(controllerMethodReference);
        Object[] arguments = this.buildMethodArguments(controllerMethodReference, httpRequest);

        var controllerInstance = currentClass.getDeclaredConstructor().newInstance();

        return (String) currentClass
                .getMethod(methodName, methodSigniture)
                .invoke(controllerInstance, arguments);
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
