package com.techvidvan;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class AdminLogin  {

	private JFrame frame;
	private JTextField adminNameField;
	private JPasswordField passwordField;
	private final String  adminName = "Admin";
	private final String password = "1234";

	public AdminLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setTitle("Admin Login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblAdmin = new JLabel("Admin Name");
		lblAdmin.setBounds(0, 67, 173, 36);
		frame.getContentPane().add(lblAdmin);
		
		adminNameField = new JTextField();
		adminNameField.setBounds(222, 68, 184, 36);
		frame.getContentPane().add(adminNameField);
		adminNameField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(0, 134, 173, 36);
		frame.getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(220, 135, 184, 36);
		frame.getContentPane().add(passwordField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(161, 201, 117, 25);
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(adminNameField.getText().equals(adminName) && password.equals(new String(passwordField.getPassword()))) {					frame.dispose();
					AdminPanel ap = new  AdminPanel(); 
					
				}else {
					JOptionPane.showMessageDialog(btnLogin, "Incorrect Username or Password");;
				}
				
			}
		});
		frame.getContentPane().add(btnLogin);
		frame.setVisible(true);
	}
}
