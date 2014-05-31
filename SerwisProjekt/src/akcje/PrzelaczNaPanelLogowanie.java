package akcje;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

public class PrzelaczNaPanelLogowanie extends AbstractAction{
	private CardLayout cardLayout;
	private JPanel panel;
	
	public PrzelaczNaPanelLogowanie(CardLayout cardLayout, JPanel panel) {
		this.cardLayout = cardLayout;
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		cardLayout.show(panel, "panelLogowania");
		
	}

}
