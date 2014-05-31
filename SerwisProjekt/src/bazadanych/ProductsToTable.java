package bazadanych;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import obsluga.Products;
import obsluga.Users;

public class ProductsToTable extends AbstractAction {
	private Products products;
	private DefaultTableModel model;
	private Connection connection;
	
	public ProductsToTable(Products products, DefaultTableModel model, Connection connection) {
		this.products = products;
		this.model = model;
		this.connection = connection;
	}
	
	static public void populateTableProductsFromDatabase(Products products, DefaultTableModel model, Connection connection) throws SQLException {
		int id;
		String name = "";
		int quantity = 0;
		float price = 0;
		
		String query = "select * from products";
		
		PreparedStatement ps = connection.prepareStatement(query);
		
		ResultSet results = ps.executeQuery();
		
		while(results.next()) {
			id = results.getInt("product_id");
			name = results.getString("name");
			quantity = results.getInt("quantity");
			price = results.getFloat("price"); 
			
			model.addRow(new Object[] {id,name,quantity,price});
			
			
		}
		
			
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			populateTableProductsFromDatabase(this.products, this.model,this.connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
