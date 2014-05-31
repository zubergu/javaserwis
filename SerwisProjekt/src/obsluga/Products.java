package obsluga;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Both information about currently logged user and database off all users and methods to modify USERS table.
 */
public class Products {
	/* DATABASE PART */
	public static final String DRIVER = "org.sqlite.JDBC";
	public static final String DB_URL = "jdbc:sqlite:productstable.db";
	private Connection connection;
	private Statement statement;
	
	
	public Products() {
		
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			connection = DriverManager.getConnection(DB_URL);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		createProductsTable();
	}
	
	
	/*
	 * Interface for managing USERS database.
	 */
	
	public boolean createProductsTable() {
		String createUser = "CREATE TABLE IF NOT EXISTS products (product_id INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(255) NOT NULL UNIQUE, quantity int, price float)";
		
		try {
			statement.execute(createUser);
		} catch(SQLException e ) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public Statement getStatement() {
		return this.statement;
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
