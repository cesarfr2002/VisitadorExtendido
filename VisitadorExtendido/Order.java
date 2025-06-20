package VisitadorExtendido;
public abstract class Order {
  protected double orderAmount;

  public double getOrderAmount() {
    return orderAmount;
  }

  public void setOrderAmount(double orderAmount) {
    this.orderAmount = orderAmount;
  }

  public abstract void accept(VisitorInterface visitor);
}
