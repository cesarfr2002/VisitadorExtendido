package UIBuilders;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import VisitadorExtendido.CanadianOrder;
import VisitadorExtendido.Order;
import VisitadorExtendido.OrderUIBuilder;

public class CanadianOrderUIBuilder extends OrderUIBuilder {
    // Singleton pattern implementation
    static CanadianOrderUIBuilder CNUIBuilder = new CanadianOrderUIBuilder();
    
    private JPanel form;
    private JFormattedTextField amount;

    private CanadianOrderUIBuilder() {
        // Private constructor for singleton
    }

    public static CanadianOrderUIBuilder getInstance() {
        return CNUIBuilder;
    }

    public void createOrderFields() {
        createContainer();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.CANADA);
        NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
        numberFormatter.setValueClass(Double.class); // Acepta números decimales
        numberFormatter.setAllowsInvalid(false); // No permite caracteres no numéricos
        numberFormatter.setMinimum(0.0); // Valor mínimo permitido
        numberFormatter.setMaximum(Double.MAX_VALUE); // Valor máximo permitido
        
        GridBagLayout gridbag = new GridBagLayout();
        form.setLayout(gridbag);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel amountLabel = new JLabel("Order Amount (CAD):");
        this.amount = new JFormattedTextField(numberFormatter);
        
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
    }

    public void initializeOrderFields() {
        amount.setValue(0);
    }

    @Override
    public JPanel getPanel() {
        return this.form;
    }

    @Override
    protected void createContainer() {
        this.form = new JPanel();
    }

    @Override
    public Order getFieldsValue() {
        Double value = (Double) amount.getValue();
        amount.setValue(0); // Reset field after getting value
        return new CanadianOrder(value);
    }

    @Override
    public void setFieldsValue(Order order) {
        if (order instanceof CanadianOrder) {
            CanadianOrder cnOrder = (CanadianOrder) order;
            amount.setValue(cnOrder.getOrderAmount());
        }
    }
}
