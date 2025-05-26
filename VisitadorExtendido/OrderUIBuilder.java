package VisitadorExtendido;
import javax.swing.JPanel;

public abstract class OrderUIBuilder {


//Si el panel no ha sido creado, hace saltar una excepcion
public abstract JPanel getPanel();
protected abstract void createContainer();

public abstract void createOrderFields();
public abstract void initializeOrderFields();
//Este metodo hace que el builder retorne los valores de sus campos como un objeto Order
public abstract Order getFieldsValue();





    
}
