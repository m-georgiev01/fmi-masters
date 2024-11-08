package org.example;

import org.example.controllers.CustomerController;
import org.example.controllers.HomeController;
import org.example.system.ApplicationLoader;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final String NEW_LINE = "\n\r";

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var appLoader = new ApplicationLoader();
        appLoader.findAllClasses("org.example");

        ServerSocket serverSocket = new ServerSocket(1423);

        while (serverSocket.isBound()) {
            Socket clientSocket = serverSocket.accept();

            InputStream request = clientSocket.getInputStream();
            OutputStream response = clientSocket.getOutputStream();

            // reader for the incoming data
            InputStreamReader reader = new InputStreamReader(request);

            // wrap it in a buffer
            BufferedReader httpRequestReader = new BufferedReader(reader);

            // read request line by line and handle it
            String currentLine;
            String httpMethod = "";
            String httpEndpoint = "";

            while((currentLine = httpRequestReader.readLine()) != null){
                String[] httpHeaderTitleCollection = currentLine.split(" ");
                httpMethod = httpHeaderTitleCollection[0];
                httpEndpoint = httpHeaderTitleCollection[1];
                break;
            }

            // request interpreter
            String controllerMessage = appLoader.executeController(httpMethod, httpEndpoint);

            String message = buildHTTPResponse(controllerMessage);
            response.write(message.getBytes());

            response.close();
            request.close();
            clientSocket.close();
        }
    }

    private static String buildHTTPResponse(String body){
        return "HTTP/1.1 200 OK" + NEW_LINE +
                "Access-Control-Allow-Origin: *" + NEW_LINE +
                "Content-Length: " + body.getBytes(StandardCharsets.UTF_8).length + NEW_LINE +
                "Content-Type: text/html" + NEW_LINE + NEW_LINE +
                body + NEW_LINE + NEW_LINE;
    }
}