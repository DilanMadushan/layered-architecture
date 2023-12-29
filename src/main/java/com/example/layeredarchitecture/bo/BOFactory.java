package com.example.layeredarchitecture.bo;

import com.example.layeredarchitecture.bo.custom.impl.CustomerBoImpl;
import com.example.layeredarchitecture.bo.custom.impl.ItemBoImpl;
import com.example.layeredarchitecture.bo.custom.impl.PlaceOrderBOImpl;
import com.example.layeredarchitecture.bo.custom.impl.QuereyBOImpl;

public class BOFactory {
    public static BOFactory boFactory;

    private BOFactory() {

    }

    public static BOFactory getBoFactory(){
        return (boFactory ==null) ? boFactory=new BOFactory() : boFactory;
    }
    public enum BOTypes{
        CUSTOMER,ITEM,PLACEORDER,QUEREY
    }

    public SuperBo getBo(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:
                return new CustomerBoImpl();
            case ITEM:
                return new ItemBoImpl();
            case PLACEORDER :
                return new PlaceOrderBOImpl();
            case QUEREY:
                return new QuereyBOImpl();
        }
        return null;
    }
}
