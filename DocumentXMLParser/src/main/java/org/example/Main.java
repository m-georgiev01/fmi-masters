package org.example;

import java.lang.reflect.Field;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Item myItem = new Item();
        System.out.println(parseObject(myItem));
        System.out.println(parseObjectToJson(myItem));
    }

    public static String parseObject(Object parsableObject) throws IllegalAccessException {
        // get the object I will work with
        // and get the info for its class
        Class parsableObjectClass = parsableObject.getClass();

        // getFields is used when we want only the public ones
        // getDeclaredFields takes everything  - private, protected, etc
        Field[] fieldCollection = parsableObjectClass.getDeclaredFields();

        StringBuilder xmlBuilder = new StringBuilder();
        for (Field field : fieldCollection) {
            // don't throw on accessing private
            field.setAccessible(true);

            if (field.isAnnotationPresent(org.example.Documentable.class)){
                Documentable annotation = field.getAnnotation(org.example.Documentable.class);
                String fieldName = annotation.title().equals("_") ?
                        field.getName() :
                        annotation.title();

                xmlBuilder
                        .append("<")
                        .append(fieldName)
                        .append(">")
                        .append(field.get(parsableObject))
                        .append("</")
                        .append(fieldName)
                        .append(">");
            }
        }

        return xmlBuilder.toString();
    }

    public static String parseObjectToJson(Object parsableObject) throws IllegalAccessException {
        Class clazz = parsableObject.getClass();

        if (!clazz.isAnnotationPresent(org.example.JSONEntity.class)){
            return "{}";
        }

        Field[] fieldCollection = clazz.getDeclaredFields();

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        for (Field field : fieldCollection) {
            field.setAccessible(true);

            if (!field.isAnnotationPresent(org.example.JsonField.class)) {
                continue;
            }

            JsonField annotation = field.getAnnotation(org.example.JsonField.class);
            String fieldName = annotation.title().equals("_") ?
                    field.getName() :
                    annotation.title();

            String value = annotation.expectedType().equals(JSONExpectedType.STRING) ?
                    "\"" + field.get(parsableObject) + "\"" :
                    field.get(parsableObject).toString();

            jsonBuilder

                    .append("\"")
                    .append(fieldName)
                    .append("\":")
                    .append(value)
                    .append(",");
        }

        // remove last ','
        if (jsonBuilder.length() > 2) {
            jsonBuilder.setLength(jsonBuilder.length() - 1);
        }
        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }
}