package org.example.p02solarparkapi.entities;

public class Project {
    public static final String TABLE_NAME = "td_projects";

    public static class columns {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String CUSTOMER_ID = "customer_id";
        public static final String COST = "cost";
        public static final String IS_ACTIVE = "is_active";
    }

    private int id;
    private String name;
    private int customer_id;
    private double cost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getCustomerId() {
        return customer_id;
    }

    public void setCustomerId(int customerId) {
        this.customer_id = customerId;
    }
}
