package bazadanych;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import obsluga.Orders;
import obsluga.UserRole;
import obsluga.Users;

public class SetOrderStatusToFinished extends AbstractAction {
	private Orders orders;
	private JFrame frame;
	private JTable table;
	
	public SetOrderStatusToFinished(Orders orders,JFrame frame, JTable table) {
		this.orders = orders;
		this.frame = frame;
		this.table = table;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int orderId = -1;
		
        JTextField field1 = new JTextField("");
        
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Finished order id:"));
        panel.add(field1);
 
        int result = JOptionPane.showConfirmDialog(frame, panel, "Order status window",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION && !field1.getText().equals("")) {
        	
        	
			try {
				
				orderId = Integer.parseInt(field1.getText());
				
				PreparedStatement preparedStatement = orders.getConnection().prepareStatement("update orders set order_status = ? where order_id = ?");
				preparedStatement.setString(1,"FINISHED");
				preparedStatement.setInt(2,orderId);
		
				preparedStatement.execute();
				DefaultTableModel model = (DefaultTableModel)(table.getModel());
				
				int rowToChange = orderId-1;
				model.setValueAt("FINISHED", rowToChange, 4);
				
				JOptionPane.showMessageDialog(frame, "Order "+orderId+" status updated.");
				
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
