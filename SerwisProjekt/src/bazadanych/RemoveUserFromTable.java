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

import obsluga.UserRole;
import obsluga.Users;

public class RemoveUserFromTable extends AbstractAction {
	private Users users;
	private String login;
	private String pwd;
	private UserRole role;
	private JFrame frame;
	private JTable table;
	
	public RemoveUserFromTable(Users users, JFrame frame, JTable table) {
		this.users = users;
		this.frame = frame;
		this.table = table;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		String[] logins = getAllLogins();
        JComboBox combo = new JComboBox(logins);
        JTextField field1 = new JTextField("");
        JTextField field2 = new JTextField("");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(combo);
        panel.add(new JLabel("Login:"));
        panel.add(field1);
        panel.add(new JLabel("Role:"));
        panel.add(field2);
        int result = JOptionPane.showConfirmDialog(frame, panel, "User deletion window",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
        		login = (String)combo.getSelectedItem();
        		
			try {
				PreparedStatement preparedStatement = users.getConnection().prepareStatement("delete from users where login = ?");
				preparedStatement.setString(1,login);
				
				preparedStatement.executeUpdate();
				DefaultTableModel model = (DefaultTableModel)(table.getModel());
				int row = -1;
				int count = 0;
				for(String element:logins) {
					
					if(login.equals(element)) {
						row = count;
						break;
					}
					count++;
				}
				if (row == -1) {
					throw new RuntimeException("Something went wrong when finding login to remove.");
				}
				model.removeRow(row);
				JOptionPane.showMessageDialog(frame, "User "+login+" removed.");
				//model.fireTableDataChanged();
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(frame, "Couldn't remove user.");
				
			}
		}
  
	}
	
	private String[] getAllLogins() {
		ArrayList<String> logins = new ArrayList<String>();
		
		for(int i = 0; i<table.getRowCount();i++) {
			logins.add((String)table.getValueAt(i, 1));
		}
		
		return logins.toArray(new String[0]);
	}
		

}
