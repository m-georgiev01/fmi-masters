package org.example.entities;

import java.util.HashMap;
import java.util.Objects;

public class RequestInfo {
    private String httpMethod;
    private String httpEndpoint;
    private String httpBody;
    private HashMap<String, String> headers = new HashMap<>();
    private HashMap<String, String> pathVariables = new HashMap<>();

    public RequestInfo() {
        this.httpMethod = "";
        this.httpEndpoint = "";
    }

    public RequestInfo(String httpMethod, String httpEndpoint) {
        this.httpMethod = httpMethod;
        this.httpEndpoint = httpEndpoint;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getHttpEndpoint() {
        return httpEndpoint;
    }

    public void setHttpEndpoint(String httpEndpoint) {
        this.httpEndpoint = httpEndpoint;
    }

    public String getHttpBody() {
        return httpBody;
    }

    public void setHttpBody(String httpBody) {
        this.httpBody = httpBody;
    }

    public void setHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public boolean hasContainer(String header) {
        return this.headers.containsKey(header);
    }

    public String getHeader(String header) {
        return this.headers.get(header);
    }

    public HashMap<String, String> getPathVariables() {
        return this.pathVariables;
    }

    public void setPathVariables(HashMap<String, String> pathVariables) {
        this.pathVariables = pathVariables;
    }

    public int getContentLength() {
        String value = this.getHeader("Content-Length");

        if (Objects.isNull(value)) {
            return 0;
        }
        
        return Integer.parseInt(value);
    }

    public boolean hasContent() {
        return this.getContentLength() > 0;
    }

    public boolean hasMethodAndEnpoint() {
        return !this.getHttpMethod().isEmpty() && !this.getHttpEndpoint().isEmpty();
    }

    public boolean isEmpty() {
        return this.httpMethod.isEmpty() || this.httpEndpoint.isEmpty();
    }

    public boolean isProcessable(String method, String endpoint) {
        if (!this.httpMethod.equals(method)) {
            return false;
        }

        return isTemplateEndpointMatchRequestEndpoint(endpoint);
    }

    public boolean isTemplateEndpointMatchRequestEndpoint(String requestEndpoint) {
        String[] templateEndpointPartCollection = this.getHttpEndpoint().split("/");
        String[] requestEndpointPartCollection = requestEndpoint.split("/");

        // check that the URLs have the same number of sections
        if (templateEndpointPartCollection.length != requestEndpointPartCollection.length) {
            return false;
        }

        // check section by section
        for (int i = 0; i < templateEndpointPartCollection.length; i++) {
            if (isUrlPartDynamic(templateEndpointPartCollection[i])) {
                String pathVariableName = extractUrlVariable(templateEndpointPartCollection[i]);
                String pathVariableValue = requestEndpointPartCollection[i];

                this.pathVariables.put(pathVariableName, pathVariableValue);

                continue;
            }

            if (!templateEndpointPartCollection[i].equals(requestEndpointPartCollection[i])) {
                return false;
            }
        }

        return true;
    }

    private boolean isUrlPartDynamic(String templateUrl) {
        return templateUrl.startsWith("{") && templateUrl.endsWith("}");
    }

    private String extractUrlVariable(String templateUrl) {
        return templateUrl.substring(1, templateUrl.length() - 1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.httpMethod, httpEndpoint);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        RequestInfo requestInfo = (RequestInfo) obj;

        return requestInfo.httpEndpoint.equals(this.httpEndpoint) &&
                requestInfo.httpMethod.equals(this.httpMethod);
    }
}