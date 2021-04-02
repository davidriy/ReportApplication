package design;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import org.jdatepicker.DateModel;
import org.jdatepicker.DatePicker;
import org.jdatepicker.JDatePicker;

import entities.Configuration;
import entities.Department;
import entities.Employee;
import entities.SavedConfiguration;
import entities.XmlManager;
import net.sf.jasperreports.engine.JRException;
import utils.ConverterUtil;
import utils.DBServiceUtil;
import utils.JasperUtil;
import utils.Util;

import javax.swing.ImageIcon;

public class App {
	/*
	 * Init and main
	 */
	
	// Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frmDrReports.setVisible(true);
					// Show greetings dialog
					window.showGreetingDialog();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	// Create the App
	public App() {
		initialize();
		
	}
	// Initialize the contents of the frame.
	private void initialize() {
		// Frame setup
		frmDrReports = new JFrame();
		frmDrReports.setResizable(false);
		frmDrReports.setTitle("Dr. Reports");
		frmDrReports.setBounds(100, 100, 600, 400);
		frmDrReports.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDrReports.getContentPane().setLayout(null);
		// Content initialization
		menuBarMng();
		contentPanelDefinition();
		mainPanelMng();
		configurationPanelMng();
		firstReportPanelMng();
		secondReportPanelMng();
		thirdReportPanelMng();
		loadConfiguration();
		loadSavedConfiguration();
		conditionallyDisplayedComponents();
	}
	
	/*
	 * Variables
	 */
	
	// Main frame
	private JFrame frmDrReports;
	// Panels
	private JPanel contentPanel;
	private JPanel mainPanel;
	private JPanel configurationPanel;
	private JPanel firstReportPanel;
	private JPanel secondReportPanel;
	private JPanel thirdReportPanel;
	// Configuration fields
	private JTextField ipField;
	private JTextField portField;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JTextField pathField;
	// Configuration format outputs
	private JCheckBox isCSVFirstReport;
	private JCheckBox isHTMLFirstReport;
	private JCheckBox isPDFFirstReport;
	private JCheckBox isCSVSecondReport;
	private JCheckBox isHTMLSecondReport;
	private JCheckBox isPDFSecondReport;
	private JCheckBox isCSVThirdReport;
	private JCheckBox isHTMLThirdReport;
	private JCheckBox isPDFThirdReport;
	private boolean configurationChecked = false;
	private boolean greetingShow = false;
	// Labels conditionally display
	private JLabel lblCheckConfiguration;
	// Configuration class which holds actual configuration
	private Configuration configuration;
	// Variables
	private JComboBox<Department> comboBoxFirstReportDepartments;
	private JComboBox<Employee> comboBoxSecondReportEmployees;
	private JComboBox<SavedConfiguration> configurationList;
	// Date
	DatePicker dateFromPicker;
	DatePicker dateToPicker;
	private String dateFrom;
	private String dateTo;
	// XmlManager
	private XmlManager xmlManager;
	
	/*
	 * Panels definition, components, and actions
	 */
	
	// Content panel init - CardLayout which allows to switch between panels
	private void contentPanelDefinition() {
		contentPanel = new JPanel();
		contentPanel.setBounds(0, 24, 584, 337);
		frmDrReports.getContentPane().add(contentPanel);
		contentPanel.setLayout(new CardLayout(0, 0));
	}
	// Main panel init and content
	private void mainPanelMng(){
		mainPanel = new JPanel();
		contentPanel.add(mainPanel, Panels.MAIN_PANEL.toString());
		mainPanel.setLayout(null);
		
		JLabel labelTitle = new JLabel("REPORT GENERATOR");
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
		labelTitle.setBounds(78, 9, 430, 54);
		mainPanel.add(labelTitle);
		
		JPanel borderMain = new JPanel();
		borderMain.setLayout(null);
		borderMain.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		borderMain.setBounds(78, 76, 430, 193);
		mainPanel.add(borderMain);
		
		JLabel lblReports = new JLabel("Reports");
		lblReports.setHorizontalAlignment(SwingConstants.CENTER);
		lblReports.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblReports.setBounds(10, 82, 410, 37);
		borderMain.add(lblReports);
		
		JButton btnFirstReport = new JButton("First");
		btnFirstReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchToPanel(Panels.FIRST_REPORT_PANEL);
			}
		});
		btnFirstReport.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnFirstReport.setBounds(20, 130, 126, 37);
		borderMain.add(btnFirstReport);
		
		JButton btnConfiguration = new JButton("Configuration");
		btnConfiguration.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnConfiguration.setBounds(142, 32, 152, 37);
		borderMain.add(btnConfiguration);
		
		JButton btnSecondReport = new JButton("Second");
		btnSecondReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchToPanel(Panels.SECOND_REPORT_PANEL);
			}
		});
		btnSecondReport.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSecondReport.setBounds(156, 130, 126, 37);
		borderMain.add(btnSecondReport);
		
		JButton btnThirdReport = new JButton("Third");
		btnThirdReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchToPanel(Panels.THIRD_REPORT_PANEL);
			}
		});
		btnThirdReport.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnThirdReport.setBounds(292, 130, 126, 37);
		borderMain.add(btnThirdReport);
		
		JLabel lblCopyright = new JLabel("\u00A9 Copyright David Riy");
		lblCopyright.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblCopyright.setBounds(501, 320, 83, 17);
		mainPanel.add(lblCopyright);
		
		lblCheckConfiguration = new JLabel("Remember to check if the configuration is correct!");
		lblCheckConfiguration.setHorizontalAlignment(SwingConstants.CENTER);
		lblCheckConfiguration.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCheckConfiguration.setBounds(78, 282, 430, 25);
		mainPanel.add(lblCheckConfiguration);
		
		btnConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchToPanel(Panels.CONFIGURATION_PANEL);
				// When configuration is entered - reminder label must be disabled and config file updated
				configurationEntered();
			}
		});
	}
	// Configuration panel init and content
	private void configurationPanelMng() {
		configurationPanel = new JPanel();
		contentPanel.add(configurationPanel, Panels.CONFIGURATION_PANEL.toString());
		configurationPanel.setLayout(null);
		
		JLabel labelSubtitle = new JLabel("Configuration");
		labelSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelSubtitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		labelSubtitle.setBounds(0, 0, 584, 39);
		configurationPanel.add(labelSubtitle);
		
		JPanel borderFormatParameterConfiguration = new JPanel();
		borderFormatParameterConfiguration.setLayout(null);
		borderFormatParameterConfiguration.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Parameters", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		borderFormatParameterConfiguration.setBounds(12, 109, 560, 174);
		configurationPanel.add(borderFormatParameterConfiguration);
		
		JLabel labelIP = new JLabel("IP:");
		labelIP.setHorizontalAlignment(SwingConstants.RIGHT);
		labelIP.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelIP.setBounds(96, 27, 77, 17);
		borderFormatParameterConfiguration.add(labelIP);
		
		JLabel labelPort = new JLabel("Port:");
		labelPort.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPort.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelPort.setBounds(96, 55, 77, 17);
		borderFormatParameterConfiguration.add(labelPort);
		
		JLabel labelUsername = new JLabel("Username:");
		labelUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		labelUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelUsername.setBounds(96, 83, 77, 17);
		borderFormatParameterConfiguration.add(labelUsername);
		
		JLabel labelPassword = new JLabel("Password:");
		labelPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelPassword.setBounds(96, 111, 77, 17);
		borderFormatParameterConfiguration.add(labelPassword);
		
		JLabel labelPath = new JLabel("Files path:");
		labelPath.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPath.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelPath.setBounds(96, 139, 77, 17);
		borderFormatParameterConfiguration.add(labelPath);
		
		ipField = new JTextField();
		ipField.setBackground(Color.WHITE);
		ipField.setEditable(false);
		ipField.setToolTipText("Database server IP");
		ipField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		ipField.setColumns(10);
		ipField.setBounds(183, 26, 282, 21);
		borderFormatParameterConfiguration.add(ipField);
		
		portField = new JTextField();
		portField.setBackground(Color.WHITE);
		portField.setEditable(false);
		portField.setToolTipText("Database server port");
		portField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		portField.setColumns(10);
		portField.setBounds(183, 54, 106, 21);
		borderFormatParameterConfiguration.add(portField);
		
		usernameField = new JTextField();
		usernameField.setBackground(Color.WHITE);
		usernameField.setEditable(false);
		usernameField.setToolTipText("Username used to login");
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		usernameField.setColumns(10);
		usernameField.setBounds(183, 82, 151, 21);
		borderFormatParameterConfiguration.add(usernameField);
		
		passwordField = new JPasswordField();
		passwordField.setBackground(Color.WHITE);
		passwordField.setEditable(false);
		passwordField.setToolTipText("Password used for login");
		passwordField.setBounds(183, 110, 151, 21);
		borderFormatParameterConfiguration.add(passwordField);
		
		pathField = new JTextField();
		pathField.setBackground(Color.WHITE);
		pathField.setEditable(false);
		pathField.setToolTipText("Custom path for generated files");
		pathField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pathField.setColumns(10);
		pathField.setBounds(183, 138, 282, 21);
		borderFormatParameterConfiguration.add(pathField);
		
		JButton btnSaveConfiguration = new JButton("Select configuration");
		btnSaveConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAndTestConfiguration();
			}
		});
		btnSaveConfiguration.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSaveConfiguration.setBounds(355, 294, 219, 32);
		configurationPanel.add(btnSaveConfiguration);
		
		JButton btnBack = new JButton("Close");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchToPanel(Panels.MAIN_PANEL);
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBack.setBounds(12, 294, 189, 32);
		configurationPanel.add(btnBack);
		
		JLabel lblCopyright = new JLabel("\u00A9 Copyright David Riy");
		lblCopyright.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblCopyright.setBounds(242, 320, 83, 17);
		configurationPanel.add(lblCopyright);
		
		configurationList = new JComboBox<SavedConfiguration>();
		configurationList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadSelectedConfiguration();
			}
		});
		configurationList.setBounds(12, 66, 250, 32);
		configurationPanel.add(configurationList);
		
		JButton btnManageSavedConfiguration = new JButton("Manage");
		btnManageSavedConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfigurationManager cfg = new ConfigurationManager();
				cfg.setVisible(true);
			}
		});
		btnManageSavedConfiguration.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnManageSavedConfiguration.setBounds(401, 66, 171, 32);
		configurationPanel.add(btnManageSavedConfiguration);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadSavedConfiguration();
			}
		});
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRefresh.setBounds(272, 66, 119, 32);
		configurationPanel.add(btnRefresh);
		
		configuration = new Configuration();
		xmlManager = new XmlManager();
	}
	// Manages First Report panel and components
	private void firstReportPanelMng() {
		firstReportPanel = new JPanel();
		contentPanel.add(firstReportPanel, Panels.FIRST_REPORT_PANEL.toString());
		firstReportPanel.setLayout(null);
		
		JLabel lblFirstReportSubTitle = new JLabel("Select the department you wish");
		lblFirstReportSubTitle.setBounds(22, 45, 550, 27);
		firstReportPanel.add(lblFirstReportSubTitle);
		
		JLabel lblFirstReportTitle = new JLabel("Employees from given department");
		lblFirstReportTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblFirstReportTitle.setBounds(12, 13, 560, 27);
		firstReportPanel.add(lblFirstReportTitle);
		
		comboBoxFirstReportDepartments = new JComboBox<Department>();
		comboBoxFirstReportDepartments.setBounds(12, 74, 560, 34);
		firstReportPanel.add(comboBoxFirstReportDepartments);
		
		JPanel borderFormatFirstReport = new JPanel();
		borderFormatFirstReport.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Output format", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		borderFormatFirstReport.setBounds(189, 258, 206, 49);
		firstReportPanel.add(borderFormatFirstReport);
		borderFormatFirstReport.setLayout(null);		
		
		isCSVFirstReport = new JCheckBox("CSV");
		isCSVFirstReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveConfiguration();
			}
		});
		isCSVFirstReport.setBounds(12, 18, 56, 25);
		borderFormatFirstReport.add(isCSVFirstReport);
		
		isHTMLFirstReport = new JCheckBox("HTML");
		isHTMLFirstReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveConfiguration();
			}
		});
		isHTMLFirstReport.setBounds(72, 18, 66, 25);
		borderFormatFirstReport.add(isHTMLFirstReport);
		
		isPDFFirstReport = new JCheckBox("PDF");
		isPDFFirstReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveConfiguration();
			}
		});
		isPDFFirstReport.setBounds(142, 18, 56, 25);
		borderFormatFirstReport.add(isPDFFirstReport);
		
		JButton btnBackFirstReport = new JButton("Close");
		btnBackFirstReport.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBackFirstReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchToPanel(Panels.MAIN_PANEL);
			}
		});
		btnBackFirstReport.setBounds(12, 266, 165, 41);
		firstReportPanel.add(btnBackFirstReport);
		
		JButton btnGenerateFirstReport = new JButton("Generate");
		btnGenerateFirstReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateReport(Panels.FIRST_REPORT_PANEL);
			}
		});
		btnGenerateFirstReport.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnGenerateFirstReport.setBounds(407, 266, 165, 41);
		firstReportPanel.add(btnGenerateFirstReport);
		
		JLabel lblCopyright = new JLabel("\u00A9 Copyright David Riy");
		lblCopyright.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblCopyright.setBounds(501, 320, 83, 17);
		firstReportPanel.add(lblCopyright);
		
		loadFirstReportComboBox();
	}
	// Manages Second Report panel and components
	private void secondReportPanelMng() {
		secondReportPanel = new JPanel();
		secondReportPanel.setLayout(null);
		contentPanel.add(secondReportPanel, Panels.SECOND_REPORT_PANEL.toString());
		
		JLabel lblSecondReportTitle = new JLabel("Employee's life report");
		lblSecondReportTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSecondReportTitle.setBounds(12, 13, 560, 27);
		secondReportPanel.add(lblSecondReportTitle);
		
		JLabel lblSecondReportSubTitle = new JLabel("Select the employee you wish");
		lblSecondReportSubTitle.setBounds(22, 45, 550, 27);
		secondReportPanel.add(lblSecondReportSubTitle);
		
		JButton btnBackSecondReport = new JButton("Close");
		btnBackSecondReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchToPanel(Panels.MAIN_PANEL);
			}
		});
		btnBackSecondReport.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBackSecondReport.setBounds(12, 266, 165, 41);
		secondReportPanel.add(btnBackSecondReport);
		
		JPanel borderFormatSecondReport = new JPanel();
		borderFormatSecondReport.setLayout(null);
		borderFormatSecondReport.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Output format", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		borderFormatSecondReport.setBounds(189, 258, 206, 49);
		secondReportPanel.add(borderFormatSecondReport);
		
		isCSVSecondReport = new JCheckBox("CSV");
		isCSVSecondReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveConfiguration();
			}
		});
		isCSVSecondReport.setBounds(12, 18, 56, 25);
		borderFormatSecondReport.add(isCSVSecondReport);
		
		isHTMLSecondReport = new JCheckBox("HTML");
		isHTMLSecondReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveConfiguration();
			}
		});
		isHTMLSecondReport.setBounds(72, 18, 66, 25);
		borderFormatSecondReport.add(isHTMLSecondReport);
		
		isPDFSecondReport = new JCheckBox("PDF");
		isPDFSecondReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveConfiguration();
			}
		});
		isPDFSecondReport.setBounds(142, 18, 56, 25);
		borderFormatSecondReport.add(isPDFSecondReport);
		
		JButton btnGenerateSecondReport = new JButton("Generate");
		btnGenerateSecondReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateReport(Panels.SECOND_REPORT_PANEL);
			}
		});
		btnGenerateSecondReport.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnGenerateSecondReport.setBounds(407, 266, 165, 41);
		secondReportPanel.add(btnGenerateSecondReport);
		
		comboBoxSecondReportEmployees = new JComboBox<Employee>();
		comboBoxSecondReportEmployees.setBounds(12, 71, 560, 34);
		secondReportPanel.add(comboBoxSecondReportEmployees);
		
		JLabel lblCopyright = new JLabel("\u00A9 Copyright David Riy");
		lblCopyright.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblCopyright.setBounds(501, 320, 83, 17);
		secondReportPanel.add(lblCopyright);
		
		// Load data into combo box
		loadSecondReportComboBox();
	}
	// Manages Third Report panel and components
	private void thirdReportPanelMng() {
		thirdReportPanel = 
				new JPanel();
		thirdReportPanel.setLayout(null);
		contentPanel.add(thirdReportPanel, Panels.THIRD_REPORT_PANEL.toString());
		
		JLabel lblThirdReportTitle = new JLabel("List of personnel by department");
		lblThirdReportTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblThirdReportTitle.setBounds(12, 13, 560, 27);
		thirdReportPanel.add(lblThirdReportTitle);
		
		JLabel lblThirdReportSubTitle = new JLabel("Select the dates to filter with");
		lblThirdReportSubTitle.setBounds(22, 45, 550, 27);
		thirdReportPanel.add(lblThirdReportSubTitle);
		
		JButton btnBackThirdReport = new JButton("Close");
		btnBackThirdReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchToPanel(Panels.MAIN_PANEL);
			}
		});
		btnBackThirdReport.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBackThirdReport.setBounds(12, 266, 165, 41);
		thirdReportPanel.add(btnBackThirdReport);
		
		JPanel borderFormatThirdReport = new JPanel();
		borderFormatThirdReport.setLayout(null);
		borderFormatThirdReport.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Output format", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		borderFormatThirdReport.setBounds(189, 258, 206, 49);
		thirdReportPanel.add(borderFormatThirdReport);
		
		isCSVThirdReport = new JCheckBox("CSV");
		isCSVThirdReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveConfiguration();
			}
		});
		isCSVThirdReport.setBounds(12, 18, 56, 25);
		borderFormatThirdReport.add(isCSVThirdReport);
		
		isHTMLThirdReport = new JCheckBox("HTML");
		isHTMLThirdReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveConfiguration();
			}
		});
		isHTMLThirdReport.setBounds(72, 18, 66, 25);
		borderFormatThirdReport.add(isHTMLThirdReport);
		
		isPDFThirdReport = new JCheckBox("PDF");
		isPDFThirdReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveConfiguration();
			}
		});
		isPDFThirdReport.setBounds(142, 18, 56, 25);
		borderFormatThirdReport.add(isPDFThirdReport);
		
		JButton btnGenerateThirdReport = new JButton("Generate");
		btnGenerateThirdReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateReport(Panels.THIRD_REPORT_PANEL);
			}
		});
		btnGenerateThirdReport.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnGenerateThirdReport.setBounds(407, 266, 165, 41);
		thirdReportPanel.add(btnGenerateThirdReport);
		
		JLabel lblCopyright = new JLabel("\u00A9 Copyright David Riy");
		lblCopyright.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblCopyright.setBounds(501, 320, 83, 17);
		thirdReportPanel.add(lblCopyright);
		
		// Create panel which will hold the date picker
		JPanel dateFromPanel = new JPanel();
		dateFromPanel.setBorder(new TitledBorder(null, "Date from", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		// Instanciate date picker
		dateFromPicker = new JDatePicker();
		
		// Format
		dateFromPanel.setBounds(12, 82, 275, 49);
		dateFromPanel.setLayout(new BorderLayout());
		// Add date picker to its panel and the panel to the thirdReportPanel
		dateFromPanel.add((JComponent) dateFromPicker);
		thirdReportPanel.add(dateFromPanel, BorderLayout.WEST);
		dateFromPicker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dateFrom = getDateStrFromPickerRaw(dateFromPicker);        
            }
        });
		
		// Create panel which will hold the date picker
		JPanel dateToPanel = new JPanel();
		dateToPanel.setBorder(new TitledBorder(null, "Date to", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		// Instanciate date picker
		dateToPicker = new JDatePicker();
		// Format
		dateToPanel.setBounds(297, 82, 275, 49);
		dateToPanel.setLayout(new BorderLayout());
		// Add date picker to its panel and the panel to the thirdReportPanel
		dateToPanel.add((JComponent) dateToPicker);
		thirdReportPanel.add(dateToPanel, BorderLayout.WEST);
		dateToPicker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dateTo = getDateStrFromPickerRaw(dateToPicker);
            }
        });
		

	}
	// Manages Menu bar items
	private void menuBarMng() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 584, 22);
		frmDrReports.getContentPane().add(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenu fileMenuOpen = new JMenu("Open");
		fileMenu.add(fileMenuOpen);
		
		JMenuItem fileMenuMain = new JMenuItem("Main");
		fileMenuMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchToPanel(Panels.MAIN_PANEL);
			}
		});
		fileMenuOpen.add(fileMenuMain);
		
		JMenuItem fileMenuConfiguration = new JMenuItem("Configuration");
		fileMenuConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchToPanel(Panels.CONFIGURATION_PANEL);
				configurationEntered();
			}
		});
		fileMenuOpen.add(fileMenuConfiguration);
		
		JMenuItem fileMenuFirstReport = new JMenuItem("First Report");
		fileMenuFirstReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchToPanel(Panels.FIRST_REPORT_PANEL);
			}
		});
		fileMenuOpen.add(fileMenuFirstReport);
		
		JMenuItem fileMenuSecondReport = new JMenuItem("Second Report");
		fileMenuSecondReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchToPanel(Panels.SECOND_REPORT_PANEL);
			}
		});
		fileMenuOpen.add(fileMenuSecondReport);
		
		JMenuItem fileMenuThirdReport = new JMenuItem("Third Report");
		fileMenuThirdReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchToPanel(Panels.THIRD_REPORT_PANEL);
			}
		});
		fileMenuOpen.add(fileMenuThirdReport);
		
		JMenuItem fileMenuExit = new JMenuItem("Exit");
		fileMenuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Instanciate confirm dialog
				int dialogButtons = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Close application", dialogButtons);
				if(dialogResult == JOptionPane.YES_OPTION) System.exit(0);
			}
		});
		fileMenu.add(fileMenuExit);
		
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		
		JMenuItem editMenuSavedConfigurations = new JMenuItem("Saved configurations");
		editMenuSavedConfigurations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfigurationManager cfg = new ConfigurationManager();
				cfg.setVisible(true);
			}
		});
		editMenu.add(editMenuSavedConfigurations);
		
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		JMenuItem helpMenuHelp = new JMenuItem("Need help?");
		helpMenuHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.informationDialog("Help", "Please check our user manuals for more help.");
			}
		});
		helpMenu.add(helpMenuHelp);
		
		JMenuItem helpMenuAbout = new JMenuItem("About");
		helpMenuAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutDialog ab = new AboutDialog();
				ab.setVisible(true);
			}
		});
		helpMenu.add(helpMenuAbout);
	}
	// Greeting dialog
	private void showGreetingDialog() {
		if(!greetingShow) {
			GreetingDialog greetingDialog = new GreetingDialog();
			greetingDialog.setVisible(true);
			greetingDialog.requestFocus();
		}
	}
	/*
	 * Configuration input/output
	 */
		
	// Load configuration from file to app
	private void loadConfiguration() {
		Configuration config = Configuration.obtainConfig();
		if(!config.equals(null)) {
			setIp(config.getIp());
			setPort(config.getPort());
			setUsername(config.getUsername());
			setPassword(config.getPassword());
			setPath(config.getPath());
			setCSVFirst(config.isFirstCSV());
			setCSVSecond(config.isSecondCSV());
			setCSVThird(config.isThirdCSV());
			setPDFFirst(config.isFirstPDF());
			setPDFSecond(config.isSecondPDF());
			setPDFThird(config.isThirdPDF());
			setHTMLFirst(config.isFirstHTML());
			setHTMLSecond(config.isSecondHTML());
			setHTMLThird(config.isThirdHTML());
			setConfigurationChecked(config.isConfigurationChecked());
			setGreetingShow(config.isGreetingDisplayed());
		}
	}
	private void loadSavedConfiguration() {
		xmlManager = new XmlManager();
		configurationList.removeAllItems();
		ArrayList<SavedConfiguration> list = xmlManager.getConfigurationList();
		for(SavedConfiguration item: list) {
			configurationList.addItem(item);
		}
	}
	// Refresh configuration instance data with input data
	private void refreshConfiguration() {
		configuration.setIp(getIp());
		configuration.setPort(getPort());
		configuration.setUsername(getUsername());
		configuration.setPassword(getPassword());
		configuration.setPath(getPath());
		configuration.setFirstCSV(isCSVFirst());
		configuration.setSecondCSV(isCSVSecond());
		configuration.setThirdCSV(isCSVThird());
		configuration.setFirstPDF(isPDFFirst());
		configuration.setSecondPDF(isPDFSecond());
		configuration.setThirdPDF(isPDFThird());
		configuration.setFirstHTML(isHTMLFirst());
		configuration.setSecondHTML(isHTMLSecond());
		configuration.setThirdHTML(isHTMLThird());
		configuration.setConfigurationChecked(isConfigurationChecked());
		configuration.setGreetingDisplayed(isGreetingShow());
	}
	private void loadSelectedConfiguration() {
		SavedConfiguration cfg = (SavedConfiguration)configurationList.getSelectedItem();
		if(cfg != null) {
			setIp(cfg.getIp());
			setPort(cfg.getPort());
			setUsername(cfg.getUsername());
			setPassword(cfg.getPassword());
			setPath(cfg.getPath());
		}
	}
	// Saves configuration into file
	private void saveAndTestConfiguration() {
		refreshConfiguration();
		// Check if connection is correct
		if(DBServiceUtil.testConnection(configuration.getIp(), Integer.valueOf(configuration.getPort()), configuration.getUsername(), configuration.getPassword())) {
			Util.informationDialog("Testing connection", "Connection successful.");
			Configuration.writeConfig(configuration);
		} else {
			Util.errorDialog("Testing connection", "Connection error, check configuration. Configuration not saved");
		}
	}
	// Saves configuration into file
	private void saveConfiguration() {
		refreshConfiguration();
		Configuration.writeConfig(configuration);
	}
	
	// If configuration isn't checked it checks it and updates config file
	private void configurationEntered() {
		if(!isConfigurationChecked()) {
			setConfigurationChecked(true);
			lblCheckConfiguration.setVisible(!isConfigurationChecked());
			saveAndTestConfiguration();
		}
	}
	// Manages all the conditionally displayed components - this must be executed after loading them all
	private void conditionallyDisplayedComponents() {
		lblCheckConfiguration.setVisible(!isConfigurationChecked());
	}

	/*
	 * Loads FirstReportPanel's employee combobox list
	 */
	private void loadFirstReportComboBox() {
		ArrayList<Department> depts  = DBServiceUtil.getDepartmentList();
		for(Department dept: depts) {
			comboBoxFirstReportDepartments.addItem(dept);
		}
	}
	/*
	 * Loads SeconReportPanel's employee combobox list
	 */
	private void loadSecondReportComboBox() {
		ArrayList<Employee> emp  = DBServiceUtil.getEmployeeList();
		for(Employee empl: emp) {
			comboBoxSecondReportEmployees.addItem(empl);
		}
	}
	
	/*
	 * Util
	 */
	
	// Switch between panels
	private void switchToPanel(Panels panel) {
		CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
        cardLayout.show(contentPanel, panel.toString());
	}
	/*
	 * Returns a dd/mm/yy format date string
	 */
	private String getDateStrFromPicker(DatePicker picker) {
		return picker.getModel().getDay() + "/" + picker.getModel().getMonth() + "/" + picker.getModel().getYear();
	}
	/*
	 * Returns a ddmmyyyy format date string
	 * 		Adds a zero to the number if it's one digit
	 */
	private String getDateStrFromPickerRaw(DatePicker picker) {
		return String.format("%02d", picker.getModel().getDay()) + String.format("%02d", picker.getModel().getMonth()) + picker.getModel().getYear();
	}
	
	/*
	 * 	Checks if dates are equal or from date is greater than to date
	 *		 Returns true if it is correct and false if there is a problem
	 */
	private boolean checkDateInput() {
		boolean testPassed = false;
		try {
			Date dateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(getDateStrFromPicker(dateFromPicker));
			Date dateTo = new SimpleDateFormat("dd/MM/yyyy").parse(getDateStrFromPicker(dateToPicker));
			int dateCompare = dateFrom.compareTo(dateTo);
			if(dateFrom.equals(dateTo)){
				// BothDatesEqual
				Util.errorDialog("Date error", "Given dates cannot be equal.");
			} else if(dateFrom.after(dateTo)){
				// Date from cant be bigger than date to
				Util.errorDialog("Date error", "Given from date cannot greater than date to.");
			} else {
				testPassed = true;
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return testPassed;
	}
	/*
	 * Checks given boolean values. Used for checking if output formats are selected and displaying an error message if not
	 * Returns true if there is no error
	 */
	private boolean checkFormatSelected(boolean isPDF, boolean isCSV, boolean isHTML) {
		if(!isPDF && !isCSV && !isHTML) {
			Util.errorDialog("Format error", "You must select atleast one output format");
			return false;
		} else {
			return true;
		}
	}
	/*
	 * Reports
	 * 		Checks if there was atleast one ouput format selected and gets name
	 */
	
	private void generateReport(Panels panel) {
		if(panel == panel.FIRST_REPORT_PANEL) {
			if(checkFormatSelected(isPDFFirstReport.isSelected(), isPDFFirstReport.isSelected(), isPDFFirstReport.isSelected())) {
				generateFirstReport(Util.getFileName(), (Department) comboBoxFirstReportDepartments.getSelectedItem());
				Util.informationDialog("Success", "Report generated successfully.");
			}
		} else if(panel == panel.SECOND_REPORT_PANEL) {
			if(checkFormatSelected(isPDFSecondReport.isSelected(), isPDFSecondReport.isSelected(), isPDFSecondReport.isSelected())) {
				generateSecondReport(Util.getFileName(), (Employee) comboBoxSecondReportEmployees.getSelectedItem());
				Util.informationDialog("Success", "Report generated successfully.");
			}
		} else if(panel == panel.THIRD_REPORT_PANEL) {
			if(dateFrom != null && dateTo != null) {
				// Check if dates are OK
				if(checkDateInput()) {
					// Check if output format is selected
					if(checkFormatSelected(isPDFThirdReport.isSelected(), isCSVThirdReport.isSelected(), isHTMLThirdReport.isSelected())) {
						generateThirdReport(Util.getFileName(), dateFrom, dateTo, getDateStrFromPicker(dateFromPicker),  getDateStrFromPicker(dateToPicker));
						Util.informationDialog("Success", "Report generated successfully.");
					}
				}					
			} else {
				Util.errorDialog("Parameters error", "You need to specify both dates");
			}
		}
		
		
	}
	
	/*
	 * Generates first report with given department
	 */
	private void generateFirstReport(String name, Department department) {
		try {			
			JasperUtil.generateFirstReport(department.getDeptNo(), name, configuration);
				
		} catch (JRException e) {
			Util.errorDialog("JasperReports error", e.getMessage());
		}
	}
	/*
	 * Generates second report with given employee	
	 */
	private void generateSecondReport(String name, Employee employee) {
		try {
			JasperUtil.generateSecondReport(employee.getEmpNo(), name, configuration);
			
		} catch (JRException e) {
			Util.errorDialog("JasperReports error", e.getMessage());
		}
	}
	/*
	 * Generates third report with given dates	
	 */
	private void generateThirdReport(String name, String dateFrom, String dateTo, String dateFromShow, String dateToShow) {
		try {
			JasperUtil.generateThirdReport(dateFrom, dateTo, dateFromShow, dateToShow, name, configuration);
			Util.informationDialog("Success", "Report generated successfully.");
			
		} catch (JRException e) {
			Util.errorDialog("JasperReports error", e.getMessage());
		}
	}

	/*
	 * Getters and setters
	 */
	private void setIp(String value) {
		ipField.setText(value);
		configuration.setIp(value);
	}
	private String getIp() {
		return ipField.getText();
	}
	private void setPort(String value) {
		portField.setText(value);
		configuration.setPort(value);
	}
	private String getPort() {
		return portField.getText();
	}
	private void setUsername(String value) {
		usernameField.setText(value);
		configuration.setUsername(value);
	}
	private String getUsername() {
		return usernameField.getText();
	}
	private void setPassword(String value) {
		passwordField.setText(value);
		configuration.setPassword(value);
	}
	private String getPassword() {
		return String.valueOf(passwordField.getPassword());
	}
	private void setPath(String value) {
		pathField.setText(value);
		configuration.setPath(value);
	}
	private String getPath() {
		return pathField.getText();
	}
	private boolean isCSVFirst() {
		return isCSVFirstReport.isSelected();
	}
	private void setCSVFirst(boolean value) {
		isCSVFirstReport.setSelected(value);
		configuration.setFirstCSV(value);
	}
	private boolean isCSVSecond() {
		return isCSVSecondReport.isSelected();
	}
	private void setCSVSecond(boolean value) {
		isCSVSecondReport.setSelected(value);
		configuration.setSecondCSV(value);
	}
	private boolean isCSVThird() {
		return isCSVThirdReport.isSelected();
	}
	private void setCSVThird(boolean value) {
		isCSVThirdReport.setSelected(value);
		configuration.setThirdCSV(value);
	}
	private boolean isPDFFirst() {
		return isPDFFirstReport.isSelected();
	}
	private void setPDFFirst(boolean value) {
		isPDFFirstReport.setSelected(value);
		configuration.setFirstPDF(value);
	}
	private boolean isPDFSecond() {
		return isPDFSecondReport.isSelected();
	}
	private void setPDFSecond(boolean value) {
		isPDFSecondReport.setSelected(value);
		configuration.setSecondPDF(value);
	}
	private boolean isPDFThird() {
		return isPDFThirdReport.isSelected();
	}
	private void setPDFThird(boolean value) {
		isPDFThirdReport.setSelected(value);
		configuration.setThirdPDF(value);
	}
	private boolean isHTMLFirst() {
		return isHTMLFirstReport.isSelected();
	}
	private void setHTMLFirst(boolean value) {
		isHTMLFirstReport.setSelected(value);;
		configuration.setFirstHTML(value);
	}
	private boolean isHTMLSecond() {
		return isHTMLSecondReport.isSelected();
	}
	private void setHTMLSecond(boolean value) {
		isHTMLSecondReport.setSelected(value);;
		configuration.setSecondHTML(value);
	}
	private boolean isHTMLThird() {
		return isHTMLThirdReport.isSelected();
	}
	private void setHTMLThird(boolean value) {
		isHTMLThirdReport.setSelected(value);;
		configuration.setThirdHTML(value);
	}
	public boolean isConfigurationChecked() {
		return configurationChecked;
	}
	public void setConfigurationChecked(boolean value) {
		this.configurationChecked = value;
		configuration.setConfigurationChecked(value);
	}
	public boolean isGreetingShow() {
		return greetingShow;
	}
	public void setGreetingShow(boolean value) {
		this.greetingShow = value;
		configuration.setGreetingDisplayed(value);
	}
}
