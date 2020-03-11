package base;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import bean.Film;

public class FilmBdd {
	
	// Vérifie si un film existe dans la base 
	public boolean filmExist(Film f, Connection connection) {
		boolean res = false;
		// Préparation de la requête et du résultat
		String sql = "SELECT FilmID FROM `Film` WHERE FilmDateSort = ? AND FilmDescription = ? AND FilmName = ?;";
		try {
			// Lancement de la requête 
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setDate(1, f.getReleaseDate());
			ps.setString(2, f.getDescription());
			ps.setString(3, f.getTitle());
			ResultSet rs = ps.executeQuery();
			// Tant qu'il y a des films 
			if (rs.next())  {
				res = true;
			}
			// Fermeture 
			try {rs.close();}catch (Exception e) {}
			try {ps.close();}catch (Exception e) {}
		
		} catch (Exception e) {
			// Si echec affichage de l'erreur
			System.out.println("SQL Error " + e.getMessage());
			e.printStackTrace();
		}
		// Je retourne le résultat
		return res;
	}
	
	// Liste les films présent sur la base
	public ArrayList <Film> listerFilms(Connection connection) {
		// Préparation de la requête et du résultat
		String sql = "SELECT * FROM `Film`;";
		ArrayList <Film> res = new ArrayList<Film>();
		
		try {
			// Lancement de la requête 
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			// Tant qu'il y a des films 
			while (rs.next()) {
				// Création d'un objet film
				Film film = new Film();
				film.setId(rs.getInt("FilmID"));
				film.setTitle(rs.getString("FilmName"));
				film.setReleaseDate(rs.getDate("FilmDateSort"));
				film.setDescription(rs.getString("FilmDescription"));
				// Ajout à la liste de résultat
				res.add(film);
			}
			// Fermeture 
			try {rs.close();}catch (Exception e) {}
			try {ps.close();}catch (Exception e) {}
		
		} catch (Exception e) {
			// Si echec affichage de l'erreur
			System.out.println("SQL Error " + e.getMessage());
			e.printStackTrace();
		}
		// Je retourne le résultat
		return res;
	}
	
	// Ajoute un film à la base 
	public boolean enregistrerFilm(Film film, Connection connection) {
		
		// Préparation du résultat
		boolean res = false;
		// Préparation de la requête
		String sql = "INSERT INTO Film " + "(FilmDateSort, FilmDescription, FilmName) values (?, ?, ?)";
	
		try {
			// Remplissage de la requête 
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setDate(1, film.getReleaseDate());
			ps.setString(2, film.getDescription());
			ps.setString(3, film.getTitle());
			// Modification du résultat
			res = (ps.executeUpdate() == 1);
			try {ps.close();}catch (Exception e) {}
			
		} catch (Exception e) {
			// Si echec affichge de l'erreur
			System.out.println("Error enregistrerFilm " + e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
	
	// Supprime un film de la base
	public boolean supprimerFilm(Film film, Connection connection) {
		// Préparation du résultat
		boolean res = false;
		// Préparation de la requête
		String sql = "DELETE FROM Film WHERE FilmDateSort = ? AND FilmDescription = ? AND FilmName = ?;";
		try {
			// Remplissage de la requête 
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setDate(1, film.getReleaseDate());
			ps.setString(2, film.getDescription());
			ps.setString(3, film.getTitle());
			// Modification du résultat
			res = (ps.executeUpdate() == 1);
			try {ps.close();}catch (Exception e) {}
			
		} catch (Exception e) {
			// Si echec affichge de l'erreur
			System.out.println("Error suprimerFilm " + e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
	
	// Supprime une image du serveur
	public boolean supprimerImageFromServ(String location) {
		// Préparation du résultat
		boolean res = false;
		// Fichier à supprimer 
		File f= new File(location);           
		if(f.delete())                        
		{  
			System.out.println(f.getName() + " deleted");     
			res = true;
		}  
		else  
		{  
			System.out.println("failed");  
		}  
		return res;
	}
	
	// Supprime toutes les images d'un films dans la base
	public boolean supprimerImages(Film film, Connection connection) {
		// Préparation du résultat
		boolean res = false;
		// Préparation de la requête
		String sql = "DELETE FROM Picture WHERE FilmID = ?";
		try {
			// Remplissage de la requête 
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, film.getId());
			// Modification du résultat
			res = (ps.executeUpdate() == 1);
			try {ps.close();}catch (Exception e) {}
			
		} catch (Exception e) {
			// Si echec affichge de l'erreur
			System.out.println("Error suprimerImage " + e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
	
	// Ajout d'une image
	public boolean ajouterImage(Integer idfilm,  String location, Connection connection) {
		// Préparation du résultat
		boolean res = false;
		// Préparation de la requête
		String sql = "INSERT INTO Picture " + "(PictureLocation, FilmID) values (?, ?)";
		try {
			// Remplissage de la requête 
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, location);
			ps.setInt(2, idfilm);
			// Modification du résultat
			res = (ps.executeUpdate() == 1);
			try {ps.close();}catch (Exception e) {}
			
		} catch (Exception e) {
			// Si echec affichge de l'erreur
			System.out.println("Error suprimerImage " + e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
	
}
