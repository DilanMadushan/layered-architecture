package com.example.layeredarchitecture.Entity;

import java.math.BigDecimal;

public class OrderDetail {
    private String OrderId;
    private String itemCode;
    private int qty;
    private BigDecimal unitPrice;

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public OrderDetail() {
    }

    public OrderDetail(String itemCode, int qty, BigDecimal unitPrice) {
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public OrderDetail(String orderId, String itemCode, int qty, BigDecimal unitPrice) {
        OrderId = orderId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }
}
