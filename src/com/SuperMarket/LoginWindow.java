package com.techvidvan;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.GridLayout;

public class LoginWindow {

	private JFrame frmLoginWindow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.frmLoginWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginWindow() {
		DatabaseOperations.dbInit();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLoginWindow = new JFrame();
		frmLoginWindow.getContentPane().setBackground(new Color(192, 191, 188));
		frmLoginWindow.setTitle("Supermarket billing system by TechVidvan");
		frmLoginWindow.setBounds(100, 100, 440, 300);
		frmLoginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLoginWindow.getContentPane().setLayout(new GridLayout(0, 1, 0, 10));
		
		JLabel lblWelcome = new JLabel("SuperMarket Billing System");
		lblWelcome.setForeground(new Color(0, 0, 0));
		lblWelcome.setFont(new Font("Jua", Font.BOLD, 30));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		frmLoginWindow.getContentPane().add(lblWelcome);
		
		JButton btnAdmin = new JButton("Admin");
		btnAdmin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frmLoginWindow.dispose();
				new AdminLogin();
			}
			
		});
		
		JLabel lblInstruction = new JLabel("Please Select Login profile:");
		lblInstruction.setForeground(Color.BLACK);
		frmLoginWindow.getContentPane().add(lblInstruction);
		frmLoginWindow.getContentPane().add(btnAdmin);
		
		JButton btnCashier = new JButton("Cashier");
		btnCashier.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frmLoginWindow.dispose();
				new CashierPanel();

				
				
			}
		});
		frmLoginWindow.getContentPane().add(btnCashier);
	}
}
