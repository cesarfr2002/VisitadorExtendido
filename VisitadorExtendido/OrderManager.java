package VisitadorExtendido;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Fabricas.OrderUIBuilderFactory;
import UIBuilders.OrderUIDirector;

public class OrderManager extends JFrame {
  public static final String newline = "\n";
  public static final String GET_TOTAL = "Get Total";
  public static final String CREATE_ORDER = "Create Order";
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

  private OrderVisitor objVisitor;

  public OrderManager() {
    super("Visitor Pattern - Example");

    //Create the visitor instance
    objVisitor = new OrderVisitor();

    cmbOrderType = new JComboBox<String>();
    cmbOrderType.setActionCommand(comboBox);
    cmbOrderType.addItem(OrderManager.CA_ORDER);
    cmbOrderType.addItem(OrderManager.NON_CA_ORDER);
    cmbOrderType.addItem(OrderManager.OVERSEAS_ORDER);
    cmbOrderType.addItem(OrderManager.CANADIAN_ORDER);
    lblOrderType = new JLabel("Order Type:");
    
    
    

    //Create the open button
    JButton getTotalButton =
      new JButton(OrderManager.GET_TOTAL);
    getTotalButton.setMnemonic(KeyEvent.VK_G);
    JButton createOrderButton =
      new JButton(OrderManager.CREATE_ORDER);
    getTotalButton.setMnemonic(KeyEvent.VK_C);
    JButton exitButton = new JButton(OrderManager.EXIT);
    exitButton.setMnemonic(KeyEvent.VK_X);
    ButtonHandler objButtonHandler = new ButtonHandler(this);


    getTotalButton.addActionListener(objButtonHandler);
    createOrderButton.addActionListener(objButtonHandler);
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
    panel.add(exitButton);
    gbc2.anchor = GridBagConstraints.EAST;
    gbc2.gridx = 0;
    gbc2.gridy = 0;
    gridbag2.setConstraints(createOrderButton, gbc2);
    gbc2.gridx = 1;
    gbc2.gridy = 0;
    gridbag2.setConstraints(getTotalButton, gbc2);
    gbc2.gridx = 2;
    gbc2.gridy = 0;
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
  public OrderVisitor getOrderVisitor() {
    return objVisitor;
  }
  public String getOrderType() {
    return (String) cmbOrderType.getSelectedItem();
  }
 
  

} // End of class OrderManager

class ButtonHandler implements ActionListener {
  OrderManager objOrderManager;
  public void actionPerformed(ActionEvent e) {
    String totalResult = null;

    if (e.getActionCommand().equals(OrderManager.EXIT)) {
      System.exit(1);
    }
    if (e.getActionCommand().equals(OrderManager.CREATE_ORDER)
        ) {
      //get input values
     

      
    }
    //Aqui se crea el panel al elegir  el tipo de orden a crear
    if(e.getActionCommand().equals(OrderManager.comboBox)){
      String selectedOrderType = (String) objOrderManager.getOrderType();
      OrderUIBuilder builder=OrderUIBuilderFactory.getUI(selectedOrderType);
      OrderUIDirector director=new OrderUIDirector();
      director.setOrderForm(builder);
      director.build();
      objOrderManager.drawOrderPane(builder.getPanel());
      try{
      
      }catch(Exception es ){
        es.printStackTrace();
      }
    }

    if (e.getActionCommand().equals(OrderManager.GET_TOTAL)) {
      //Get the Visitor
      OrderVisitor visitor =
        objOrderManager.getOrderVisitor();
      totalResult = Double.toString(visitor.getOrderTotal());
      totalResult = " Orders Total = " + totalResult;
      
    }
    
  }

  public Order createOrder(String orderType,
      double orderAmount, double tax, double SH) {
    if (orderType.equalsIgnoreCase(OrderManager.CA_ORDER)) {
      return new CaliforniaOrder(orderAmount, tax);
    }
    if (orderType.equalsIgnoreCase(
      OrderManager.NON_CA_ORDER)) {
      return new NonCaliforniaOrder(orderAmount);
    }
    if (orderType.equalsIgnoreCase(
          OrderManager.OVERSEAS_ORDER)) {
      return new OverseasOrder(orderAmount, SH);
    }
    if (orderType.equalsIgnoreCase(
        OrderManager.CANADIAN_ORDER)) {
      return new CanadianOrder(orderAmount);
    }
    return null;
  }

  public ButtonHandler() {

  }
  public ButtonHandler(OrderManager inObjOrderManager) {
    objOrderManager = inObjOrderManager;
  }

} // End of class ButtonHandler

