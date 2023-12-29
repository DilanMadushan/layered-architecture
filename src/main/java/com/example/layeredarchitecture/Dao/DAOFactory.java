package com.example.layeredarchitecture.Dao;

import com.example.layeredarchitecture.Dao.custom.impl.*;

public class DAOFactory {
    public static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getDaoFactory(){
        return (daoFactory ==null) ? daoFactory=new DAOFactory() : daoFactory;
    }
    public enum DTOTypes{
        CUSTOMER,ITEM,ORDER,ORDERDETAILS,QUERY
    }

    public SuperDAO getDAO(DTOTypes dtoTypes){
        switch (dtoTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDERDETAILS:
                return new OrderDetailsDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
        }
        return null;
    }

}
