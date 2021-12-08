package com.ecommerce.app.models;

import java.io.Serializable;

public class CartModel implements Serializable {
    String CurrentDate, CurrentTime, ProductName, ProductPrice, TotalPrice;
    int TotalQuantity;
    String documentId;

    public CartModel() {
    }

    public CartModel(String currentDate, String currentTime, String productName, String productPrice, String totalPrice, int totalQuantity) {
        CurrentDate = currentDate;
        CurrentTime = currentTime;
        ProductName = productName;
        ProductPrice = productPrice;
        TotalPrice = totalPrice;
        TotalQuantity = totalQuantity;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(String currentDate) {
        CurrentDate = currentDate;
    }

    public String getCurrentTime() {
        return CurrentTime;
    }

    public void setCurrentTime(String currentTime) {
        CurrentTime = currentTime;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public int getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        TotalQuantity = totalQuantity;
    }
}
