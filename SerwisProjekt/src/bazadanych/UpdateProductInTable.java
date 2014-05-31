package bazadanych;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javax.swing.table.DefaultTableModel;

import obsluga.Products;

public class UpdateProductInTable extends AbstractAction {
	private Products products;
	private JFrame frame;
	private JTable table;
	private String newName;
	private double newPrice;
	private int newQuantity;
	
	public UpdateProductInTable(Products products,JFrame frame, JTable table) {
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
        JTextField field3 = new JTextField("");
        
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nowa nazwa produktu:"));
        panel.add(field1);
        panel.add(new JLabel("Nowa cena:"));
        panel.add(field2);
        panel.add(new JLabel("Nowa ilosc:"));
        panel.add(field3);
        int result = JOptionPane.showConfirmDialog(frame, panel, "Aktualizacja produktu",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION && !field1.getText().equals("") && !field2.getText().equals("") && !field3.getText().equals("")){
        	newName = field1.getText();
        	try {
        		newPrice = Double.parseDouble(field2.getText());
        		newQuantity = Integer.parseInt(field3.getText());
        	} catch(NumberFormatException e) {
        		JOptionPane.showMessageDialog(frame, "Price or quantity is in wrong format");
        	}
 
			try {
				PreparedStatement preparedStatement = products.getConnection().prepareStatement("update products set name = '?' , quantity = ?, price = ? where name = '?'");
				preparedStatement.setString(1,newName);
				preparedStatement.setInt(2,newQuantity);
				preparedStatement.setFloat(3,(float)newPrice);
				preparedStatement.setString(4, (String)combo.getSelectedItem());
		
				preparedStatement.execute();
				DefaultTableModel model = (DefaultTableModel)(table.getModel());
				
				String query = "select * from products where name = '"+name+"'";
				
				PreparedStatement ps = products.getConnection().prepareStatement(query);
				
				ResultSet results = ps.executeQuery();
				
				int newProductId = results.getInt("product_id");
				
					
					
					
				
				model.addRow(new Object[] {newProductId,name,0,price });
				JOptionPane.showMessageDialog(frame, "Product "+name+" added.");
				//model.fireTableDataChanged();
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(frame, "Couldn't add product to database.");
				
			}
		}
        else if (result == JOptionPane.CANCEL_OPTION) {
        }
        else {
        	JOptionPane.showMessageDialog(frame, "Couldn't add product, maybe one of the fields is empty");
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
