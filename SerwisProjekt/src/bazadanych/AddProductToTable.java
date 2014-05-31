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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import obsluga.Products;
import obsluga.UserRole;
import obsluga.Users;

public class AddProductToTable extends AbstractAction {
	private Products products;
	private String name;
	private double price;
	private JFrame frame;
	private JTable table;
	
	public AddProductToTable(Products products, JFrame frame, JTable table) {
		this.products = products;
		this.frame = frame;
		this.table = table;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {

        JTextField field1 = new JTextField("");
        JTextField field2 = new JTextField("");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nazwa produktu:"));
        panel.add(field1);
        panel.add(new JLabel("Cena:"));
        panel.add(field2);
        int result = JOptionPane.showConfirmDialog(frame, panel, "Dodawanie produktu",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION && !field1.getText().equals("") && !field2.getText().equals("")) {
        	name = field1.getText();
        	try {
        		price = Double.parseDouble(field2.getText());
        	} catch(NumberFormatException e) {
        		JOptionPane.showMessageDialog(frame, "Price is in wrong format");
        	}
			try {
				PreparedStatement preparedStatement = products.getConnection().prepareStatement("insert into products values(NULL,?,?,?)");
				preparedStatement.setString(1,name);
				preparedStatement.setInt(2,0);
				preparedStatement.setFloat(3,(float)price);
		
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
	
			

}
