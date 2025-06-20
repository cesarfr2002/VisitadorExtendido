package VisitadorExtendido;
public class NonCaliforniaOrder extends Order {
  private double orderAmount;

  public NonCaliforniaOrder() {
  }
  public NonCaliforniaOrder(double inp_orderAmount) {
    orderAmount = inp_orderAmount;
  }
  public double getOrderAmount() {
    return orderAmount;
  }
  public void accept(VisitorInterface v) { // Changed from OrderVisitor to VisitorInterface
    v.visit(this);
  }
}
