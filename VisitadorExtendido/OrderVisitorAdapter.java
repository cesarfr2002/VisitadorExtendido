package VisitadorExtendido;
import java.util.*;

public class OrderVisitorAdapter implements OrderProcessorInterface {
    private PureOrderVisitor visitor;
    private OrderCalculatorInterface calculator;
    private Vector<Order> orderObjList;
    
    public OrderVisitorAdapter() {
        calculator = new OrderCalculator();
        visitor = new PureOrderVisitor(calculator);
        orderObjList = new Vector<Order>();
    }
    
    public double getOrderTotal() {
        return calculator.getOrderTotal();
    }
    
    public void resetTotal() {
        calculator.resetTotal();
    }
    
    // Método para recuperar orden por índice
    public Order getOrder(int index) {
        if (index >= 0 && index < orderObjList.size()) {
            return orderObjList.get(index);
        }
        return null;
    }
    
    // Método para obtener el número total de órdenes
    public int getOrderCount() {
        return orderObjList.size();
    }
    
    // Método para actualizar una orden específica
    public void updateOrder(int index, Order newOrder) {
        if (index >= 0 && index < orderObjList.size()) {
            orderObjList.set(index, newOrder);
            recalculateTotal();
        }
    }
    
    // Recalcular total después de actualizar órdenes
    private void recalculateTotal() {
        calculator.resetTotal();
        for (Order order : orderObjList) {
            order.accept(visitor);
        }
    }
    
    // Implementa la interfaz del cliente (OrderProcessorInterface)
    public void processOrder(Order order) {
        orderObjList.add(order);
        order.accept(visitor);
    }
}
