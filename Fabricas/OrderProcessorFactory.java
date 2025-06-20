package Fabricas;

import VisitadorExtendido.OrderCalculator;
import VisitadorExtendido.OrderCalculatorInterface;
import VisitadorExtendido.OrderProcessorInterface;
import VisitadorExtendido.OrderRepository;
import VisitadorExtendido.OrderVisitorAdapter;
import VisitadorExtendido.PureOrderVisitor;

public class OrderProcessorFactory {
    public static OrderProcessorInterface createOrderProcessor() {
        OrderCalculatorInterface calculator = new OrderCalculator();
        PureOrderVisitor visitor = new PureOrderVisitor(calculator);
        OrderRepository repository = new OrderRepository();
        return new OrderVisitorAdapter(visitor, calculator, repository);
    }
}
