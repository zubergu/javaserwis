package bazadanych;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import obsluga.Orders;
import obsluga.UserRole;
import obsluga.Users;

public class DisplayOrderInfo extends AbstractAction {
	private Orders orders;
	private JFrame frame;
	private JTable table;
	
	public DisplayOrderInfo(Orders orders,JFrame frame, JTable table) {
		this.orders = orders;
		this.frame = frame;
		this.table = table;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Integer orderId;
		
        JTextField field1 = new JTextField("");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Order id:"));
        panel.add(field1);
 
        int result = JOptionPane.showConfirmDialog(frame, panel, "Order details window",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION && !field1.getText().equals("")) {
        	
        	
			try {
				orderId = Integer.parseInt(field1.getText());
				
				String query = "select * from orders where order_id = "+orderId;
				
				PreparedStatement ps = orders.getConnection().prepareStatement(query);
				ResultSet results = ps.executeQuery();
				
				if(results.next()) {
					
					
					
					JPanel panel2 = new JPanel(new GridLayout(0, 1));
					panel2.add(new JLabel("Order Id: "+results.getInt("order_id")));
					panel2.add(new JLabel("Customer: "+results.getString("customer")));
					panel2.add(new JLabel("Order type: "+results.getString("order_type")));
					panel2.add(new JLabel("Description: "+results.getString("description")));
					panel2.add(new JLabel("Status: "+results.getString("order_status")));
					panel2.add(new JLabel("Handler: "+results.getString("order_handler")));
					panel2.add(new JLabel("display: "+(results.getInt("display")==1 ? "yes":"no")));
					panel2.add(new JLabel("keyboard: "+(results.getInt("keyboard")==1 ? "yes":"no")));
					panel2.add(new JLabel("mouse: "+(results.getInt("mouse")==1 ? "yes":"no")));
					panel2.add(new JLabel("OS: "+(results.getInt("os")==1 ? "yes":"no")));
					panel2.add(new JLabel("processor: "+(results.getInt("processor")==1 ? "yes":"no")));
					panel2.add(new JLabel("mainboard: "+(results.getInt("mainboard")==1 ? "yes":"no")));
					panel2.add(new JLabel("speakers: "+(results.getInt("speakers")==1 ? "yes":"no")));
					panel2.add(new JLabel("graphics: "+(results.getInt("graphics")==1 ? "yes":"no")));
					panel2.add(new JLabel("GPS: "+(results.getInt("GPS")==1 ? "yes":"no")));
					panel2.add(new JLabel("bluetooth: "+(results.getInt("bluetooth")==1 ? "yes":"no")));
					panel2.add(new JLabel("hdd: "+(results.getInt("hdd")==1 ? "yes":"no")));
					panel2.add(new JLabel("dvd_rom: "+(results.getInt("dvd_rom")==1 ? "yes":"no")));
					panel2.add(new JLabel("ram: "+(results.getInt("ram")==1 ? "yes":"no")));
					panel2.add(new JLabel("other: "+(results.getInt("other")==1 ? "yes":"no")));
					
					
					
		        		        
					JOptionPane.showMessageDialog(frame, panel2,"Results window", JOptionPane.PLAIN_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(frame, "Couldn't find order with this id.");
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(frame, "wrong number somewhere.");
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(frame, "Something went wrong when connecting to database.");
				
			}
		}
        else if (result == JOptionPane.CANCEL_OPTION) {
        }
	}

}
