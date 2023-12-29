package com.example.layeredarchitecture.bo.custom.impl;

import com.example.layeredarchitecture.DTO.CusOrderOTO;
import com.example.layeredarchitecture.Dao.DAOFactory;
import com.example.layeredarchitecture.Dao.custom.QuereyDAO;
import com.example.layeredarchitecture.bo.custom.QuereyBO;

import java.sql.SQLException;
import java.util.ArrayList;

public class QuereyBOImpl implements QuereyBO {
    QuereyDAO quereyDAO = (QuereyDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DTOTypes.QUERY);
    @Override
    public ArrayList<CusOrderOTO> getAll(String id) throws SQLException, ClassNotFoundException {
        return quereyDAO.getAll(id);
    }
}
