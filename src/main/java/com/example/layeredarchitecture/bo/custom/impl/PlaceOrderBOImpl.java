package com.example.layeredarchitecture.bo.custom.impl;

import com.example.layeredarchitecture.Dao.DAOFactory;
import com.example.layeredarchitecture.Dao.custom.CustomerDAO;
import com.example.layeredarchitecture.Dao.custom.ItemDAO;
import com.example.layeredarchitecture.Dao.custom.OrderDAO;
import com.example.layeredarchitecture.Dao.custom.OrderDetailsDAO;
import com.example.layeredarchitecture.Entity.Customer;
import com.example.layeredarchitecture.Entity.Item;
import com.example.layeredarchitecture.Entity.Order;
import com.example.layeredarchitecture.Entity.OrderDetail;
import com.example.layeredarchitecture.bo.custom.PlaceOrderBO;
import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.DTO.CustomerDTO;
import com.example.layeredarchitecture.DTO.ItemDTO;
import com.example.layeredarchitecture.DTO.OrderDTO;
import com.example.layeredarchitecture.DTO.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DTOTypes.CUSTOMER);
    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DTOTypes.ITEM);
    private OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DTOTypes.ORDER);
    private OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DTOTypes.ORDERDETAILS);
    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
        Connection connection = null;

            connection = DBConnection.getDbConnection().getConnection();
            connection.setAutoCommit(false);

//            boolean isOrderSaved = orderDAO.saveOrder(orderId,orderDate,customerId);
//
//            if (isOrderSaved) {
//                boolean isODetailsSaved= orderDetailsDAO.saveOredrDetils(orderId,orderDetails);
//
//                if (isODetailsSaved) {
//                    boolean isItemUpdate = itemDAO.updateProducts(orderDetails);
//
//                    if (isItemUpdate) {
//                        return true;
//
//                    }else {
//                        return false;
//                    }
//                }else {
//                    return false;
//                }
//            }else {
//                return false;
//            }
            boolean isOrderSaved = orderDAO.save(new Order(orderId,orderDate,customerId));


            boolean isODetailsSaved= false;

            if (isOrderSaved) {
                for (OrderDetailDTO dto: orderDetails) {
                    dto.setOrderId(orderId);
                    isODetailsSaved= orderDetailsDAO.save(new OrderDetail(dto.getOrderId(),dto.getItemCode(), dto.getQty(),dto.getUnitPrice()));
                }


                boolean isItemUpdate = false;

                if (isODetailsSaved) {
                    for (OrderDetailDTO dto: orderDetails) {

                        Item item = itemDAO.search(dto.getItemCode());
                        item.setQtyOnHand(item.getQtyOnHand() - dto.getQty());

                        isItemUpdate = itemDAO.update(new Item(item.getCode(),item.getDescription(),item.getUnitPrice(), item.getQtyOnHand()));
                    }

                    if (isItemUpdate) {
                        connection.commit();
                        connection.setAutoCommit(true);
                        return true;

                    }else {
                        connection.rollback();
                        connection.setAutoCommit(true);
                        return false;
                    }
                }else {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }else {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
    }
    @Override
    public CustomerDTO searchCustomer(String newValue) throws SQLException, ClassNotFoundException{
       Customer dto = customerDAO.search(newValue);
       return new CustomerDTO(dto.getId(),dto.getName(),dto.getAddress());
    }

    @Override
    public ItemDTO searchItem(String newValue) throws SQLException, ClassNotFoundException {
        Item dto = itemDAO.search(newValue);
        return new ItemDTO(dto.getCode(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand());
    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return orderDAO.exist(code);
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String genarateOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.genarateId();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> List = customerDAO.getAll();
        ArrayList<CustomerDTO> dtoList = new ArrayList<>();

        for (Customer dto: List) {
            dtoList.add(new CustomerDTO(dto.getId(),dto.getName(),dto.getAddress()));
        }
        return dtoList;
    }

    @Override
    public ArrayList<ItemDTO> getAllOrder() throws SQLException, ClassNotFoundException {
        ArrayList<Item> list =  itemDAO.getAll();
        ArrayList<ItemDTO> dtoList = new ArrayList<>();

        for (Item dto: list) {
            dtoList.add(new ItemDTO(dto.getCode(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand()));
        }
        return dtoList;
    }

}
