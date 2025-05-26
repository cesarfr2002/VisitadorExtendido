package Fabricas;

import UIBuilders.CaliforniaOrderUIBuilder;
import UIBuilders.NonCaliforniaOrderUIBuilder;
import UIBuilders.OverseasOrderUIBuilder;
import VisitadorExtendido.OrderManager;
import VisitadorExtendido.OrderUIBuilder;

public  class OrderUIBuilderFactory {
    private OrderUIBuilderFactory(){
        
    }
public static OrderUIBuilder getUI(String type){
    if(type.equals(OrderManager.CANADIAN_ORDER)|| type.equals(OrderManager.NON_CA_ORDER)){
        return NonCaliforniaOrderUIBuilder.getInstance();
    }
    if(type.equals(OrderManager.OVERSEAS_ORDER)){
        return OverseasOrderUIBuilder.getInstance();
    }
    if(type.equals(OrderManager.CA_ORDER)){
        return CaliforniaOrderUIBuilder.getInstance();
    }
    return null;
}   
}
