package VisitadorExtendido;

public interface OrderCalculatorInterface {
    public void addOrderAmount(double amount);
    public void addOrderAmountWithTax(double amount, double tax);
    public void addOrderAmountWithSH(double amount, double sh);
    public double getOrderTotal();
    public void resetTotal();
}
