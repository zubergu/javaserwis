package akcje;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JTable;

import obsluga.Products;
import obsluga.UserRole;
import obsluga.Users;

public class ManageProducts extends AbstractAction {
	private Products products;
	private JFrame frame;
	private JTable table;
	
	public ManageProducts(Products products,JFrame frame,JTable table) {
		this.products = products;
		this.frame = frame;
		this.table = table;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		
	}

}
