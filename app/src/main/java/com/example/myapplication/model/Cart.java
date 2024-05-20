package com.example.myapplication.model;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;

public class Cart implements Serializable {
    //
    private int _id;
    private String orderId;
    private String user;
    private String image;
    private String title;
    private Double price;
    private int quantity;
    private Double total;
    private ArrayList<String> options;
    private String paymentMethod;
    private float starEvaluate;
    private String contentEvaluate;
    private Boolean isConfirmed;
    private Boolean isOrdered;
    private Boolean isDelivered;
    private Boolean isCompleted;
    public Cart(int _id, String orderId, String user, String image, String title, Double price, int quantity, Double total, ArrayList<String> options, String paymentMethod, float starEvaluate, String contentEvaluate, Boolean isConfirmed, Boolean isOrdered,  Boolean isDelivered, Boolean isCompleted, Date updatedAt) {
        this._id = _id;
        this.orderId = orderId;
        this.user = user;
        this.image = image;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        this.options = options;
        this.paymentMethod = paymentMethod;
        this.starEvaluate = starEvaluate;
        this.contentEvaluate = contentEvaluate;
        this.isConfirmed = isConfirmed;
        this.isOrdered = isOrdered;
        this.isDelivered = isDelivered;
        this.isCompleted = isCompleted;
    }
    public Cart(String user, Product product, int quantity) {
        this.user = user;
        this.image = product.getImage();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.quantity = quantity;
        this.total = product.getPrice() * quantity;
        this.isConfirmed = false;
        this.isOrdered = false;
        this.isDelivered = false;
        this.isCompleted = false;
    }
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getTotal() {
        return price * quantity;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public float getStarEvaluate() {
        return starEvaluate;
    }

    public String getContentEvaluate() {
        return contentEvaluate;
    }

    public Boolean getConfirmed() {
        return isConfirmed;
    }
    public Boolean getOrdered() {
        return isOrdered;
    }

    public void setOrdered(Boolean ordered) {
        isOrdered = ordered;
    }
    public Boolean getDelivered() {
        return isDelivered;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
}
