package VisitadorExtendido;

public class OrderVisitorAdapter implements OrderProcessorInterface {
    private PureOrderVisitor visitor;
    private OrderCalculatorInterface calculator;
    private OrderRepository orderRepository;

    public OrderVisitorAdapter(PureOrderVisitor visitor, OrderCalculatorInterface calculator, OrderRepository orderRepository) {
        this.visitor = visitor;
        this.calculator = calculator;
        this.orderRepository = orderRepository;
    }

    public double getOrderTotal() {
        return calculator.getOrderTotal();
    }

    public void resetTotal() {
        calculator.resetTotal();
    }

    public Order getOrder(int index) {
        return orderRepository.getOrder(index);
    }

    public int getOrderCount() {
        return orderRepository.getOrderCount();
    }

    public void updateOrder(int index, Order newOrder) {
        orderRepository.updateOrder(index, newOrder);
        recalculateTotal();
    }

    private void recalculateTotal() {
        calculator.resetTotal();
        for (Order order : orderRepository.getAllOrders()) {
            order.accept(visitor);
        }
    }

    public void processOrder(Order order) {
        orderRepository.addOrder(order);
        order.accept(visitor);
    }
}
