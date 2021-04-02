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
			path = pathSelector.getCurrentDirectory().getAbsolutePath();
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
		if(ConverterUtil.containsIllegals(name)) {
			fileNameErrorDialog(name);
		} else {
			return name;
		}
		return null;
	}
}
