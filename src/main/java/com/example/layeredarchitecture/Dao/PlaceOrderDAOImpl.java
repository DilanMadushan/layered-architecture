package com.example.layeredarchitecture.Dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PlaceOrderDAOImpl implements PlaceOrderDAO {

    private ItemDAO itemDAO = new ItemDAOImpl();
    private OrderDAO orderDAO = new OrderDAOImpl();
    private OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAOImpl();

    @Override
    public boolean placeOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {

            Connection connection = DBConnection.getDbConnection().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = orderDAO.saveOrder(orderId,orderDate,customerId);

            if (isOrderSaved) {
                boolean isODetailsSaved= orderDetailsDAO.saveOredrDetils(orderId,orderDetails);

                if (isODetailsSaved) {
                    boolean isItemUpdate = itemDAO.updateProducts(orderDetails);

                    if (isItemUpdate) {
                        connection.commit();
                        connection.setAutoCommit(true);
                        return true;
                    }else {
                        return false;
                    }
                }else {
                    return false;
                }
            }else {
                return false;
            }
    }

}
