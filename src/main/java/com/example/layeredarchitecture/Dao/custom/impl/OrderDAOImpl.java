package com.example.layeredarchitecture.Dao.custom.impl;

import com.example.layeredarchitecture.Dao.SQLUtil;
import com.example.layeredarchitecture.Dao.custom.OrderDAO;
import com.example.layeredarchitecture.DTO.OrderDTO;
import com.example.layeredarchitecture.Entity.Order;

import java.sql.*;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    SQLUtil sqlUtil = new SQLUtil();
    @Override
    public String genarateId()throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getDbConnection().getConnection();
//        Statement stm = connection.createStatement();
//        ResultSet rst = stm.executeQuery("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");
//
//        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";

        ResultSet resultSet = sqlUtil.execute("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");
        return resultSet.next() ? String.format("OID-%03d", (Integer.parseInt(resultSet.getString("oid").replace("OID-", "")) + 1)) : "OID-001";
    }

    @Override
    public boolean save(Order dto) throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getDbConnection().getConnection();
//        PreparedStatement stm = connection.prepareStatement("INSERT INTO `Orders` (oid, date, customerID) VALUES (?,?,?)");
//        stm.setString(1,dto.getOrderId() );
//        stm.setDate(2, Date.valueOf(dto.getOrderDate()));
//        stm.setString(3,dto.getCustomerId());

        return sqlUtil.execute("INSERT INTO `Orders` (oid, date, customerID) VALUES (?,?,?)"
                ,dto.getOrderId(),Date.valueOf(dto.getOrderDate()),dto.getCustomerId());
    }
    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(Order dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

}
