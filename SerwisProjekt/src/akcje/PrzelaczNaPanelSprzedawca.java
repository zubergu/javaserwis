package akcje;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

import obsluga.Users;

public class PrzelaczNaPanelSprzedawca extends AbstractAction {
	private Users users;
	private CardLayout cardLayout;
	private JPanel panel;
	
	
	public PrzelaczNaPanelSprzedawca(Users users, CardLayout cardLayout, JPanel panel) {
		this.users = users;
		this.cardLayout = cardLayout;
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(users.isClerk()) {
			cardLayout.show(panel, "panelSprzedawcaZ");
		}
		else {
			cardLayout.show(panel, "panelSprzedawcaNz");
		}
		
	}

}
