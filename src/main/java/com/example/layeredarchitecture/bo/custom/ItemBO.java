package com.example.layeredarchitecture.bo.custom;

import com.example.layeredarchitecture.bo.SuperBo;
import com.example.layeredarchitecture.DTO.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBo {
    public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException;

    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException;

    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean existItem(String code) throws SQLException, ClassNotFoundException ;

    public String genarateItemId() throws SQLException, ClassNotFoundException ;

    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException ;
    public ItemDTO searchItem(String newValue) throws SQLException, ClassNotFoundException ;
}
