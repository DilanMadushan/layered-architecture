package com.example.layeredarchitecture.Dao;

import java.sql.SQLException;
import java.time.LocalDate;

public interface OrderDAO {
    String generateNewOrderId() throws SQLException, ClassNotFoundException;

    boolean saveOrder(String orderId, LocalDate orderDate, String customerId) throws SQLException, ClassNotFoundException;
}
