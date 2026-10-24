package com.spendwise.model;

import java.sql.Date;

public class Expense {
    private int id;
    private int userId;
    private double amount;
    private String category;
    private Date date;
    private String paymentMode;
    private String description;

    public Expense() {}

    public Expense(int id, int userId, double amount, String category, Date date, String paymentMode, String description) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.paymentMode = paymentMode;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
