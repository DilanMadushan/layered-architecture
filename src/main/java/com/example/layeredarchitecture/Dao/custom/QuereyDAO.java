package com.example.layeredarchitecture.Dao.custom;

import com.example.layeredarchitecture.Dao.SuperDAO;
import com.example.layeredarchitecture.DTO.CusOrderOTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QuereyDAO extends SuperDAO {
    ArrayList<CusOrderOTO> getAll(String id) throws SQLException, ClassNotFoundException;
}
