package bazadanych;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import obsluga.Orders;
import obsluga.Products;
import obsluga.UserRole;
import obsluga.Users;

public class AddOrderToTable extends AbstractAction {
	private Orders orders;
	private String name;
	private double price;
	private JFrame frame;
	private JTable table;
	
	public AddOrderToTable(Orders orders, JFrame frame, JTable table) {
		this.orders = orders;
		this.frame = frame;
		this.table = table;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {

		String[] values = new String[] {"NAPRAWA","WYMIANA", "INNE"};
		
		JComboBox combo = new JComboBox(values);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextArea descriptionArea = new JTextArea("Opis dodatkowy");
        JTextField field1 = new JTextField("");
       
       
        JScrollPane scrollArea = new JScrollPane (descriptionArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        JCheckBox displayBox = new JCheckBox("display");
        JCheckBox keyboardBox = new JCheckBox("keyboard");
        JCheckBox mouseBox = new JCheckBox("mouse");
        JCheckBox osBox = new JCheckBox("os");
        JCheckBox processorBox = new JCheckBox("processor");
        JCheckBox mainboardBox = new JCheckBox("mainboard");
        JCheckBox speakersBox = new JCheckBox("speakers");
        JCheckBox graphicsBox = new JCheckBox("graphics");
        JCheckBox GPSBox = new JCheckBox("GPS");
        JCheckBox bluetoothBox = new JCheckBox("bluetooth");
        JCheckBox hddBox = new JCheckBox("hdd");
        JCheckBox dvdBox = new JCheckBox("dvd");
        JCheckBox ramBox = new JCheckBox("ram");
        JCheckBox otherBox = new JCheckBox("other");
        
        panel.add(combo);
        
        panel.add(new JLabel("Klient:"));
        panel.add(field1);
        
        panel.add(displayBox);
        panel.add(keyboardBox);
        panel.add(mouseBox);
        panel.add(osBox);
        panel.add(processorBox);
        panel.add(mainboardBox);
        panel.add(speakersBox);
        panel.add(graphicsBox);
        panel.add(GPSBox);
        panel.add(bluetoothBox);
        panel.add(hddBox);
        panel.add(dvdBox);
        panel.add(ramBox);
        panel.add(otherBox);
        panel.add(scrollArea);
        
        int result = JOptionPane.showConfirmDialog(frame, panel, "Dodawanie zlecenia",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
     
        if (result == JOptionPane.OK_OPTION && !field1.getText().equals("")){
        	try {
        		int newOrderId=-1;
        		
        		String clientName = field1.getText();
        		String additionalDescription = descriptionArea.getText();
        		
        		
        		
				PreparedStatement preparedStatement = orders.getConnection().prepareStatement("insert into orders values(NULL,?,?,?,?,NULL,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				preparedStatement.setString(1,clientName);
				preparedStatement.setString(2,(String)combo.getSelectedItem());
				preparedStatement.setString(3, additionalDescription);
				preparedStatement.setString(4, "NOT STARTED");
				preparedStatement.setInt(5,displayBox.isSelected() ? 1:0);
				preparedStatement.setInt(6,keyboardBox.isSelected() ? 1:0);
        		preparedStatement.setInt(7,mouseBox.isSelected() ? 1:0);
        		preparedStatement.setInt(8,osBox.isSelected() ? 1:0);
        		preparedStatement.setInt(9,processorBox.isSelected() ? 1:0);
        		preparedStatement.setInt(10,mainboardBox.isSelected() ? 1:0);
        		preparedStatement.setInt(11,speakersBox.isSelected() ? 1:0);
        		preparedStatement.setInt(12,graphicsBox.isSelected() ? 1:0);
        		preparedStatement.setInt(13,GPSBox.isSelected() ? 1:0);
        		preparedStatement.setInt(14,bluetoothBox.isSelected() ? 1:0);
        		preparedStatement.setInt(15,hddBox.isSelected() ? 1:0);
        		preparedStatement.setInt(16,dvdBox.isSelected() ? 1:0);
        		preparedStatement.setInt(17,ramBox.isSelected() ? 1:0);
        		preparedStatement.setInt(18,otherBox.isSelected() ? 1:0);
		
				preparedStatement.execute();
				DefaultTableModel model = (DefaultTableModel)(table.getModel());
				
				String query = "SELECT MAX(order_id) FROM orders";
				
				Statement statement = orders.getConnection().createStatement();
				statement.execute(query);
				ResultSet results = statement.getResultSet();
				
				if (results.next()) {
					newOrderId = results.getInt(1);
				}
				
					
					
					
				
				model.addRow(new Object[] {newOrderId,clientName,(String)combo.getSelectedItem(),additionalDescription,"NOT STARTED"," ", displayBox.isSelected() ? 1:0,
						(keyboardBox.isSelected() ? 1:0),
        		(mouseBox.isSelected() ? 1:0),
        		(osBox.isSelected() ? 1:0),
        		(processorBox.isSelected() ? 1:0),
        		(mainboardBox.isSelected() ? 1:0),
        		(speakersBox.isSelected() ? 1:0),
        		(graphicsBox.isSelected() ? 1:0),
        		(GPSBox.isSelected() ? 1:0),
        		(bluetoothBox.isSelected() ? 1:0),
        		(hddBox.isSelected() ? 1:0),
        		(dvdBox.isSelected() ? 1:0),
        		(ramBox.isSelected() ? 1:0),
        		(otherBox.isSelected() ? 1:0),
				});
				JOptionPane.showMessageDialog(frame, "Order added.");
				
        	
        	} catch (SQLException e) {
				JOptionPane.showMessageDialog(frame, "Couldn't add order to database.");
				
			}
		}
        else if (result == JOptionPane.CANCEL_OPTION) {
        	
        }
        else {
        	JOptionPane.showMessageDialog(frame, "Couldn't add order to database,probably client name is empty.");
        }
	}
	
			

}
