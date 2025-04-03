package com.example.postal;

public class UserItem {
    private final String uid;
    private final String email;
    private final int orderCount;

    public UserItem(String uid, String email, int orderCount) {
        this.uid = uid;
        this.email = email;
        this.orderCount = orderCount;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public int getOrderCount() {
        return orderCount;
    }
}
