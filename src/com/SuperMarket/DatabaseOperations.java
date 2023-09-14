package com.techvidvan;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import org.sqlite.SQLiteDataSource;

public class DatabaseOperations {
//	declaring connection and dataSource variables
	private static Connection conn;
	private static SQLiteDataSource ds;
	
//	initialize method to initialize the database with all the tables
	public static void dbInit() {
		ds = new SQLiteDataSource();
		
		try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:Supermarket.db");
        } catch ( Exception e ) {
            e.printStackTrace();
            
            System.exit(0);
        }
        try {
        	 conn = ds.getConnection();
        	 
        	 Statement statement = conn.createStatement();
             statement.executeUpdate("CREATE TABLE IF NOT EXISTS customers (\n"
             		+ "  id INTEGER PRIMARY KEY,\n"
             		+ "  name TEXT NOT NULL,\n"
             		+ "  phone TEXT NOT NULL,\n"
             		+ "  email TEXT,\n"
             		+ "  address TEXT\n"
             		+ ");\n"

             		
             		+ "CREATE TABLE IF NOT EXISTS products (\n"
             		+ "  id INTEGER PRIMARY KEY,\n"
             		+ "  name TEXT NOT NULL,\n"
             		+ "  price DECIMAL(10,2) NOT NULL\n"
             		+ ");\n"
             		
             	
             		+ "CREATE TABLE IF NOT EXISTS orders (\n"
             		+ "  id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
             		+ "  customer_id INTEGER NOT NULL,\n"
             		+ "  date DATETIME NOT NULL,\n"
             		+ "  FOREIGN KEY (customer_id) REFERENCES customers(id)\n"
             		+ ");\n"
             		
             		+ "CREATE TABLE IF NOT EXISTS order_items (\n"
             		+ "  id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
             		+ "  order_id INTEGER NOT NULL,\n"
             		+ "  product_id INTEGER NOT NULL,\n"
             		+ "  quantity INTEGER NOT NULL,\n"
             		+ "  price DECIMAL(10,2) NOT NULL,\n"
             		+ "  FOREIGN KEY (order_id) REFERENCES orders(id),\n"
             		+ "  FOREIGN KEY (product_id) REFERENCES products(id)\n"
             		+ ");\n"
             		);
             
             
//           Closing statement and connection  
             statement.close();
        	 conn.close();
        	 
        }catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            }catch (SQLException e) {
                System.err.println(e);
              }
        
        }
	

	}

		
	
	

	/*
	 * ----------------------------------- Order Operations--------------------------------------------------
	 */
//	Method to create new orders
	public static int createNewOrder(int custID) throws SQLException {
		conn = ds.getConnection();
		PreparedStatement ps =conn.prepareStatement("INSERT INTO "
													+ "orders(customer_id,date)"
													+ "VALUES(?,?)");
		
		ps.setInt(1, custID);
		ps.setDate(2, Date.valueOf(java.time.LocalDate.now()));
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		int oid = rs.getInt(1);
		rs.close();
		ps.close();
		conn.close();
		return oid;
	}
	
//	Method to add new items to the order 
	public static void addOrderItems(int orderID,int prodID,int quantity,Float price) throws SQLException {
		conn = ds.getConnection();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO order_items (order_id, product_id, quantity, price)"
				+ " VALUES(?,?,?,?);");
		ps.setInt(1, orderID);
		ps.setInt(2, prodID);
		ps.setInt(3, quantity);
		ps.setFloat(4, price);
		
		ps.executeUpdate();
		ps.close();
		conn.close();
		
	}
	
//	Method to discard/delete the order and the ordered items
	public static void discardOrder(int orderID) throws SQLException{
		conn = ds.getConnection();
		String sql = "DELETE  FROM order_items WHERE order_id = ?;\n";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, orderID);
		ps.executeUpdate();
		ps = conn.prepareStatement("DELETE  FROM orders WHERE id = ?;");
		ps.setInt(1, orderID);
		ps.executeUpdate();
		ps.close();
		
		
		conn.close();
	}

//	Method to delete items from the order_items table 
	public static void deleteOrderItem(int orderID) throws SQLException {
		conn = ds.getConnection();
		String sql = "DELETE FROM order_items WHERE id = (SELECT MAX(id) FROM order_items WHERE order_id = ?);";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, orderID);
		ps.executeUpdate();
		
		ps.close();
		conn.close();
	}
	
//	Method to total up the price for the specific order 
	public static float getTotalPrice(int orderID) throws SQLException {
		conn = ds.getConnection();
		PreparedStatement ps =conn.prepareStatement("SELECT SUM(price) as total_price\n"
				+ "FROM order_items WHERE order_id = ?;");
		
		ps.setInt(1, orderID);
		ResultSet rs = ps.executeQuery();
		float price = rs.getFloat(1);
		ps.close();
		conn.close();
		
		return price;
	}

	/*
	 * ----------------------------------- Product Operations--------------------------------------------------
	 */

//	method to add the produc into the database
	public static void addProduct( String name, Float price) throws SQLException {
        String query = "INSERT INTO products (name, price) VALUES (?, ?)";
		conn = ds.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setFloat(2, price);
            
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        
        }
	
	
	//Method to get product details from the database
		public static String[] getProd(int id) throws SQLException {
			
			conn = ds.getConnection();
			PreparedStatement ps =conn.prepareStatement("SELECT * FROM products WHERE id = ?");
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			String[] product = {rs.getString("id"),rs.getString("name"),rs.getString("price")};
			ps.close();
			conn.close();
			
			return product;
		}
	
	
	/*
	 * ----------------------------------- Customer Operations--------------------------------------------------
	 */
		public static void addCustomer( String name, String phone, String email, String address) throws SQLException {
	        String query = "INSERT INTO customers (name, phone, email, address) VALUES (?, ?, ?, ?)";
			conn = ds.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(query);
	            stmt.setString(1, name);
	            stmt.setString(2, phone);
	            stmt.setString(3, email);
	            stmt.setString(4, address);
	            
	            stmt.executeUpdate();
	            stmt.close();
	            conn.close();
	        
	        }
		
		public static void delete(int id,String table) throws SQLException {
			String query = "DELETE FROM "+table+" WHERE id = ? ";
			conn = ds.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(query);
	            stmt.setInt(1, id);
	            stmt.executeUpdate();
	            stmt.close();
	            conn.close();
		}
		

	
//	Method to update the comboBoxes with data from the database
	public static void updateCombox(String table,JComboBox<String> cbx) throws SQLException {
		cbx.removeAll();
		conn = ds.getConnection();
		String sql = "SELECT * FROM "+table+";";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			cbx.addItem(rs.getString("id") +"-|-"+ rs.getString("name"));
		}
		
		
		rs.close();
		ps.close();
		conn.close();

	}


	
//	Method to Load Data from the database into the table
	public static void loadData(DefaultTableModel model,String table) throws SQLException {
		model.setRowCount(0);
		conn = ds.getConnection();
		String sql = "SELECT * FROM "+table+";";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		Object[] row = new Object[model.getColumnCount()]; 
		while (rs.next()) {
			for (int i = 0; i < row.length; i++) {
				row[i] = rs.getObject(i+1);
			}
			model.addRow(row);

		}
		ps.close();
		conn.close();
	}



}