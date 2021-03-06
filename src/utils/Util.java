package utils;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Util {
	public static String openPathSelector() {
		String path = "";
		JFileChooser pathSelector = new JFileChooser();
		pathSelector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		pathSelector.setCurrentDirectory(new File("."));
		pathSelector.setDialogTitle("Select path to export files");
		if(pathSelector.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			path = pathSelector.getSelectedFile().getAbsolutePath();
		}
		return path;
	}
	/*
	 * Creates a new error dialog with given title and message
	 */
	public static void errorDialog(String title, String message) {		
		JOptionPane optionPane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);    
		JDialog dialog = optionPane.createDialog(title);
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}
	public static void warningDialog(String title, String message) {		
		JOptionPane optionPane = new JOptionPane(message, JOptionPane.WARNING_MESSAGE);    
		JDialog dialog = optionPane.createDialog(title);
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}
	/*
	 * Creates a new information dialog with given title and message
	 */
	public static void informationDialog(String title, String message) {		
		JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);    
		JDialog dialog = optionPane.createDialog(title);
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}
	/*
	 * Creates a file name error dialog with given name
	 */
	public static void fileNameErrorDialog(String name) {
		errorDialog("File name error", "The given filename '" + name + "' cannot contain \\ / : * ? \" < > | ");
	}
	public static String getFileName() {
		String name = JOptionPane.showInputDialog("Please give a name for the generated file/s");
		if(name != null) {
			if(ConverterUtil.containsIllegals(name)) {
				fileNameErrorDialog(name);
			} else {
				return name;
			}
		}
		
		return null;
	}
	/*
	 * Checks given boolean values. Used for checking if output formats are selected and displaying an error message if not
	 * Returns true if there is no error
	 */
	public static boolean checkFormatSelected(boolean isPDF, boolean isCSV, boolean isHTML) {
		if(!isPDF && !isCSV && !isHTML) {
			errorDialog("Format error", "You must select atleast one output format");
			return false;
		} else {
			return true;
		}
	}
}
