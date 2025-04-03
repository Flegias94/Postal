package com.example.postal;

public class OrderModel {
    private String id;
    private String userId;
    private String receiverName;
    private String receiverAdress;
    private double weight;
    private int quantity;
    private double price;
    private String status;

    public OrderModel() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

    public String getReceiverAdress() { return receiverAdress; }
    public void setReceiverAdress(String receiverAdress) { this.receiverAdress = receiverAdress; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
