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
		txtrThisIsA.setText("  This is a project build to generate reports from \r\nthe sample Employee DB.\r\n\r\nThis was developed by David Riy\r\n\r\nLorem ipsum dolor sit amet, consectetuer adipiscing \r\nelit, sed diam nonummy nibh euismod tincidunt ut la\r\noreet dolore magna aliquam erat volutpat. Ut wisi e\r\nnim ad minim veniam, quis nostrud exerci tation ullamcor\r\nper suscipit lobortis nisl ut aliquip ex ea commodo cons");
		
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
