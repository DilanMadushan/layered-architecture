package com.example.layeredarchitecture.Dao;

import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsDAO {
    boolean saveOredrDetils(String orderId,List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException;
}
