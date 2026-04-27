package com.example.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BookOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private int textbookId;
    private int quantity;
    private String status; // pending, confirmed, cancelled
    private String orderTime;
    private String remark;

    // 用于展示关联信息
    private transient User user;
    private transient Textbook textbook;

    public BookOrder() {
        this.orderTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.status = "pending";
    }

    public BookOrder(int id, int userId, int textbookId, int quantity, String remark) {
        this();
        this.id = id;
        this.userId = userId;
        this.textbookId = textbookId;
        this.quantity = quantity;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTextbookId() {
        return textbookId;
    }

    public void setTextbookId(int textbookId) {
        this.textbookId = textbookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Textbook getTextbook() {
        return textbook;
    }

    public void setTextbook(Textbook textbook) {
        this.textbook = textbook;
    }

    public double getTotalPrice() {
        if (textbook != null) {
            return textbook.getPrice() * quantity;
        }
        return 0;
    }
}
