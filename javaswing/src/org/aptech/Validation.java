package org.aptech;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Validation {
	public static boolean isEmpty(JTextField txtField, String msg) {
		String txtValue = txtField.getText().trim();
		if(txtValue.length() == 0) {
			JOptionPane.showMessageDialog(null, msg);
			txtField.requestFocus();
			return true;
		}
		return false;
		
	}
	
	public static boolean isEmail(JTextField txtField, String msg) {
		String txtValue = txtField.getText().trim();
		String strPtn = "^[a-zA-Z]\\w{2,}@\\w{2,}(\\.\\w{2,3}){1,2}$";
		if(!txtValue.matches(strPtn)) {
			JOptionPane.showMessageDialog(null, msg);
			txtField.requestFocus();
			return false;
		}		
		return true;
		
	}
	
	public static boolean isAddress(JTextField txtField, String msg) {
		String txtValue = txtField.getText().trim();
		String strPtn = "^[a-zA-Z0-9\\\\s]+$";
		if(!txtValue.matches(strPtn)) {
			JOptionPane.showMessageDialog(null, msg);
			txtField.requestFocus();
			return false;
		}		
		return true;
		
	}
}
