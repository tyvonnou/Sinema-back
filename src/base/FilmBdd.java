package base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bean.Film;

public class FilmBdd {
	
	public ArrayList <Film> listerFilms(Connection connection) {
		
		String sql = "SELECT * FROM `Film`;";
		ArrayList <Film> res = new ArrayList<Film>();
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Film film = new Film();
				film.setId(rs.getInt("FilmID"));
				film.setTitle(rs.getString("FilmName"));
				film.setReleaseDate(rs.getDate("FilmDateSort"));
				film.setDescription(rs.getString("FilmDescription"));
				res.add(film);
			}
			
			try {rs.close();}catch (Exception e) {}
			try {ps.close();}catch (Exception e) {}
			
		} catch (Exception e) {
			System.out.println("SQL Error " + e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
	
	public boolean enregistrerFilm(Film film, Connection connection) {
		
		boolean res = false;
		
		String sql = "INSERT INTO Film " + "(FilmDateSort, FilmDescription, FilmName) values (?, ?, ?)";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setDate(1, film.getReleaseDate());
			ps.setString(2, film.getDescription());
			ps.setString(3, film.getTitle());			
			res = (ps.executeUpdate() == 1);
			try {ps.close();}catch (Exception e) {}
			
		} catch (Exception e) {
			System.out.println("Error enregistrerLivre " + e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
}
