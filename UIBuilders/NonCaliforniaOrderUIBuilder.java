package UIBuilders;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import VisitadorExtendido.NonCaliforniaOrder;
import VisitadorExtendido.Order;
import VisitadorExtendido.OrderUIBuilder;
public class NonCaliforniaOrderUIBuilder extends OrderUIBuilder {
    private JPanel form;
    private JFormattedTextField amount;
    // Aqui se aplica un singleton para CaliforniaOrderUIbuilder
    static NonCaliforniaOrderUIBuilder NCUIBuilder=new NonCaliforniaOrderUIBuilder();

    private NonCaliforniaOrderUIBuilder(){
    
    }
    
   public static NonCaliforniaOrderUIBuilder getInstance(){
        return NCUIBuilder;
   }
    
    public void createOrderFields(){
                createContainer();
                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);
                NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
                numberFormatter.setValueClass(Double.class); // Acepta números decimales
                numberFormatter.setAllowsInvalid(false); // No permite caracteres no numéricos
                numberFormatter.setMinimum(0.0); // Valor mínimo permitido
                numberFormatter.setMaximum(Double.MAX_VALUE); // Valor máximo permitido
                GridBagLayout gridbag = new GridBagLayout();
                form.setLayout(gridbag);
                GridBagConstraints gbc = new GridBagConstraints();

                JLabel amountLabel=new JLabel("Order Amount (USD):");
                this.amount=new JFormattedTextField(numberFormatter); 
                    gbc.insets.top = 5;
                    gbc.insets.bottom = 5;
                    gbc.insets.left = 5;
                    gbc.insets.right = 5;
                    gbc.anchor = GridBagConstraints.EAST;
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gridbag.setConstraints(amountLabel, gbc);
                    gbc.anchor = GridBagConstraints.WEST;
                    gbc.gridx = 1;
                    gbc.gridy = 0;
                    gridbag.setConstraints(this.amount, gbc); 
                    this.amount.setColumns(10); 
               form.add(amountLabel);
               form.add(this.amount); 
               
            };
    
    public void initializeOrderFields(){
            amount.setValue(0);
            }

    @Override
    public JPanel getPanel() {
        return this.form;
        
    }

    @Override
    protected void createContainer() {
        this.form=new JPanel();
         
    }

    @Override
    public Order getFieldsValue() {
        Double value=(Double) amount.getValue();
        amount.setValue(0); // This will now correctly reset the class member
        return new NonCaliforniaOrder(value);
    }
    
    @Override
    public void setFieldsValue(Order order) {
        if (order instanceof NonCaliforniaOrder) {
            NonCaliforniaOrder ncOrder = (NonCaliforniaOrder) order;
            amount.setValue(ncOrder.getOrderAmount());
        }
    }
}
