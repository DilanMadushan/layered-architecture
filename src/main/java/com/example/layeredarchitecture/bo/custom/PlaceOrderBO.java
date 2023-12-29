package com.example.layeredarchitecture.bo.custom;

import com.example.layeredarchitecture.bo.SuperBo;
import com.example.layeredarchitecture.DTO.CustomerDTO;
import com.example.layeredarchitecture.DTO.ItemDTO;
import com.example.layeredarchitecture.DTO.OrderDetailDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface PlaceOrderBO extends SuperBo {
    boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(String newValue) throws SQLException, ClassNotFoundException ;

    ItemDTO searchItem(String newValue) throws SQLException, ClassNotFoundException ;

    boolean existItem(String code) throws SQLException, ClassNotFoundException ;

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException ;

    String genarateOrderId()throws SQLException, ClassNotFoundException ;
    ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException ;

    ArrayList<ItemDTO> getAllOrder() throws SQLException, ClassNotFoundException;
}
