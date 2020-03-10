package base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import bean.User;

public class UserBdd {
	
	public User connection(User user, Connection connection) {
		
		User usr = new User();
		// Préparation de la requête
		String sql = "SELECT * FROM UserInfo WHERE UserName = ? AND UserPassword = ? ;";
		
		try {
			// Lancement de la requête 
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			// Création d'un objet User
			usr.setId(rs.getInt("UserID"));
			usr.setUsername(rs.getString("UserName"));
			usr.setPassword(rs.getString("UserPassword"));
			usr.setIsAdmin(rs.getBoolean("UserIsAdmin"));
			try {ps.close();}catch (Exception e) {}
			
		} catch (Exception e) {
			// Si echec affichge de l'erreur
			System.out.println("Error connection " + e.getMessage());
			e.printStackTrace();
		}
		
		return usr;		
		
	}
}
