package akcje;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;

import obsluga.Users;

public class ActionZaloguj extends AbstractAction {
	private Users users;
	private JFrame frame;
	private JTable table;
	private JTextField loginField;
	private JPasswordField pwdField;
	
	public ActionZaloguj (Users users, JFrame frame, JTable table, JTextField loginField, JPasswordField pwdField) {
		this.users = users;
		this.frame = frame;
		this.table = table;
		this.loginField = loginField;
		this.pwdField = pwdField;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(users.getAlreadyLogged()) {
			JOptionPane.showMessageDialog(frame,
			    "Wyloguj sie przed ponownym logowaniem.",
			    "Blad logowania.",
			    JOptionPane.ERROR_MESSAGE);
		}
		else if (loginField.getText().equals("") || pwdField.getText().equals("")){
			JOptionPane.showMessageDialog(frame,
				    "Podaj nazwe uzytkownika i haslo.",
				    "Blad logowania.",
				    JOptionPane.ERROR_MESSAGE);
		}
		else {
			String user= loginField.getText();
			String password = pwdField.getText();
			
			checkUserPasswordAndRole(user, password);
			
			
		}
		
		loginField.setText("");
		pwdField.setText("");
	}
	
	private void checkUserPasswordAndRole(String user, String password) {
		ArrayList<String> logins = new ArrayList<String>();
		ArrayList<String> passwords = new ArrayList<String>();
		ArrayList<String> roles = new ArrayList<String>();
		
		for(int i = 0; i<table.getRowCount();i++) {
			logins.add((String)table.getValueAt(i, 1));
			passwords.add((String)table.getValueAt(i,2));
			roles.add((String)table.getValueAt(i, 3));
		}
		
		if(logins.contains(user)) {
			if(passwords.get(logins.indexOf(user)).equals(password)) {
				JOptionPane.showMessageDialog(frame,
					    "Uzytkownik zalogowany",
					    "Sukces.",
					    JOptionPane.INFORMATION_MESSAGE);
				
				
				switch(roles.get(logins.indexOf(user))) {
					case "ADMIN": users.loginAdmin();break;
					case "CLERK": users.loginClerk(); break;
					case "SERVICE":users.loginService(); break;
					default: {
						System.out.println("Blad przy ustawianiu uprawnien");
					}
				}
			}
			else {
				JOptionPane.showMessageDialog(frame,
					    "Bledne haslo uzytkownika.",
					    "Blad logowania.",
					    JOptionPane.ERROR_MESSAGE);
			}
			
		}
		else {
			JOptionPane.showMessageDialog(frame,
				    "Nie znaleziono uzytkownika.",
				    "Blad logowania.",
				    JOptionPane.ERROR_MESSAGE);
		}
	}

	

}

