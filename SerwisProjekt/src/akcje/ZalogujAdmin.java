package akcje;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import obsluga.Users;

public class ZalogujAdmin extends AbstractAction {
	private Users users;
	private JFrame frame;
	
	public ZalogujAdmin(Users users, JFrame frame) {
		this.users = users;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(users.getAlreadyLogged()) {
			JOptionPane.showMessageDialog(frame,
			    "Wyloguj sie przed ponownym logowaniem.",
			    "Blad logowania.",
			    JOptionPane.ERROR_MESSAGE);
		}
		else {
			users.loginAdmin();
		}
	}

	

}
