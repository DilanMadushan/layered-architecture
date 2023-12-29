package com.example.layeredarchitecture.bo.custom.impl;

import com.example.layeredarchitecture.Dao.DAOFactory;
import com.example.layeredarchitecture.Dao.custom.ItemDAO;
import com.example.layeredarchitecture.Entity.Item;
import com.example.layeredarchitecture.bo.custom.ItemBO;
import com.example.layeredarchitecture.DTO.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBoImpl implements ItemBO {
    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DTOTypes.ITEM);


    @Override
    public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException {
        ArrayList<Item> entity =  itemDAO.getAll();
        ArrayList<ItemDTO> dto = new ArrayList<>();

        for (Item set:entity) {
            dto.add(new ItemDTO(set.getCode(),set.getDescription(),set.getUnitPrice(),set.getQtyOnHand()));
        }
        return dto;
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(code);
    }

    @Override
    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new Item(dto.getCode(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand()));
    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    @Override
    public String genarateItemId() throws SQLException, ClassNotFoundException {
        return itemDAO.genarateId();
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(dto.getCode(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand()));
    }

    @Override
    public ItemDTO searchItem(String newValue) throws SQLException, ClassNotFoundException {
        Item dto = itemDAO.search(newValue);

        return new ItemDTO(dto.getCode(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand());
    }
}
