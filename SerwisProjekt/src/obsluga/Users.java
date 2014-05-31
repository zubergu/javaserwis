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
public class Users {
	/* DATABASE PART */
	public static final String DRIVER = "org.sqlite.JDBC";
	public static final String DB_URL = "jdbc:sqlite:userstable.db";
	private Connection connection;
	private Statement statement;
	
	/* LOGGED USER INFO */
	private boolean alreadyLogged;
	private boolean admin;
	private boolean clerk;
	private boolean service;
	
	public Users() {
		this.admin = false;
		this.clerk = false;
		this.service = false;
		this.alreadyLogged = false;
		
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
		
		createUsersTable();
	}
	/*
	 * Interface for logged user information.
	 */
	public void logoutUser() {
		this.admin = false;
		this.clerk = false;
		this.service = false;
		this.alreadyLogged = false;
	}
	
	public void loginAdmin() {
		this.admin = true;
		this.clerk = true;
		this.service = true;
		this.alreadyLogged = true;
	}
	
	public void loginService() {
		this.admin = false;
		this.clerk = false;
		this.service = true;
		this.alreadyLogged = true;
	}
	
	public void loginClerk() {
		this.admin = false;
		this.clerk = true;
		this.service = false;
		this.alreadyLogged = true;
	}
	
	public boolean isAdmin() {
		return this.admin;
	}
	
	public boolean isService() {
		return this.service;
	}
	
	public boolean isClerk() {
		return this.clerk;
	}
	
	public boolean getAlreadyLogged() {
		return this.alreadyLogged;
	}
	public void setAlreadyLogged() {
		this.alreadyLogged = true;
	}
	
	
	/*
	 * Interface for managing USERS database.
	 */
	
	public boolean createUsersTable() {
		String createUser = "CREATE TABLE IF NOT EXISTS users (user_id INTEGER PRIMARY KEY AUTOINCREMENT, login varchar(255) NOT NULL UNIQUE, password varchar(255), role varchar(255))";
		
		try {
			statement.execute(createUser);
		} catch(SQLException e ) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	
	
	public void printAllUsers() {
		try {
			ResultSet result = statement.executeQuery("SELECT * from users");
			while(result.next()) {
				System.out.println(result.getInt("user_id"));
				System.out.println(result.getString("login"));
				System.out.println(result.getString("password"));
				System.out.println(result.getString("role"));
			}
		} catch (SQLException e) {
			System.out.println("cant read from database.");
		}
		
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
