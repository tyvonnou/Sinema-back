package base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bean.Film;
import bean.Picture;
import helper.Image;

public class MovieDtb {

  /**
   * Insert a new movie into the database.
   * 
   * @param film | New movie.
   * @param connection | Allows connection to mySQL database.
   * @return ID of the last inserted movie, -1 if the query failed.
   * @throws SQLException
   */
  public static int addMovie(Film film, Connection connection) throws SQLException {

    int result = -1;

    // Check if the movie already exist
    if (MovieDtb.movieExist(film, connection))
      return result;

    // Query preparation
    String sql = "INSERT INTO Film (FilmTitle, FilmDescription, FilmReleaseDate) values (?, ?, ?);";

    // Launching the query
    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    ps.setString(1, film.title);
    ps.setString(2, film.description);
    ps.setDate(3, film.releaseDate);
    ps.executeUpdate();

    // Return id of the last inserted movie
    ResultSet rs = ps.getGeneratedKeys();
    if (rs.next()) {
      result = Integer.parseInt(rs.getString(1));
    }

    // Close database connection
    try {
      rs.close();
    } catch (Exception e) {
    }
    try {
      ps.close();
    } catch (Exception e) {
    }
    return result;
  }

  /**
   * Update the selected movie from the database.
   * 
   * @param id | Movie ID.
   * @param connection | Allows connection to mySQL database.
   * @return True if the movie was updated, false if not.
   * @throws SQLException
   */
  public static void updateMovie(Film film, Connection connection) throws SQLException {

    // Query preparation
    String sql = "UPDATE Film SET WHERE FilmTitle = ?, FilmDescription = ?, FilmReleaseDate = ?;";

    // Launching the query
    PreparedStatement ps = connection.prepareStatement(sql);
    ps.setString(3, film.title);
    ps.setString(2, film.description);
    ps.setDate(1, film.releaseDate);
    ps.executeUpdate();

    // Close database connection
    try {
      ps.close();
    } catch (Exception e) {
    }
  }

  /**
   * Delete the selected movie from the database.
   * 
   * @param id | Movie ID.
   * @param connection | Allows connection to mySQL database.
   * @return True if the movie was deleted, false if not.
   * @throws SQLException
   */
  public static void deleteMovie(int id, Connection connection) throws SQLException {

    // Delete all images related to the movie before
    MovieDtb.deleteImages(id, connection);

    // Query preparation
    String sql = "DELETE FROM Film WHERE FilmID = ?;";

    // Launching the query
    PreparedStatement ps = connection.prepareStatement(sql);
    ps.setInt(1, id);
    ps.executeUpdate();

    try {
      ps.close();
    } catch (Exception e) {
    }
  }

  /**
   * Add an image into the database.
   * 
   * @param picture | New picture.
   * @param connection | Allows connection to mySQL database.
   * @return True if the image was inserted, false if not.
   * @throws SQLException
   */
  public static void addImage(Picture picture, Connection connection) throws SQLException {

    // Query preparation
    String sql =
        "INSERT INTO Picture (PictureLocation, PictureContentType, FilmID) values (?, ?, ?);";

    // Launching the query
    PreparedStatement ps = connection.prepareStatement(sql);
    ps.setString(1, picture.getLocation());
    ps.setString(1, picture.getContentType());
    ps.setInt(2, picture.getIdFilm());
    ps.executeUpdate();

    // Close database connection
    try {
      ps.close();
    } catch (Exception e) {
    }
  }

  /**
   * Delete an image from the database.
   * 
   * @param id | Picture ID.
   * @param connection | Allows connection to mySQL database.
   * @return True if the image was deleted, false if not.
   * @throws SQLException
   */
  public static void deleteImage(int id, Connection connection) throws SQLException {

    String location = null;

    // Query preparation
    String sqlSelect = "SELECT PictureLocation FROM Picture WHERE PictureID = ?;";
    String sqlDelete = "DELETE FROM Picture WHERE PictureID = ?;";

    // Launching the select query
    PreparedStatement ps = connection.prepareStatement(sqlSelect);
    ps.setInt(1, id);
    ResultSet rs = ps.executeQuery();

    // Location of the selected image
    if (rs.next()) {
      location = rs.getString("PictureLocation");

      // Delete image from the server
      Image.deleteImageFromServ(location);
    }

    // Launching the delete query
    ps = connection.prepareStatement(sqlDelete);
    ps.setInt(1, id);
    ps.executeUpdate();

    try {
      rs.close();
    } catch (Exception e) {
    }
    try {
      ps.close();
    } catch (Exception e) {
    }
  }

  /**
   * Delete all images of the selected movie
   * 
   * @param id | Movie ID.
   * @param connection | Allows connection to mySQL database.
   * @return True if the images was deleted, false if not.
   * @throws SQLException
   */
  public static void deleteImages(int id, Connection connection) throws SQLException {

    List<String> picLocations = new ArrayList<>();

    // Query preparation
    String sqlSelect = "SELECT PictureLocation FROM Picture WHERE FilmID = ?;";
    String sqlDelete = "DELETE FROM Picture WHERE FilmID = ?;";

    // Launching the select query
    PreparedStatement ps = connection.prepareStatement(sqlSelect);
    ps.setInt(1, id);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      picLocations.add((rs.getString("PictureLocation")));
    }

    // Delete all images from the server
    for (String location : picLocations) {
      Image.deleteImageFromServ(location);
    }

    // Launching the delete query
    ps = connection.prepareStatement(sqlDelete);
    ps.setInt(1, id);
    ps.executeUpdate();

    // Close database connection
    try {
      rs.close();
    } catch (Exception e) {
    }
    try {
      ps.close();
    } catch (Exception e) {
    }
  }

  /**
   * Check if the movie already exist.
   * 
   * @param film | The verified movie.
   * @param connection | Allows connection to mySQL database.
   * @return True if the movie already exist, false if not.
   * @throws SQLException
   */
  public static boolean movieExist(Film film, Connection connection) throws SQLException {

    boolean result = false;

    // Query preparation
    String sql =
        "SELECT FilmID FROM `Film` WHERE FilmTitle = ? AND FilmDescription = ? AND FilmReleaseDate = ?;";

    // Launching the query
    PreparedStatement ps = connection.prepareStatement(sql);
    ps.setString(1, film.title);
    ps.setString(2, film.description);
    ps.setDate(3, film.releaseDate);
    ResultSet rs = ps.executeQuery();

    // Movie exist
    if (rs.next()) {
      result = true;
    }

    // Close database connection
    try {
      rs.close();
    } catch (Exception e) {
    }
    try {
      ps.close();
    } catch (Exception e) {
    }
    return result;
  }
}
