package com.example.layeredarchitecture.Dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public interface CustomerDAO {

     ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;


     boolean saveCustomer(String id, String name, String address) throws SQLException, ClassNotFoundException;


     boolean updateCustomer(String id, String name, String address) throws SQLException, ClassNotFoundException ;


     boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException ;


     boolean existCustomer(String id) throws SQLException, ClassNotFoundException ;


     String genarateId() throws SQLException, ClassNotFoundException ;

    CustomerDTO serchCustomer(String newValue) throws SQLException, ClassNotFoundException;

     boolean checkCustomer(String id) throws SQLException, ClassNotFoundException;

    ObservableList<String> getAllId() throws SQLException, ClassNotFoundException;
}
