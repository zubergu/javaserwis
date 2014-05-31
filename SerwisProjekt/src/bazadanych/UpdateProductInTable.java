package bazadanych;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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
        final JComboBox combo = new JComboBox(names);
		
		final JTextField field1 = new JTextField("");
        final JTextField field2 = new JTextField("");
        final JTextField field3 = new JTextField("");
        
        
        JPanel panel = new JPanel(new GridLayout(0, 1));
        
        panel.add(combo);
        panel.add(new JLabel("Nowa nazwa produktu:"));
        panel.add(field1);
        panel.add(new JLabel("Nowa cena:"));
        panel.add(field2);
        panel.add(new JLabel("Nowa ilosc:"));
        panel.add(field3);
        
        /*
         * When selecting from items in combo box insert current values into fields.
         */
        combo.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * Check current values in db and place them in fields.
				 */
				try {
				String productName = combo.getSelectedItem().toString();
				String query = "select * from products where name = '"+productName+"'";
				
				PreparedStatement ps = products.getConnection().prepareStatement(query);
				
				ResultSet results = ps.executeQuery();
				
				field1.setText(results.getString("name"));
				
				field2.setText(""+results.getFloat("price"));
				
				field3.setText(""+results.getInt("quantity"));
				
				} catch (SQLException e) {
					System.out.println("Failed finding combo selection in db");
				}
				
				
			}
        	
        });
        
        int result = JOptionPane.showConfirmDialog(frame, panel, "Aktualizacja produktu",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION && !field1.getText().equals("") && !field2.getText().equals("") && !field3.getText().equals("")){
        	newName = field1.getText();
        	try {
        		newQuantity = Integer.parseInt(field3.getText());
        		newPrice = Double.parseDouble(field2.getText());
        	} catch(NumberFormatException e) {
        		JOptionPane.showMessageDialog(frame, "Price or quantity is in wrong format");
        	}
 
			try {
				PreparedStatement preparedStatement = products.getConnection().prepareStatement("update products set name = ? , quantity = ?, price = ? where name = ?");
				preparedStatement.setString(1,newName);
				preparedStatement.setInt(2,newQuantity);
				preparedStatement.setFloat(3,(float)newPrice);
				preparedStatement.setString(4, (String)combo.getSelectedItem());
		
				preparedStatement.execute();
				DefaultTableModel model = (DefaultTableModel)(table.getModel());
				
				int rowToChange = Arrays.asList(names).indexOf((String)combo.getSelectedItem());
				model.setValueAt(newName, rowToChange, 1);
				model.setValueAt(newQuantity, rowToChange, 2);
				model.setValueAt(newPrice,rowToChange,3);
				
				JOptionPane.showMessageDialog(frame, "Product "+newName+" updated.");
				
				
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
