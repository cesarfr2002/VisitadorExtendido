package UIBuilders;

import VisitadorExtendido.OrderUIBuilder;

public class OrderUIDirector {
    OrderUIBuilder orderForm;

    public OrderUIDirector(){
        
    }
    public void  build(){
        this.orderForm.createOrderFields();
        this.orderForm.initializeOrderFields();
        
       
    }
    public void setOrderForm(OrderUIBuilder orderForm){
        this.orderForm=orderForm;
        
    }
    
}
