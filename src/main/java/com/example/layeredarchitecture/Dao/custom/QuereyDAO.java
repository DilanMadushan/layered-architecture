package com.example.layeredarchitecture.Dao.custom;

import com.example.layeredarchitecture.Dao.CrudDAO;
import com.example.layeredarchitecture.model.CusOrderOTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QuereyDAO extends CrudDAO<CusOrderOTO> {
    ArrayList<CusOrderOTO> getAll(String id) throws SQLException, ClassNotFoundException;
}
