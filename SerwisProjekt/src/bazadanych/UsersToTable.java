package bazadanych;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import obsluga.Users;

public class UsersToTable extends AbstractAction {
	private Users users;
	private DefaultTableModel model;
	private Connection connection;
	
	public UsersToTable(Users users, DefaultTableModel model, Connection connection) {
		this.users = users;
		this.model = model;
		this.connection = connection;
	}
	
	static public void populateTableUsersFromDatabase(Users users, DefaultTableModel model, Connection connection) throws SQLException {
		int id;
		String login = "";
		String password = "";
		String role = "";
		
		String query = "select * from users";
		
		PreparedStatement ps = connection.prepareStatement(query);
		
		ResultSet results = ps.executeQuery();
		
		while(results.next()) {
			id = results.getInt("user_id");
			login = results.getString("login");
			password = results.getString("password");
			role = results.getString("role"); 
			
			model.addRow(new Object[] {id,login,password,role});
			
			
		}
		
			
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			populateTableUsersFromDatabase(this.users, this.model,this.connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
