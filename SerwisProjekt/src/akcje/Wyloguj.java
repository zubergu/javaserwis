package akcje;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import obsluga.Users;

public class Wyloguj extends AbstractAction {
	private Users users;
	private JFrame frame;
	
	public Wyloguj(Users users, JFrame frame)  {
		this.users = users;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(users.getAlreadyLogged()) {
		users.logoutUser();
		JOptionPane.showMessageDialog(frame,
			    "Wylogowanie uzytkownika.",
			    "Sukces.",
			    JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(frame,
				    "Najpierw sie zaloguj.",
				    "",
				    JOptionPane.INFORMATION_MESSAGE);
			
		}
		
	}

}
