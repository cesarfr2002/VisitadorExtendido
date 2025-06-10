package VisitadorExtendido;

public class PureOrderVisitor implements VisitorInterface {
    private OrderCalculatorInterface calculator;
    
    public PureOrderVisitor(OrderCalculatorInterface calculator) {
        this.calculator = calculator;
    }
    
    public void visit(NonCaliforniaOrder inp_order) {
        calculator.addOrderAmount(inp_order.getOrderAmount());
    }
    
    public void visit(CaliforniaOrder inp_order) {
        calculator.addOrderAmountWithTax(
            inp_order.getOrderAmount(), 
            inp_order.getAdditionalTax()
        );
    }
    
    public void visit(OverseasOrder inp_order) {
        calculator.addOrderAmountWithSH(
            inp_order.getOrderAmount(), 
            inp_order.getAdditionalSH()
        );
    }
    
    public void visit(CanadianOrder inp_order) {
        calculator.addOrderAmount(inp_order.getOrderAmount());
    }
}
