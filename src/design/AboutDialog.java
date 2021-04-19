package design;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.DropMode;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * Dialog for About information
 */
public class AboutDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AboutDialog dialog = new AboutDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JTextArea txtrThisIsA = new JTextArea();
		txtrThisIsA.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtrThisIsA.setBackground(SystemColor.menu);
		txtrThisIsA.setEditable(false);
		txtrThisIsA.setText("  This is a project build to generate reports from "
				+ "\r\nthe sample Employee DB.\r\n\r\nThis was developed by David Riy\r\n\r\n"
				+ "(c) Copyright David Riy 2021. All rights reserved. Here I am supposed\n"
				+ "to write a disclaimer about this application and my company and how\n"
				+ "you cannot make not fair use without my permission, but since I do not\n"
				+ "own a company. This project was build with Java and Swing Window\n"
				+ "Builder plugin. It uses JDBC to recover some data, XML DOM\n"
				+ "to save configurations and Jasper to generate the reports.");
		
		txtrThisIsA.setBounds(12, 13, 410, 185);
		contentPanel.add(txtrThisIsA);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
