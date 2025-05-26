package VisitadorExtendido;
public interface Order {
  public void accept(OrderVisitor v);
}
