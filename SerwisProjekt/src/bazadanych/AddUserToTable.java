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

import obsluga.UserRole;
import obsluga.Users;

public class AddUserToTable extends AbstractAction {
	private Users users;
	private String login;
	private String pwd;
	private UserRole role;
	private JFrame frame;
	private JTable table;
	
	public AddUserToTable(Users users, JFrame frame, JTable table) {
		this.users = users;
		this.frame = frame;
		this.table = table;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		String[] values = new String[] {"ADMIN","CLERK", "SERVICE"};

        JComboBox combo = new JComboBox(values);
        JTextField field1 = new JTextField("");
        JTextField field2 = new JTextField("");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(combo);
        panel.add(new JLabel("Login:"));
        panel.add(field1);
        panel.add(new JLabel("Password:"));
        panel.add(field2);
        int result = JOptionPane.showConfirmDialog(frame, panel, "User create window",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION && !field1.getText().equals("") && !field2.getText().equals("")) {
        	login = field1.getText();
        	pwd = field2.getText();
        	
        	switch((String)combo.getSelectedItem()) {
        	case "ADMIN": role = UserRole.ADMIN; break;
        	case "CLERK": role = UserRole.CLERK; break;
        	case "SERVICE": role = UserRole.SERVICE; break;
        	default: {
        		throw new RuntimeException("Finding role failed.");
        	}
        	}
        	
        	
			try {
				PreparedStatement preparedStatement = users.getConnection().prepareStatement("insert into users values(NULL,?,?,?)");
				preparedStatement.setString(1,login);
				preparedStatement.setString(2,pwd);
				preparedStatement.setString(3,role.toString());
		
				preparedStatement.execute();
				DefaultTableModel model = (DefaultTableModel)(table.getModel());
				
				String query = "select * from users where login = '"+login+"'";
				
				PreparedStatement ps = users.getConnection().prepareStatement(query);
				
				ResultSet results = ps.executeQuery();
				
				int newUserId = results.getInt("user_id");
				
					
					
					
				
				model.addRow(new Object[] {newUserId,login,pwd,role.toString() });
				JOptionPane.showMessageDialog(frame, "User "+login+" added.");
				//model.fireTableDataChanged();
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(frame, "Couldn't add user to database.");
				
			}
		}
        else if (result == JOptionPane.CANCEL_OPTION) {
        }
        else {
        	JOptionPane.showMessageDialog(frame, "Couldn't create user, maybe one of the fields is empty");
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
