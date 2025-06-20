package VisitadorExtendido;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Fabricas.OrderProcessorFactory;
import Fabricas.OrderUIBuilderFactory;
import UIBuilders.OrderUIDirector;

public class OrderManager extends JFrame {
  public static final String newline = "\n";
  public static final String GET_TOTAL = "Get Total";
  public static final String CREATE_ORDER = "Create Order";
  public static final String EDIT_ORDER = "Edit Order";
  public static final String EXIT = "Exit";
  public static final String CA_ORDER = "California Order";
  public static final String NON_CA_ORDER = 
    "Non-California Order";
    public static final String comboBox = 
    "Cmb";

  public static final String OVERSEAS_ORDER = "Overseas Order";
  public static final String CANADIAN_ORDER = "Canadian Order";


  private JComboBox<String> cmbOrderType;
  private JLabel lblOrderType;
  //Panel donde se anadira el formulario dependiendo de la orden
  private JPanel orderForm;

  private OrderProcessorInterface orderProcessor; // Changed from OrderVisitor

  public OrderManager() {
    super("Visitor Pattern - Example");

    //Create the processor instance
    orderProcessor = OrderProcessorFactory.createOrderProcessor();

    cmbOrderType = new JComboBox<String>();
    cmbOrderType.setActionCommand(comboBox);
    cmbOrderType.addItem(OrderManager.CA_ORDER);
    cmbOrderType.addItem(OrderManager.NON_CA_ORDER);
    cmbOrderType.addItem(OrderManager.OVERSEAS_ORDER);
    cmbOrderType.addItem(OrderManager.CANADIAN_ORDER);
    lblOrderType = new JLabel("Order Type:");
    
    
    

    //Create the buttons
    JButton getTotalButton = new JButton(OrderManager.GET_TOTAL);
    getTotalButton.setMnemonic(KeyEvent.VK_G);
    JButton createOrderButton = new JButton(OrderManager.CREATE_ORDER);
    createOrderButton.setMnemonic(KeyEvent.VK_C);
    JButton editOrderButton = new JButton(OrderManager.EDIT_ORDER);
    editOrderButton.setMnemonic(KeyEvent.VK_E);
    JButton exitButton = new JButton(OrderManager.EXIT);
    exitButton.setMnemonic(KeyEvent.VK_X);

    ButtonHandler objButtonHandler = new ButtonHandler(this);

    getTotalButton.addActionListener(objButtonHandler);
    createOrderButton.addActionListener(objButtonHandler);
    editOrderButton.addActionListener(objButtonHandler);
    cmbOrderType.addActionListener(objButtonHandler);
    exitButton.addActionListener(new ButtonHandler());

    //For layout purposes, put the buttons in a separate panel
    JPanel buttonPanel = new JPanel();
    orderForm=new JPanel();
    JPanel panel = new JPanel();
    GridBagLayout gridbag2 = new GridBagLayout();
    panel.setLayout(gridbag2);
    GridBagConstraints gbc2 = new GridBagConstraints();
    panel.add(getTotalButton);
    panel.add(createOrderButton);
    panel.add(editOrderButton);
    panel.add(exitButton);
    
    gbc2.gridx = 0;
    gridbag2.setConstraints(createOrderButton, gbc2);
    gbc2.gridx = 1;
    gridbag2.setConstraints(getTotalButton, gbc2);
    gbc2.gridx = 2;
    gridbag2.setConstraints(editOrderButton, gbc2);
    gbc2.gridx = 3;
    gridbag2.setConstraints(exitButton, gbc2);

    //****************************************************
    GridBagLayout gridbag = new GridBagLayout();
    buttonPanel.setLayout(gridbag);
    GridBagConstraints gbc = new GridBagConstraints();

    buttonPanel.add(lblOrderType);
    buttonPanel.add(cmbOrderType);
    buttonPanel.add(orderForm);


    gbc.insets.top = 5;
    gbc.insets.bottom = 5;
    gbc.insets.left = 5;
    gbc.insets.right = 5;

    gbc.anchor = GridBagConstraints.EAST;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gridbag.setConstraints(lblOrderType, gbc);
    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridx = 1;
    gbc.gridy = 0;
    gridbag.setConstraints(cmbOrderType, gbc);
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx= 0;
    gbc.gridy = 1;
    gridbag.setConstraints(orderForm, gbc);
    
    
    

    gbc.insets.left = 2;
    gbc.insets.right = 2;
    gbc.insets.top = 40;

    //****************************************************

    //Add the buttons and the log to the frame
    Container contentPane = getContentPane();

    contentPane.add(buttonPanel, BorderLayout.NORTH);
    contentPane.add(panel, BorderLayout.CENTER);
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      SwingUtilities.updateComponentTreeUI(
        OrderManager.this);
    } catch (Exception ex) {
      System.out.println(ex);
    }

  }

  public static void main(String[] args) {
    JFrame frame = new OrderManager();

    frame.addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            System.exit(0);
          }
        }
                           );

    //frame.pack();
    frame.setSize(500, 400);
    frame.setVisible(true);
  }

  public void drawOrderPane(JPanel valorean){
    orderForm.removeAll(); // Clear any existing components in the panel
    orderForm.add(valorean); // Add the new panel
    orderForm.revalidate(); // Revalidate the layout
    orderForm.repaint();
  }
  public OrderProcessorInterface getOrderProcessor() { // Changed from getOrderVisitor
    return orderProcessor;
  }
  public String getOrderType() {
    return (String) cmbOrderType.getSelectedItem();
  }
  
  // Método para acceder al combo desde ButtonHandler
  public JComboBox<String> getOrderTypeCombo() {
    return cmbOrderType;
  }
 

} // End of class OrderManager

class ButtonHandler implements ActionListener {
  OrderManager objOrderManager;
  private int editingOrderIndex = -1; // Para trackear qué orden estamos editando
  
  public void actionPerformed(ActionEvent e) {
    String totalResult = null;

    if (e.getActionCommand().equals(OrderManager.EXIT)) {
      System.exit(1);
    }
    
    if (e.getActionCommand().equals(OrderManager.CREATE_ORDER)) {
      if (editingOrderIndex >= 0) {
        // Estamos editando una orden existente
        updateExistingOrder();
      } else {
        // Crear nueva orden
        createNewOrder();
      }
    }
    
    if (e.getActionCommand().equals(OrderManager.EDIT_ORDER)) {
      OrderProcessorInterface processor = objOrderManager.getOrderProcessor(); // Changed from OrderVisitor
      
      if (processor.getOrderCount() == 0) {
        JOptionPane.showMessageDialog(objOrderManager, 
          "No hay órdenes para editar.", 
          "Sin órdenes", 
          JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      
      OrderRecoveryDialog dialog = new OrderRecoveryDialog(objOrderManager, processor);
      dialog.setVisible(true);
      
      if (dialog.isOrderSelected()) {
        OrderRecoveryDialog.OrderItem item = dialog.getSelectedOrderItem();
        loadOrderForEditing(item);
      }
    }

    //Aqui se crea el panel al elegir  el tipo de orden a crear
    if(e.getActionCommand().equals(OrderManager.comboBox)){
      String selectedOrderType = (String) objOrderManager.getOrderType();
      OrderUIBuilder builder=OrderUIBuilderFactory.getUI(selectedOrderType);
      OrderUIDirector director=new OrderUIDirector();
      director.setOrderForm(builder);
      director.build();
      objOrderManager.drawOrderPane(builder.getPanel());
      // try-catch block was empty, can be removed or completed if error handling is needed
    }

    if (e.getActionCommand().equals(OrderManager.GET_TOTAL)) {
      //Get the Processor
      OrderProcessorInterface processor = objOrderManager.getOrderProcessor(); // Changed from OrderVisitor
      totalResult = Double.toString(processor.getOrderTotal()); // Changed from visitor.getOrderTotal()
      totalResult = " Orders Total = " + totalResult;
      // Display totalResult, e.g., in a JOptionPane or a status bar
      JOptionPane.showMessageDialog(objOrderManager, totalResult, "Total de Órdenes", JOptionPane.INFORMATION_MESSAGE);
    }
    
  }

  private void createNewOrder() {
    String selectedOrderType = objOrderManager.getOrderType();
    OrderUIBuilder builder = OrderUIBuilderFactory.getUI(selectedOrderType);
    
    if (builder != null) {
      Order newOrder = builder.getFieldsValue();
      if (newOrder != null) {
        // Agregar al processor para procesamiento y almacenamiento
        objOrderManager.getOrderProcessor().processOrder(newOrder); // Changed from newOrder.accept(objOrderManager.getOrderVisitor());
        
        JOptionPane.showMessageDialog(objOrderManager,
          "Orden creada exitosamente.",
          "Orden Creada",
          JOptionPane.INFORMATION_MESSAGE);
      }
    }
    editingOrderIndex = -1; // Reset editing mode
  }

  private void updateExistingOrder() {
    String selectedOrderType = objOrderManager.getOrderType();
    OrderUIBuilder builder = OrderUIBuilderFactory.getUI(selectedOrderType);
    
    if (builder != null && editingOrderIndex >= 0) {
      Order updatedOrder = builder.getFieldsValue();
      if (updatedOrder != null) {
        OrderProcessorInterface processor = objOrderManager.getOrderProcessor(); // Changed from OrderVisitor
        processor.updateOrder(editingOrderIndex, updatedOrder);
        
        JOptionPane.showMessageDialog(objOrderManager,
          "Orden actualizada exitosamente.",
          "Orden Actualizada",
          JOptionPane.INFORMATION_MESSAGE);
      }
    }
    editingOrderIndex = -1;
  }

  private void loadOrderForEditing(OrderRecoveryDialog.OrderItem item) {
    editingOrderIndex = item.getIndex();
    Order order = item.getOrder();
    String orderType = item.getType();
    
    // Cambiar el combo al tipo de orden correcto
    JComboBox<String> combo = objOrderManager.getOrderTypeCombo();
    if (orderType.equals("California Order")) {
      combo.setSelectedItem(OrderManager.CA_ORDER);
    } else if (orderType.equals("Non-California Order")) {
      combo.setSelectedItem(OrderManager.NON_CA_ORDER);
    } else if (orderType.equals("Overseas Order")) {
      combo.setSelectedItem(OrderManager.OVERSEAS_ORDER);
    } else if (orderType.equals("Canadian Order")) {
      combo.setSelectedItem(OrderManager.CANADIAN_ORDER); // Corrected this line
    }
    // Cargar el formulario correspondiente
    String selectedOrderType = objOrderManager.getOrderType();
    OrderUIBuilder builder = OrderUIBuilderFactory.getUI(selectedOrderType);
    OrderUIDirector director = new OrderUIDirector();
    director.setOrderForm(builder);
    director.build();
    objOrderManager.drawOrderPane(builder.getPanel()); // Removed duplicated lines after this
    
    // Prellenar los campos con los datos de la orden
    builder.setFieldsValue(order);
    
    JOptionPane.showMessageDialog(objOrderManager,
      "Orden cargada para edición. Modifique los valores y presione 'Create Order' para guardar.",
      "Editando Orden #" + (item.getIndex() + 1),
      JOptionPane.INFORMATION_MESSAGE);
  }

  // Removed unused createOrder method

  public ButtonHandler() {
  }
  public ButtonHandler(OrderManager inObjOrderManager) {
    objOrderManager = inObjOrderManager;
  }

} // End of class ButtonHandler

