package com.techvidvan;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class AdminPanel extends JFrame {
	private JTextField customerNameField;
	private JTextField phoneField;
	private JTextField emailField;
	private JTextField addressField;
	private JTable cusotmerTable;
	private JTextField txtCustdeletefield;
	private JTextField productNameField;
	private JTextField priceField;
	private JTable productTable;
	private JTextField deleteProdField;
	
	public AdminPanel() {
		setSize(700,500);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel customerPanel = new JPanel();
		tabbedPane.addTab("Customers", null, customerPanel, null);
		customerPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel addCustomerPanel = new JPanel();
		customerPanel.add(addCustomerPanel);
		addCustomerPanel.setLayout(null);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(0, 0, 173, 87);
		addCustomerPanel.add(lblName);
		
		customerNameField = new JTextField();
		customerNameField.setBounds(173, 0, 173, 87);
		addCustomerPanel.add(customerNameField);
		customerNameField.setColumns(10);
		
		JLabel lblPhone = new JLabel("Phone:");
		lblPhone.setBounds(0, 87, 173, 87);
		addCustomerPanel.add(lblPhone);
		
		phoneField = new JTextField();
		phoneField.setBounds(173, 87, 173, 87);
		addCustomerPanel.add(phoneField);
		phoneField.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email;");
		lblEmail.setBounds(0, 174, 173, 87);
		addCustomerPanel.add(lblEmail);
		
		emailField = new JTextField();
		emailField.setBounds(173, 174, 173, 87);
		emailField.setColumns(10);
		addCustomerPanel.add(emailField);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(0, 261, 173, 87);
		addCustomerPanel.add(lblAddress);
		
		addressField = new JTextField();
		addressField.setBounds(173, 261, 173, 87);
		addressField.setColumns(10);
		addCustomerPanel.add(addressField);
		
		JButton btnAddCustomer = new JButton("Add customer");
		btnAddCustomer.setBounds(0, 348, 340, 87);
		btnAddCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DatabaseOperations.addCustomer(customerNameField.getText(), phoneField.getText(), emailField.getText(), addressField.getText());
					JOptionPane.showMessageDialog(btnAddCustomer, "Added Successfully");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(btnAddCustomer, "Can't Add customer");
					e1.printStackTrace();
				}
			}
		});
		addCustomerPanel.add(btnAddCustomer);
		
		JPanel displayPanel = new JPanel();
		customerPanel.add(displayPanel);
		displayPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 36, 347, 400);
		displayPanel.add(scrollPane);
		
		cusotmerTable = new JTable();
		cusotmerTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
			},
			new String[] {
				"id", "Name", "Phone", "Email", "Address"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		cusotmerTable.getColumnModel().getColumn(0).setResizable(false);
		cusotmerTable.getColumnModel().getColumn(1).setResizable(false);
		cusotmerTable.getColumnModel().getColumn(2).setResizable(false);
		scrollPane.setViewportView(cusotmerTable);
		
		txtCustdeletefield = new JTextField();
		txtCustdeletefield.setBounds(0, 0, 125, 36);
		displayPanel.add(txtCustdeletefield);
		txtCustdeletefield.setColumns(10);
		
		JButton btnDeletecust = new JButton("Delete");
		btnDeletecust.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DatabaseOperations.delete(Integer.valueOf(txtCustdeletefield.getText())  ,"customers" );
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(btnDeletecust, "Entern numeric value");
					e1.printStackTrace();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(btnDeletecust, "can't delete");
					e1.printStackTrace();
				}
			}
		});
		btnDeletecust.setBounds(125, 0, 117, 36);
		displayPanel.add(btnDeletecust);
		
		JButton btnRefereshCust = new JButton("Refresh");
		btnRefereshCust.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DatabaseOperations.loadData((DefaultTableModel)cusotmerTable.getModel(), "customers");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRefereshCust.setBounds(240, 0, 100, 36);
		displayPanel.add(btnRefereshCust);
		
		JPanel productsPanel = new JPanel();
		tabbedPane.addTab("Products", null, productsPanel, null);
		productsPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel addProductPanel = new JPanel();
		addProductPanel.setLayout(null);
		productsPanel.add(addProductPanel);
		
		JLabel lblName_1 = new JLabel("Name:");
		lblName_1.setBounds(0, 0, 173, 87);
		addProductPanel.add(lblName_1);
		
		productNameField = new JTextField();
		productNameField.setColumns(10);
		productNameField.setBounds(173, 0, 173, 87);
		addProductPanel.add(productNameField);
		
		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setBounds(0, 87, 173, 87);
		addProductPanel.add(lblPrice);
		
		priceField = new JTextField();
		priceField.setColumns(10);
		priceField.setBounds(173, 87, 173, 87);
		addProductPanel.add(priceField);
		
		JButton btnAddProduct = new JButton("Add Product");
		btnAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DatabaseOperations.addProduct(productNameField.getText(), Float.valueOf(priceField.getText()));
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(btnAddProduct, "Enter decimal values");
					e1.printStackTrace();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnAddProduct.setBounds(0, 200, 340, 87);
		addProductPanel.add(btnAddProduct);
		
		JButton btnDeleteProduct = new JButton("Delete");
		btnDeleteProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DatabaseOperations.delete(Integer.valueOf(deleteProdField.getText()), "products");
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(btnDeleteProduct, e1.getMessage());
					e1.printStackTrace();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(btnDeleteProduct, e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		btnDeleteProduct.setBounds(230, 399, 117, 30);
		addProductPanel.add(btnDeleteProduct);
		
		deleteProdField = new JTextField();
		deleteProdField.setBounds(100, 399, 114, 30);
		addProductPanel.add(deleteProdField);
		deleteProdField.setColumns(10);
		
		JPanel productsDisplayPanel = new JPanel();
		productsPanel.add(productsDisplayPanel);
		productsDisplayPanel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 36, 347, 400);
		productsDisplayPanel.add(scrollPane_1);
		
		productTable = new JTable();
		productTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
			},
			new String[] {
				"id", "Name", "Price"
			}
		));
		scrollPane_1.setViewportView(productTable);
		
		JButton btnRefreshProducts = new JButton("Refresh");
		btnRefreshProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DatabaseOperations.loadData((DefaultTableModel)productTable.getModel(), "products");
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnRefreshProducts.setBounds(0, 0, 117, 30);
		productsDisplayPanel.add(btnRefreshProducts);
		setVisible(true);
	}
}
