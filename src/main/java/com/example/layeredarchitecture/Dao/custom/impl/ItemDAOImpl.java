package com.example.layeredarchitecture.Dao.custom.impl;

import com.example.layeredarchitecture.Dao.SQLUtil;
import com.example.layeredarchitecture.Dao.custom.ItemDAO;
import com.example.layeredarchitecture.DTO.ItemDTO;
import com.example.layeredarchitecture.Entity.Item;

import java.sql.*;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {

    private SQLUtil sqlUtil = new SQLUtil();
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getDbConnection().getConnection();
//        Statement stm = connection.createStatement();
//        ResultSet rst = stm.executeQuery("SELECT * FROM Item");
//
//        ArrayList<ItemDTO> dto = new ArrayList<>();
//
//        while (rst.next()) {
//            dto.add(new ItemDTO(rst.getString("code"), rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand")));
//        }
//
//        return dto;
        ResultSet rst = sqlUtil.execute("SELECT * FROM Item");
        ArrayList<Item> dto = new ArrayList<>();

        while (rst.next()) {
            dto.add(new Item(rst.getString("code"), rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand")));
        }

        return dto;
    }

    public boolean delete(String code) throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getDbConnection().getConnection();
//        PreparedStatement pstm = connection.prepareStatement("DELETE FROM Item WHERE code=?");
//        pstm.setString(1, code);
//        return pstm.executeUpdate() > 0;

        return sqlUtil.execute("DELETE FROM Item WHERE code=?",code);
    }

    public boolean save(Item dto) throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getDbConnection().getConnection();
//        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)");
//        pstm.setString(1, dto.getCode());
//        pstm.setString(2, dto.getDescription());
//        pstm.setBigDecimal(3, dto.getUnitPrice());
//        pstm.setInt(4, dto.getQtyOnHand());
//        return pstm.executeUpdate() > 0;

        return sqlUtil.execute("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)",dto.getCode(),dto.getDescription()
                ,dto.getUnitPrice(),dto.getQtyOnHand());
    }

    public boolean exist(String code) throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getDbConnection().getConnection();
//        PreparedStatement pstm = connection.prepareStatement("SELECT code FROM Item WHERE code=?");
//        pstm.setString(1, code);
//        return pstm.executeQuery().next();

        ResultSet resultSet = sqlUtil.execute("SELECT code FROM Item WHERE code=?",code);
        return resultSet.next();
    }

    public String genarateId() throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getDbConnection().getConnection();
//        ResultSet rst = connection.createStatement().executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");
//        if (rst.next()) {
//            String id = rst.getString("code");
//            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
//            return String.format("I00-%03d", newItemId);
//        } else {
//            return "I00-001";
//        }
        ResultSet rst = sqlUtil.execute("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("code");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }

    }

    public boolean update(Item dto) throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getDbConnection().getConnection();
//        PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
//        pstm.setString(1, dto.getDescription());
//        pstm.setBigDecimal(2, dto.getUnitPrice());
//        pstm.setInt(3, dto.getQtyOnHand());
//        pstm.setString(4, dto.getCode());
//        return pstm.executeUpdate() > 0;
        return sqlUtil.execute("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?",
                dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand(),dto.getCode());
    }

    @Override
    public Item search(String newValue) throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getDbConnection().getConnection();
//        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Item WHERE code=?");
//        pstm.setString(1, newItemCode + "");
//        ResultSet rst = pstm.executeQuery();
//        rst.next();
//        return new ItemDTO(newItemCode + "", rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));

        ResultSet resultSet = sqlUtil.execute("SELECT * FROM Item WHERE code=?",newValue);
        resultSet.next();
        return new Item(newValue + "", resultSet.getString("description"), resultSet.getBigDecimal("unitPrice"), resultSet.getInt("qtyOnHand"));
    }

}
