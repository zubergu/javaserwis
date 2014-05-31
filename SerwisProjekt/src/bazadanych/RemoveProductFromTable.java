package bazadanych;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import obsluga.Products;
import obsluga.UserRole;
import obsluga.Users;

public class RemoveProductFromTable extends AbstractAction {
	private Products products;
	private JFrame frame;
	private JTable table;
	private String productName;
	
	public RemoveProductFromTable(Products products, JFrame frame, JTable table) {
		this.products = products;
		this.frame = frame;
		this.table = table;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		String[] names = getAllProductNames();
        JComboBox combo = new JComboBox(names);
        JTextField field1 = new JTextField("");
        JTextField field2 = new JTextField("");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(combo);
        panel.add(new JLabel("Login:"));
        panel.add(field1);
        panel.add(new JLabel("Role:"));
        panel.add(field2);
        int result = JOptionPane.showConfirmDialog(frame, panel, "Product deletion window",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
        		productName = (String)combo.getSelectedItem();
        		
			try {
				PreparedStatement preparedStatement = products.getConnection().prepareStatement("delete from products where name = ?");
				preparedStatement.setString(1,productName);
				
				preparedStatement.executeUpdate();
				DefaultTableModel model = (DefaultTableModel)(table.getModel());
				int row = -1;
				int count = 0;
				for(String element:names) {
					
					if(productName.equals(element)) {
						row = count;
						break;
					}
					count++;
				}
				if (row == -1) {
					throw new RuntimeException("Something went wrong when finding login to remove.");
				}
				model.removeRow(row);
				JOptionPane.showMessageDialog(frame, "Product "+productName+" removed.");
				//model.fireTableDataChanged();
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(frame, "Couldn't remove product.");
				
			}
		}
  
	}
	
	private String[] getAllProductNames() {
		ArrayList<String> names = new ArrayList<String>();
		
		for(int i = 0; i<table.getRowCount();i++) {
			names.add((String)table.getValueAt(i, 1));
		}
		
		return names.toArray(new String[0]);
	}
		

}
