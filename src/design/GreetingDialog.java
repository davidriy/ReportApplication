package design;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entities.Configuration;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/*
 * Greeting dialog for initial run
 */
public class GreetingDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Configuration configuration;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GreetingDialog dialog = new GreetingDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GreetingDialog() {
		setBounds(100, 100, 450, 248);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("THANKS FOR USING THIS APP!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 10, 416, 35);
		contentPanel.add(lblNewLabel);
		
		JTextPane txtpnPlease = new JTextPane();
		txtpnPlease.setText("Before you start using the app please make sure that the given \nconfiguration is correct! If it is not it will not work.\n\nIf you need any help do not hesitate in contacting me. My details are:\n\nEmail: davidnriy@gmail.com\nAddress: C/ Arner, 3");
		txtpnPlease.setFont(new Font("Arial", Font.PLAIN, 12));
		txtpnPlease.setBounds(10, 55, 416, 114);
		contentPanel.add(txtpnPlease);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			JCheckBox checkboxGreeting = new JCheckBox("Do not display this again");
			checkboxGreeting.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					configuration = Configuration.obtainConfig();
					configuration.setGreetingDisplayed(checkboxGreeting.isSelected());
					Configuration.writeConfig(configuration);
					
				}
			});
			buttonPane.add(checkboxGreeting);
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

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
}
