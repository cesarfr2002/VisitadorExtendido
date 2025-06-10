package VisitadorExtendido;

public class OrderCalculator implements OrderCalculatorInterface {
    private double orderTotal;
    
    public OrderCalculator() {
        this.orderTotal = 0.0;
    }
    
    public void addOrderAmount(double amount) {
        orderTotal += amount;
    }
    
    public void addOrderAmountWithTax(double amount, double tax) {
        orderTotal += amount + tax;
    }
    
    public void addOrderAmountWithSH(double amount, double sh) {
        orderTotal += amount + sh;
    }
    
    public double getOrderTotal() {
        return orderTotal;
    }
    
    public void resetTotal() {
        orderTotal = 0.0;
    }
}
