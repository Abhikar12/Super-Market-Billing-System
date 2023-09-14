package com.techvidvan;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;


@SuppressWarnings("serial")
public class CashierPanel extends JFrame{
	private JTable productsTable;
	private int currentOrderID = 0;
	
	public CashierPanel() {
			setSize(800,600);
			getContentPane().setLayout(null);
			setVisible(true);
			
			JPanel productsPanel = new JPanel();
			productsPanel.setBounds(0, 300, 450, 260);
			getContentPane().add(productsPanel);
			productsPanel.setLayout(null);
			
			JButton btnRefresh = new JButton("Refresh");
			btnRefresh.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						DatabaseOperations.loadData((DefaultTableModel)productsTable.getModel(), "products");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnRefresh.setBounds(350, 10, 100, 30);
			productsPanel.add(btnRefresh);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(0, 40, 453, 200);
			productsPanel.add(scrollPane);
			
			productsTable = new JTable();
			productsTable.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null},
				},
				new String[] {
					"Id", "Name", "Price"
				}
			) {
				boolean[] columnEditables = new boolean[] {
					true, true, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			scrollPane.setViewportView(productsTable);
			
			JPanel billDisplayPanel = new JPanel();
			billDisplayPanel.setBounds(450, 0, 350, 560);
			getContentPane().add(billDisplayPanel);
			billDisplayPanel.setLayout(new BoxLayout(billDisplayPanel, BoxLayout.X_AXIS));
			
			JScrollPane billScrollpane = new JScrollPane();
			billDisplayPanel.add(billScrollpane);
			
			JTextArea billTextArea = new JTextArea();
			billTextArea.setEditable(false);
			clearBillArea(billTextArea);
			billScrollpane.setViewportView(billTextArea);
			
			JPanel billingPanel = new JPanel();
			billingPanel.setBounds(0, 0, 450, 300);
			getContentPane().add(billingPanel);
			billingPanel.setLayout(null);
			
			JLabel lblSelectCustomer = new JLabel("Select Customer:");
			lblSelectCustomer.setBounds(21, 12, 135, 15);
			billingPanel.add(lblSelectCustomer);
			
			JComboBox<String> customerBox = new JComboBox<String>();
			customerBox.setBounds(177, 7, 200, 30);
			billingPanel.add(customerBox);
			
			JButton btnDiscardOrder = new JButton("Discard Order");
			
			btnDiscardOrder.setEnabled(false);
			btnDiscardOrder.setBounds(50, 75, 300, 25);
			billingPanel.add(btnDiscardOrder);
			
			JLabel lblSelectProducts = new JLabel("Select Product:");
			lblSelectProducts.setBounds(21, 117, 135, 15);
			billingPanel.add(lblSelectProducts);
			
			JComboBox<String> productBox = new JComboBox<String>();
			productBox.setEnabled(false);
			productBox.setBounds(177, 112, 200, 30);
			billingPanel.add(productBox);
			
			JSpinner quantitySpinner = new JSpinner();
			quantitySpinner.setEnabled(false);
			quantitySpinner.setBounds(177, 145, 200, 30);
			billingPanel.add(quantitySpinner);
			
			JLabel lblQuantity = new JLabel("Quantity:");
			lblQuantity.setBounds(21, 152, 135, 15);
			billingPanel.add(lblQuantity);
			
			JButton btnAddToBill = new JButton("Add to Bill");
			btnAddToBill.setEnabled(false);
			btnAddToBill.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String prodID = (String)productBox.getSelectedItem();
					prodID = prodID.substring(0, prodID.indexOf('-'));
					String[] product = new String[3];
					try {
						 product= DatabaseOperations.getProd(Integer.valueOf(prodID));
							float price = (int)quantitySpinner.getValue()*Float.valueOf(product[2]);

						 DatabaseOperations.addOrderItems(currentOrderID,
								 							Integer.valueOf(prodID), 
								 							(int)quantitySpinner.getValue(),
								 							Float.valueOf(price) );
					} catch (NumberFormatException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					billTextArea.append("*"+product[0]+"\t"+product[1]+"\t"+(int)quantitySpinner.getValue()+"\t"+(int)quantitySpinner.getValue()*Float.valueOf(product[2])+"\n");
				}
			});
			btnAddToBill.setBounds(210, 187, 140, 30);
			billingPanel.add(btnAddToBill);
			
			JButton btnRemoveLast = new JButton("Remove Last");
			btnRemoveLast.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						DatabaseOperations.deleteOrderItem(currentOrderID);
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					String text = billTextArea.getText();
					try {
						text = text.substring(0, text.lastIndexOf("*"));
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(btnRemoveLast, "NO Items to remove");
						e1.printStackTrace();
					}
					billTextArea.setText(text);
				}
			});
			btnRemoveLast.setEnabled(false);
			btnRemoveLast.setBounds(210, 220, 140, 30);
			billingPanel.add(btnRemoveLast);
			
			JButton btnGenerateBill = new JButton("Generate Bill");
			btnGenerateBill.setEnabled(false);
			btnGenerateBill.setBounds(100, 260, 200, 35);
			billingPanel.add(btnGenerateBill);
			
			JButton btnCreateNewOrder = new JButton("Create new Order for Customer");
			btnCreateNewOrder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					clearBillArea(billTextArea);
					btnCreateNewOrder.setEnabled(false);
					customerBox.setEnabled(false);
					productBox.setEnabled(true);
					quantitySpinner.setEnabled(true);
					btnAddToBill.setEnabled(true);
					btnRemoveLast.setEnabled(true);
					btnGenerateBill.setEnabled(true);
					btnDiscardOrder.setEnabled(true);
					
					String custID = (String)customerBox.getSelectedItem(); 
					try {
						currentOrderID = DatabaseOperations.createNewOrder(Integer.valueOf(custID.substring(0, custID.indexOf('-'))));
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					billTextArea.append("Customer name :"+custID.substring(custID.lastIndexOf('-')+1)+"\n");
					billTextArea.append("Order ID:"+currentOrderID+"\n\n");
					billTextArea.append("ProdID\tName\tQt\tRs.\n");
				}
			});
			btnCreateNewOrder.setBounds(50, 45, 300, 25);
			billingPanel.add(btnCreateNewOrder);
			
			btnDiscardOrder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnCreateNewOrder.setEnabled(true);
					customerBox.setEnabled(true);
					
					productBox.setEnabled(false);
					quantitySpinner.setEnabled(false);
					btnAddToBill.setEnabled(false);
					btnRemoveLast.setEnabled(false);
					btnGenerateBill.setEnabled(false);
					btnDiscardOrder.setEnabled(false);
					try {
						DatabaseOperations.discardOrder(currentOrderID);
						clearBillArea(billTextArea);
						currentOrderID =0;
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			
			btnGenerateBill.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnCreateNewOrder.setEnabled(true);
					customerBox.setEnabled(true);
					productBox.setEnabled(false);
					quantitySpinner.setEnabled(false);
					btnAddToBill.setEnabled(false);
					btnRemoveLast.setEnabled(false);
					btnGenerateBill.setEnabled(false);
					btnDiscardOrder.setEnabled(false);
					float price =0;
					try {
						price = DatabaseOperations.getTotalPrice(currentOrderID);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					billTextArea.append("\t\tGrand Total\t"+price);
					currentOrderID =0;
				}
			});
			try {
				DatabaseOperations.updateCombox("customers",customerBox);
				DatabaseOperations.updateCombox("products",productBox);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}
	
//	Method to reset the bill text area to the default
	private void clearBillArea(JTextArea area) {
		area.setText("\t-----------Gurukul Supermarket----------\n");
	}
	
	
	

}
