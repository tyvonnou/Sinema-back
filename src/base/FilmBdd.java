package base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bean.Film;

public class FilmBdd {
	
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
}
