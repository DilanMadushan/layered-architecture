package com.example.layeredarchitecture.Dao.custom.impl;

import com.example.layeredarchitecture.Dao.SQLUtil;
import com.example.layeredarchitecture.Dao.custom.QuereyDAO;
import com.example.layeredarchitecture.model.CusOrderOTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QuereyDAO {

    SQLUtil sql = new SQLUtil();

    @Override
    public ArrayList<CusOrderOTO> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(CusOrderOTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(CusOrderOTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String genarateId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public CusOrderOTO search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

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
