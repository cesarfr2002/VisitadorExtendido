package VisitadorExtendido;
public class CanadianOrder extends Order {
  private double orderAmount;

  public CanadianOrder() {
  }
  public CanadianOrder(double inp_orderAmount) {
    orderAmount = inp_orderAmount;
  }
  public double getOrderAmount() {
    return orderAmount;
  }
  public void accept(VisitorInterface v) { 
    v.visit(this);
  }
}
