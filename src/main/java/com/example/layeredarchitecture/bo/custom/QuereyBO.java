package com.example.layeredarchitecture.bo.custom;

import com.example.layeredarchitecture.DTO.CusOrderOTO;
import com.example.layeredarchitecture.bo.SuperBo;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QuereyBO extends SuperBo {
    ArrayList<CusOrderOTO> getAll(String id) throws SQLException, ClassNotFoundException;
}
