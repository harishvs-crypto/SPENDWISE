package com.spendwise.model;

public class User {
    private int id;
    private String username;
    private String password;
    private double budget;

    public User() {}

    public User(int id, String username, String password, double budget) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.budget = budget;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }
}
