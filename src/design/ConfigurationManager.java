package design;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import entities.Configuration;
import entities.Department;
import entities.SavedConfiguration;
import utils.DBServiceUtil;
import utils.Util;
import utils.XmlManager;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.CardLayout;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * Dialog for configuration purpouses
 */
public class ConfigurationManager extends JDialog {
	private JTextField ipField;
	private JTextField portField;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JTextField pathField;
	XmlManager xmlManager;
	JComboBox<SavedConfiguration> configurationList;
	private JTextField nameField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ConfigurationManager dialog = new ConfigurationManager();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ConfigurationManager() {
		setResizable(false);
		xmlManager = new XmlManager();
		setBounds(100, 100, 596, 384);
		getContentPane().setLayout(new CardLayout(0, 0));
		{
			JPanel newConfiguration = new JPanel();
			getContentPane().add(newConfiguration, "name_6339007418700");
			newConfiguration.setLayout(null);
			{
				JPanel borderFormatParameterConfiguration = new JPanel();
				borderFormatParameterConfiguration.setLayout(null);
				borderFormatParameterConfiguration.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Parameters", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				borderFormatParameterConfiguration.setBounds(10, 84, 560, 210);
				newConfiguration.add(borderFormatParameterConfiguration);
				{
					JLabel labelIP = new JLabel("IP:");
					labelIP.setHorizontalAlignment(SwingConstants.RIGHT);
					labelIP.setFont(new Font("Tahoma", Font.PLAIN, 14));
					labelIP.setBounds(96, 55, 77, 17);
					borderFormatParameterConfiguration.add(labelIP);
				}
				{
					JLabel labelPort = new JLabel("Port:");
					labelPort.setHorizontalAlignment(SwingConstants.RIGHT);
					labelPort.setFont(new Font("Tahoma", Font.PLAIN, 14));
					labelPort.setBounds(96, 83, 77, 17);
					borderFormatParameterConfiguration.add(labelPort);
				}
				{
					JLabel labelUsername = new JLabel("Username:");
					labelUsername.setHorizontalAlignment(SwingConstants.RIGHT);
					labelUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
					labelUsername.setBounds(96, 111, 77, 17);
					borderFormatParameterConfiguration.add(labelUsername);
				}
				{
					JLabel labelPassword = new JLabel("Password:");
					labelPassword.setHorizontalAlignment(SwingConstants.RIGHT);
					labelPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
					labelPassword.setBounds(96, 139, 77, 17);
					borderFormatParameterConfiguration.add(labelPassword);
				}
				{
					JLabel labelPath = new JLabel("Files path:");
					labelPath.setHorizontalAlignment(SwingConstants.RIGHT);
					labelPath.setFont(new Font("Tahoma", Font.PLAIN, 14));
					labelPath.setBounds(96, 167, 77, 17);
					borderFormatParameterConfiguration.add(labelPath);
				}
				{
					ipField = new JTextField();
					ipField.setToolTipText("Database server IP");
					ipField.setFont(new Font("Tahoma", Font.PLAIN, 12));
					ipField.setColumns(10);
					ipField.setBounds(183, 54, 282, 21);
					borderFormatParameterConfiguration.add(ipField);
				}
				{
					portField = new JTextField();
					portField.setToolTipText("Database server port");
					portField.setFont(new Font("Tahoma", Font.PLAIN, 12));
					portField.setColumns(10);
					portField.setBounds(183, 82, 106, 21);
					borderFormatParameterConfiguration.add(portField);
				}
				{
					usernameField = new JTextField();
					usernameField.setToolTipText("Username used to login");
					usernameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
					usernameField.setColumns(10);
					usernameField.setBounds(183, 110, 151, 21);
					borderFormatParameterConfiguration.add(usernameField);
				}
				{
					passwordField = new JPasswordField();
					passwordField.setToolTipText("Password used for login");
					passwordField.setBounds(183, 138, 151, 21);
					borderFormatParameterConfiguration.add(passwordField);
				}
				{
					pathField = new JTextField();
					pathField.setToolTipText("Custom path for generated files");
					pathField.setFont(new Font("Tahoma", Font.PLAIN, 12));
					pathField.setEditable(false);
					pathField.setColumns(10);
					pathField.setBackground(Color.WHITE);
					pathField.setBounds(183, 166, 282, 21);
					borderFormatParameterConfiguration.add(pathField);
					
					JLabel lblName = new JLabel("Name:");
					lblName.setHorizontalAlignment(SwingConstants.RIGHT);
					lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
					lblName.setBounds(96, 24, 77, 17);
					borderFormatParameterConfiguration.add(lblName);
					
					nameField = new JTextField();
					nameField.setToolTipText("Database server IP");
					nameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
					nameField.setColumns(10);
					nameField.setBounds(183, 23, 282, 21);
					borderFormatParameterConfiguration.add(nameField);
					pathField.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							String path = Util.openPathSelector();
							if(!path.equals("") && path != null) {
								pathField.setText(path);
							}
						}
					});
				}
			}
			
			configurationList = new JComboBox<SavedConfiguration>();
			configurationList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					loadSelectedConfiguration();
				}
			});
			configurationList.setBounds(10, 42, 190, 31);
			newConfiguration.add(configurationList);
			
			JButton deleteBtn = new JButton("Delete");
			deleteBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					deleteConfiguration((SavedConfiguration) configurationList.getSelectedItem());
				}
			});
			deleteBtn.setBounds(210, 42, 83, 31);
			newConfiguration.add(deleteBtn);
			
			JButton saveBtn = new JButton("Save");
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						saveConfiguration(getConfiguration());
					} catch(Exception ex) {
						ex.printStackTrace();
						Util.errorDialog("Save error", "There was an error while attempting to save the configuration. Please check that the configuration is complete.");
					}
					
				}
			});
			saveBtn.setBounds(303, 42, 83, 31);
			newConfiguration.add(saveBtn);
			
			JLabel lblNewLabel = new JLabel("Saved configurations");
			lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
			lblNewLabel.setBounds(10, 11, 184, 20);
			newConfiguration.add(lblNewLabel);
			
			JButton newBtn = new JButton("New");
			newBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					newConfiguration();
				}
			});
			newBtn.setBounds(487, 42, 83, 31);
			newConfiguration.add(newBtn);
			{
				JButton btnNewButton = new JButton("OK");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				btnNewButton.setBounds(496, 297, 74, 31);
				newConfiguration.add(btnNewButton);
			}
			{
				JButton updateBtn = new JButton("Update");
				updateBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						SavedConfiguration cfg = getConfiguration();
						try {
							deleteConfiguration((SavedConfiguration)configurationList.getSelectedItem());
							saveConfiguration(cfg);
						} catch(Exception exc ) {
							Util.errorDialog("Update error", "There was an error while attempting to update the selected object. Please make sure that everything is correct before you update");
						}
					}
				});
				updateBtn.setBounds(396, 42, 83, 31);
				newConfiguration.add(updateBtn);
			}
		}
		{
			JPanel btn = new JPanel();
			getContentPane().add(btn, "name_10554644766900");
		}
		loadConfiguration();
		
	}
	// Loads saved configuration items
	private void loadConfiguration() {
		configurationList.removeAllItems();
		ArrayList<SavedConfiguration> list = xmlManager.getConfigurationList();
		for(SavedConfiguration item: list) {
			configurationList.addItem(item);
		}
	}
	// Loads selected configuration from list to fields
	private void loadSelectedConfiguration() {
		SavedConfiguration cfg = (SavedConfiguration)configurationList.getSelectedItem();
		if(cfg != null) {
			nameField.setText(cfg.getName());
			ipField.setText(cfg.getIp());
			portField.setText(cfg.getPort());
			usernameField.setText(cfg.getUsername());
			passwordField.setText(cfg.getPassword());
			pathField.setText(cfg.getPath());
		}
	}
	// Returns a SavedConfiguration object with data from fields
	private SavedConfiguration getConfiguration() {
		return new SavedConfiguration(
				nameField.getText(),
				ipField.getText(),
				portField.getText(),
				usernameField.getText(),
				String.valueOf(passwordField.getPassword()),
				pathField.getText()
				);
	}
	// Saves configuration to file
	private void saveConfiguration(SavedConfiguration configuration) {
		if(checkDuplicateName(configuration.getName())) {
			Util.warningDialog("Configuration name duplicated", "The given name already exists, please pick another one.");
		} else {
			// Check if connection is correct
			if(DBServiceUtil.testConnection(configuration.getIp(), Integer.valueOf(configuration.getPort()), configuration.getUsername(), configuration.getPassword())) {
				Util.informationDialog("Testing connection", "Connection successful.");
				xmlManager.newConfiguration(configuration);
				loadConfiguration();
			} else {
				Util.errorDialog("Testing connection", "Connection error, check configuration. Configuration not saved");
			}	
		}
		
	}
	// Deletes configuration from file
	private void deleteConfiguration(SavedConfiguration configuration) {
		try {
			xmlManager.deleteConfiguration(configuration);
		} catch(Exception e) {
			Util.errorDialog("Deletion error", "You cannot delete any more items");
		}
		loadConfiguration();
	}
	/*
	 * Checks if the configuration name already exists
	 * @Param: name to check
	 * @Returns: true if duplicate; false if unique
	 */
	private boolean checkDuplicateName(String name) {
		boolean exists = false;
		if(configurationList.getItemCount() > 0) {
			for(int i = 0; i < configurationList.getItemCount(); i++) {
				if(name.equals(configurationList.getItemAt(i).getName())) {
					exists = true;
					break;
				}
			}
		}
		return exists;
	}
	// Resets fields data
	private void newConfiguration() {
		nameField.setText("");
		ipField.setText("");
		portField.setText("");
		usernameField.setText("");
		passwordField.setText("");
		pathField.setText("");
		
	}
}
