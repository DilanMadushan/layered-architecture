package com.example.layeredarchitecture.Dao.custom.impl;

import com.example.layeredarchitecture.Dao.SQLUtil;
import com.example.layeredarchitecture.Dao.custom.OrderDetailsDAO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    SQLUtil sqlUtil = new SQLUtil();

    @Override
    public ArrayList<OrderDetailDTO> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OrderDetailDTO dto) throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getDbConnection().getConnection();
//        PreparedStatement stm = connection.prepareStatement("INSERT INTO OrderDetails (oid, itemCode, unitPrice, qty) VALUES (?,?,?,?)");
//
//            stm.setString(1,orderId);
//            stm.setString(2, dto.getItemCode());
//            stm.setBigDecimal(3, dto.getUnitPrice());
//            stm.setInt(4, dto.getQty());
//
//            if (stm.executeUpdate() != 1) {
//                connection.rollback();
//                connection.setAutoCommit(true);
//                return false;
//            }
//            return true;
//    }
        return sqlUtil.execute("INSERT INTO OrderDetails (oid, itemCode, unitPrice, qty) VALUES (?,?,?,?)"
                , dto.getOrderId(), dto.getItemCode(), dto.getUnitPrice(), dto.getQty());

    }

    @Override
    public boolean update(OrderDetailDTO dto) throws SQLException, ClassNotFoundException {
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
    public String genarateId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public OrderDetailDTO search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }
}
