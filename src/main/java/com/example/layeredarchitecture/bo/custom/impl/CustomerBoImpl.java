package com.example.layeredarchitecture.bo.custom.impl;

import com.example.layeredarchitecture.Dao.DAOFactory;
import com.example.layeredarchitecture.Dao.custom.CustomerDAO;
import com.example.layeredarchitecture.Entity.Customer;
import com.example.layeredarchitecture.bo.custom.CustomerBO;
import com.example.layeredarchitecture.DTO.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBoImpl implements CustomerBO {
    private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DTOTypes.CUSTOMER);


    @Override
    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> entity = customerDAO.getAll();
        ArrayList<CustomerDTO> dto = new ArrayList<>();

        for (Customer set : entity) {
            dto.add(new CustomerDTO(set.getId(),set.getName(),set.getAddress()));
        }
        return dto;
    }

    @Override
    public boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(dto.getId(),dto.getName(),dto.getAddress()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getId(),dto.getName(),dto.getAddress()));
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
       return customerDAO.delete(id);
    }

    @Override
    public String genarateCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.genarateId();
    }


}
