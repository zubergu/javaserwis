package bazadanych;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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

import obsluga.Orders;
import obsluga.UserRole;
import obsluga.Users;

public class CheckOrderStatus extends AbstractAction {
	private Orders orders;
	private JFrame frame;
	private JTable table;
	
	public CheckOrderStatus(Orders orders,JFrame frame, JTable table) {
		this.orders = orders;
		this.frame = frame;
		this.table = table;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int orderId = -1;
		
        JTextField field1 = new JTextField("");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Order id:"));
        panel.add(field1);
 
        int result = JOptionPane.showConfirmDialog(frame, panel, "Order state window",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION && !field1.getText().equals("")) {
        	
        	
			try {
				orderId = Integer.parseInt(field1.getText());
				
				String query = "select order_status,order_handler from orders where order_id ="+orderId;
				
				PreparedStatement ps = orders.getConnection().prepareStatement(query);
				
				ResultSet results = ps.executeQuery();
				
				String state = results.getString("order_status");
				String handler = results.getString("order_handler");	
					
					
	
				JOptionPane.showMessageDialog(frame, "Order: "+orderId+"\n"+
													 "state: "+state+"\n"+
													 "handler: "+handler );
				
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(frame, "This is not correct order id.");
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(frame, "Couldn't find this id.");
				
			}
		}
        else if (result == JOptionPane.CANCEL_OPTION) {
        }
	}

}
