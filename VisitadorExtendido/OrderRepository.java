package VisitadorExtendido;

import java.util.Vector;

public class OrderRepository {
    private Vector<Order> orderList;

    public OrderRepository() {
        orderList = new Vector<>();
    }

    public void addOrder(Order order) {
        orderList.add(order);
    }

    public Order getOrder(int index) {
        if (index >= 0 && index < orderList.size()) {
            return orderList.get(index);
        }
        return null;
    }

    public int getOrderCount() {
        return orderList.size();
    }

    public void updateOrder(int index, Order newOrder) {
        if (index >= 0 && index < orderList.size()) {
            orderList.set(index, newOrder);
        }
    }

    public Vector<Order> getAllOrders() {
        return orderList;
    }
}
