package VisitadorExtendido;

import java.awt.*;
import javax.swing.*;

public class OrderRecoveryDialog extends JDialog {
    private JComboBox<OrderItem> orderComboBox;
    private JButton editButton;
    private JButton cancelButton;
    private OrderProcessorInterface processor;
    private OrderItem selectedOrder;
    private boolean orderSelected;

    public OrderRecoveryDialog(Frame parent, OrderProcessorInterface processor) {
        super(parent, "Recuperar Orden", true);
        this.processor = processor;
        this.orderSelected = false;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void initializeComponents() {
        orderComboBox = new JComboBox<>();
        populateOrderComboBox();
        
        editButton = new JButton("Editar Orden");
        cancelButton = new JButton("Cancelar");
        
        editButton.setEnabled(orderComboBox.getItemCount() > 0);
    }

    private void populateOrderComboBox() {
        orderComboBox.removeAllItems();
        
        for (int i = 0; i < processor.getOrderCount(); i++) {
            Order order = processor.getOrder(i);
            String orderType = getOrderTypeName(order);
            double orderAmount = getOrderAmount(order);
            
            OrderItem item = new OrderItem(i, order, orderType, orderAmount);
            orderComboBox.addItem(item);
        }
    }

    private String getOrderTypeName(Order order) {
        if (order instanceof CaliforniaOrder) return "California Order";
        if (order instanceof NonCaliforniaOrder) return "Non-California Order";
        if (order instanceof OverseasOrder) return "Overseas Order";
        if (order instanceof CanadianOrder) return "Canadian Order";
        return "Unknown Order";
    }

    private double getOrderAmount(Order order) {
        if (order instanceof CaliforniaOrder) {
            CaliforniaOrder co = (CaliforniaOrder) order;
            return co.getOrderAmount() + co.getAdditionalTax();
        }
        if (order instanceof NonCaliforniaOrder) {
            return ((NonCaliforniaOrder) order).getOrderAmount();
        }
        if (order instanceof OverseasOrder) {
            OverseasOrder oo = (OverseasOrder) order;
            return oo.getOrderAmount() + oo.getAdditionalSH();
        }
        if (order instanceof CanadianOrder) {
            return ((CanadianOrder) order).getOrderAmount();
        }
        return 0.0;
    }

    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(new JLabel("Seleccione la orden a editar:"), gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(orderComboBox, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(15, 10, 10, 5);
        add(editButton, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(15, 5, 10, 10);
        add(cancelButton, gbc);

        pack();
        setLocationRelativeTo(getParent());
    }

    private void setupEventHandlers() {
        editButton.addActionListener(e -> {
            selectedOrder = (OrderItem) orderComboBox.getSelectedItem();
            orderSelected = true;
            dispose();
        });

        cancelButton.addActionListener(e -> {
            orderSelected = false;
            dispose();
        });
    }

    public boolean isOrderSelected() {
        return orderSelected;
    }

    public OrderItem getSelectedOrderItem() {
        return selectedOrder;
    }

    // Clase interna para representar items del combo
    public static class OrderItem {
        private int index;
        private Order order;
        private String type;
        private double total;

        public OrderItem(int index, Order order, String type, double total) {
            this.index = index;
            this.order = order;
            this.type = type;
            this.total = total;
        }

        public int getIndex() { return index; }
        public Order getOrder() { return order; }
        public String getType() { return type; }
        public double getTotal() { return total; }

        @Override
        public String toString() {
            return String.format("Orden #%d - %s - Total: $%.2f", 
                               index + 1, type, total);
        }
    }
}
