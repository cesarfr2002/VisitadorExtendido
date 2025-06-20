package VisitadorExtendido;

public class OrderInfoVisitor implements VisitorInterface {
    private String orderTypeName;
    private double orderAmount;

    @Override
    public void visit(CaliforniaOrder order) {
        orderTypeName = "California Order";
        orderAmount = order.getOrderAmount() + order.getAdditionalTax();
    }

    @Override
    public void visit(NonCaliforniaOrder order) {
        orderTypeName = "Non-California Order";
        orderAmount = order.getOrderAmount();
    }

    @Override
    public void visit(OverseasOrder order) {
        orderTypeName = "Overseas Order";
        orderAmount = order.getOrderAmount() + order.getAdditionalSH();
    }

    @Override
    public void visit(CanadianOrder order) {
        orderTypeName = "Canadian Order";
        orderAmount = order.getOrderAmount();
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public double getOrderAmount() {
        return orderAmount;
    }
}
