package UIBuilders;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import VisitadorExtendido.CaliforniaOrder;
import VisitadorExtendido.Order;
import VisitadorExtendido.OrderUIBuilder; 

public class CaliforniaOrderUIBuilder extends OrderUIBuilder  {
    // Aqui se aplica un singleton para CaliforniaOrderUIbuilder
    static CaliforniaOrderUIBuilder CUIBuilder=new CaliforniaOrderUIBuilder();

    private JPanel form;
    private JFormattedTextField amount;
    private JFormattedTextField tax;

        private  CaliforniaOrderUIBuilder(){
    
        }
    
       public static CaliforniaOrderUIBuilder getInstance(){
            return CUIBuilder;
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
                    JLabel taxLabel=new JLabel("California Tax (USD):");
                    this.tax=new JFormattedTextField(numberFormatter);
                        gbc.insets.top = 5;
                        gbc.insets.bottom = 5;
                        gbc.insets.left = 5;
                        gbc.insets.right = 0;
                        gbc.anchor = GridBagConstraints.EAST;
                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gridbag.setConstraints(amountLabel, gbc);
                        gbc.anchor = GridBagConstraints.WEST;
                        gbc.gridx = 1;
                        gbc.gridy = 0;
                        gridbag.setConstraints(this.amount, gbc);
                        gbc.anchor = GridBagConstraints.EAST;
                        gbc.gridx = 0;
                        gbc.gridy = 1;
                        gridbag.setConstraints(taxLabel, gbc);
                        gbc.gridx = 1;
                        gbc.gridy = 1;
                        gridbag.setConstraints(this.tax, gbc);
                        this.amount.setColumns(10);
                        this.tax.setColumns(10);
                   form.add(amountLabel);
                   form.add(this.amount);
                   form.add(taxLabel);
                   form.add(this.tax);
                   
                };
        
        public void initializeOrderFields(){
                amount.setValue(0);
                tax.setValue(0);
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
              amount.setValue(0);
              Double taxValue=(Double) tax.getValue();
              tax.setValue(0);
              return new CaliforniaOrder(value,taxValue);
        };
        
       
        public void setFieldsValue(Order order) {
            if (order instanceof CaliforniaOrder) {
                CaliforniaOrder caOrder = (CaliforniaOrder) order;
                amount.setValue(caOrder.getOrderAmount());
                tax.setValue(caOrder.getAdditionalTax());
            }
        }
	
}
