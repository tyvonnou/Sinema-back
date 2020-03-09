package base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import bean.Livre;

public class Base {

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	

	}

	Connection connection;
	
	public Base () {
		this.connection = null;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public boolean ouvrir() {
		
		boolean res = false;
		
		String url = null;
		String user = null;
		String password = null;
		
		ResourceBundle rb = 
		ResourceBundle.getBundle("config/config");
		url = rb.getString("url");
		user = rb.getString("user");
		password = rb.getString("password");
		
		System.out.println("url = "+url);
		System.out.println("user = "+user);
		
		try {
			connection = DriverManager.getConnection(url, user, password);
			res = true;
		} catch (SQLException e) {
			System.out.println("erreur getConnection "+ e.getMessage());
			e.printStackTrace();
		}

		return res;
	}
	
	public boolean fermer() {
		try {connection.close();}catch (Exception e) {}
		return true;
	}
	
}
