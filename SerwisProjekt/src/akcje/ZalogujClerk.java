package akcje;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import obsluga.Users;

public class ZalogujClerk extends AbstractAction {
	private Users users;
	private JFrame frame;
	
	public ZalogujClerk(Users users, JFrame frame) {
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
			users.loginClerk();
		}
	}

	

}
