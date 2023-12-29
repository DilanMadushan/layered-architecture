package com.example.layeredarchitecture.Dao.custom.impl;

import com.example.layeredarchitecture.Dao.SQLUtil;
import com.example.layeredarchitecture.Dao.custom.QuereyDAO;
import com.example.layeredarchitecture.DTO.CusOrderOTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QuereyDAO {

    SQLUtil sql = new SQLUtil();


    @Override
    public ArrayList<CusOrderOTO> getAll(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = sql.execute("SELECT c.name,c.address,o.oid,o.date\n" +
                "FROM Customer c\n" +
                "JOIN Orders O ON c.id = O.customerID\n" +
                "WHERE id = ?",id);
        ArrayList<CusOrderOTO> dto = new ArrayList<>();

        while (resultSet.next()){
            dto.add(new CusOrderOTO(resultSet.getString("name"),resultSet.getString("address")
            ,resultSet.getString("oid"), resultSet.getDate("date").toLocalDate()));
        }

        return dto;
    }
}
