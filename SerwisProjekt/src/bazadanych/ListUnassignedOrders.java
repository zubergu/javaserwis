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

public class ListUnassignedOrders extends AbstractAction {
	private Orders orders;
	private JFrame frame;
	private JTable table;
	
	public ListUnassignedOrders(Orders orders,JFrame frame, JTable table) {
		this.orders = orders;
		this.frame = frame;
		this.table = table;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		List<Integer> orderList = new ArrayList<Integer>();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        try {
        
				
				String query = "select order_id from orders where order_handler IS NULL";
				
				PreparedStatement ps = orders.getConnection().prepareStatement(query);
				ResultSet results = ps.executeQuery();
				
				while(results.next()) {
					orderList.add(results.getInt("order_id"));
				}
				JList list = new JList(orderList.toArray());
		        panel.add(new JLabel("Results:"));
		        panel.add(list);
		        
		        JOptionPane.showMessageDialog(frame, panel,"Results window", JOptionPane.PLAIN_MESSAGE);
				
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(frame, "wrong number somewhere.");
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(frame, "Something went wrong when connecting to database.");
				
			}
		}
	}
