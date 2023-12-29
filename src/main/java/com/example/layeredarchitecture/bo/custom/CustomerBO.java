package com.example.layeredarchitecture.bo.custom;

import com.example.layeredarchitecture.bo.SuperBo;
import com.example.layeredarchitecture.DTO.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBo {
    ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException;

    boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException ;

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    String genarateCustomerId() throws SQLException, ClassNotFoundException ;
}
