package VisitadorExtendido;

public interface OrderProcessorInterface {
    public void processOrder(Order order);
    public double getOrderTotal();
    public void resetTotal();
    public Order getOrder(int index);
    public int getOrderCount();
    public void updateOrder(int index, Order newOrder);
}
