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

public class ListOrdersForUser extends AbstractAction {
	private Orders orders;
	private JFrame frame;
	private JTable table;
	
	public ListOrdersForUser(Orders orders,JFrame frame, JTable table) {
		this.orders = orders;
		this.frame = frame;
		this.table = table;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		List<Integer> orderList = new ArrayList<Integer>();
		String userName = "";
		
        JTextField field1 = new JTextField("");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Login or user name:"));
        panel.add(field1);
 
        int result = JOptionPane.showConfirmDialog(frame, panel, "User orders window",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION && !field1.getText().equals("")) {
        	
        	
			try {
				userName = field1.getText();
				
				String query = "select order_id from orders where order_handler = '"+userName+"'";
				
				PreparedStatement ps = orders.getConnection().prepareStatement(query);
				ResultSet results = ps.executeQuery();
				
				while(results.next()) {
					orderList.add(results.getInt("order_id"));
				}
				JList list = new JList(orderList.toArray());
				JPanel panel2 = new JPanel(new GridLayout(0, 1));
		        panel2.add(new JLabel("Results:"));
		        panel2.add(list);
		        
		        JOptionPane.showMessageDialog(frame, panel2,"Results window", JOptionPane.PLAIN_MESSAGE);
				
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
