package obsluga;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Information about current ORDERS in database.
 */
public class Orders {
	/* DATABASE PART */
	public static final String DRIVER = "org.sqlite.JDBC";
	public static final String DB_URL = "jdbc:sqlite:orderstable.db";
	private Connection connection;
	private Statement statement;
	
	
	public Orders() {
		
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
		
		createOrdersTable();
	}
	
	
	/*
	 * Interface for managing ORDERS database.
	 */
	
	public boolean createOrdersTable() {
		String createOrders = "CREATE TABLE IF NOT EXISTS orders (order_id INTEGER PRIMARY KEY AUTOINCREMENT, customer varchar(255) NOT NULL, order_type varchar(255) NOT NULL, description varchar(255),order_status varchar(255),order_handler varchar(255),display int,keyboard int ,mouse int,os int,processor int, mainboard int, speakers int,graphics int, GPS int, bluetooth int,hdd int, dvd_rom int,ram int,other int)";
		
		try {
			statement.execute(createOrders);
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
