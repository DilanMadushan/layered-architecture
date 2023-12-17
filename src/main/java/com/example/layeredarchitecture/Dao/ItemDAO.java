package com.example.layeredarchitecture.Dao;

import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface ItemDAO {
    ArrayList<ItemDTO> loadAllItems() throws SQLException, ClassNotFoundException ;

    boolean deleteItem(String code) throws SQLException, ClassNotFoundException;

    boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException ;

    boolean existItem(String code) throws SQLException, ClassNotFoundException ;

    String genarateNewId() throws SQLException, ClassNotFoundException ;

    boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException ;

    ItemDTO searchItem(String newItemCode) throws SQLException, ClassNotFoundException;

    boolean checkItem(String code) throws SQLException, ClassNotFoundException;

    ObservableList<String> getAllItemCode() throws SQLException, ClassNotFoundException;

    boolean updateProducts(List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException;

    ItemDTO findItem(String code) throws SQLException, ClassNotFoundException;
}
