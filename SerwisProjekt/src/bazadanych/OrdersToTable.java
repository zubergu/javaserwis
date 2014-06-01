package bazadanych;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import obsluga.Orders;
import obsluga.Products;
import obsluga.Users;

public class OrdersToTable extends AbstractAction {
	private Orders orders;
	private DefaultTableModel model;
	private Connection connection;
	
	public OrdersToTable(Orders orders, DefaultTableModel model, Connection connection) {
		this.orders = orders;
		this.model = model;
		this.connection = connection;
	}
	
	static public void populateTableOrdersFromDatabase(Orders orders, DefaultTableModel model, Connection connection) throws SQLException {
		int order_id;
		String customer;
		String order_type; 
		String description;
		String order_status;
		String order_handler;
		int display;
		int keyboard;
		int mouse;
		int os;
		int processor;
		int mainboard; 
		int speakers;
		int graphics;
		int GPS;
		int bluetooth;
		int hdd ;
		int dvd_rom;
		int ram;
		int other;
		
		String query = "select * from orders";
		
		PreparedStatement ps = connection.prepareStatement(query);
		
		ResultSet results = ps.executeQuery();
		
		while(results.next()) {
			/* ints will be 0/1 for true/false in database */
			order_id= results.getInt("order_id");
			customer= results.getString("customer");
			order_type=results.getString("order_type");
			description= results.getString("description");
			order_status=results.getString("order_status");
			order_handler= results.getString("order_handler");
			display= results.getInt("display");
			keyboard= results.getInt("keyboard");
			mouse= results.getInt("mouse");
			os= results.getInt("os");
			processor= results.getInt("processor");
			mainboard= results.getInt("mainboard");
			speakers= results.getInt("speakers");
			graphics= results.getInt("graphics");
			GPS= results.getInt("GPS");
			bluetooth= results.getInt("bluetooth");
			hdd = results.getInt("hdd");
			dvd_rom= results.getInt("dvd_rom");
			ram = results.getInt("ram");
			other = results.getInt("other");
			
			model.addRow(new Object[] {
				order_id,
				customer,
				order_type,
				description,
				order_status,
				order_handler,
				display,
				keyboard,
				mouse,
				os,
				processor,
				mainboard, 
				speakers,
				graphics,
				GPS,
				bluetooth,
				hdd ,
				dvd_rom,
				ram, 
				other});
			
			
		}
		
			
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			populateTableOrdersFromDatabase(this.orders, this.model,this.connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
