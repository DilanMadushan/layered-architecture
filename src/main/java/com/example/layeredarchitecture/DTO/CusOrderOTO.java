package com.example.layeredarchitecture.DTO;

import java.time.LocalDate;

public class CusOrderOTO {
    private String name;
    private String address;
    private String Oid;
    private LocalDate date;

    public CusOrderOTO(String name, String address, String oid, LocalDate date) {
        this.name = name;
        this.address = address;
        Oid = oid;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOid() {
        return Oid;
    }

    public void setOid(String oid) {
        Oid = oid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
