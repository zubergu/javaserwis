package akcje;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

import obsluga.Users;

public class PrzelaczNaPanelSerwis extends AbstractAction {

	private Users users;
	private CardLayout cardLayout;
	private JPanel panel;
	
	public PrzelaczNaPanelSerwis(Users users, CardLayout cardLayout, JPanel panel) {
		this.users = users;
		this.cardLayout = cardLayout;
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(users.isService()) {
			cardLayout.show(panel,"panelSerwisZ");
		}
		else {
			cardLayout.show(panel, "panelSerwisNz");
		}
		
	}

}
