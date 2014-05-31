package akcje;

import java.awt.CardLayout;
import java.awt.event.*;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

import obsluga.Users;

public class PrzelaczNaPanelAdmin extends AbstractAction {
	
	private Users users;
	private CardLayout cardLayout;
	private JPanel panel;
	
	public PrzelaczNaPanelAdmin(Users users, CardLayout cardLayout,JPanel panel) {
		this.users = users;
		this.cardLayout = cardLayout;
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(users.isAdmin()) {
			cardLayout.show(panel, "panelAdminZ");
		}
		else {
			cardLayout.show(panel, "panelAdminNz");
		}
		
	}

}
