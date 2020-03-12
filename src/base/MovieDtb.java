package base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bean.Film;
import picture.Picture;

public class MovieDtb {

  /**
   * Insert a new movie into the database.
   * 
   * @param film | New movie.
   * @param connection | Allows connection to mySQL database.
   * @return ID of the last inserted movie, -1 if the query failed.
   */
  public int addMovie(Film film, Connection connection) {

    int result = -1;

    // Check if the movie already exist
    if (this.movieExist(film, connection))
      return result;

    // Query preparation
    String sql = "INSERT INTO Film (FilmDateSort, FilmDescription, FilmName) values (?, ?, ?);";

    try {
      // Launching the query
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setDate(1, film.getReleaseDate());
      ps.setString(2, film.getDescription());
      ps.setString(3, film.getTitle());
      ps.executeUpdate();

      // Return id of the last inserted movie
      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next())
        result = Integer.parseInt(rs.getString(1));

      // Close database connection
      try {
        rs.close();
      } catch (Exception e) {
      }
      try {
        ps.close();
      } catch (Exception e) {
      }

    } catch (Exception e) {
      System.out.println("Error addMovie " + e.getMessage());
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Update the selected movie from the database.
   * 
   * @param id | Movie ID.
   * @param connection | Allows connection to mySQL database.
   * @return True if the movie was updated, false if not.
   */
  public boolean updateMovie(Film film, Connection connection) {

    boolean result = false;

    // Query preparation
    String sql = "UPDATE Film SET WHERE FilmDateSort = ?, FilmDescription = ?, FilmName = ?;";

    try {
      // Launching the query
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setDate(1, film.getReleaseDate());
      ps.setString(2, film.getDescription());
      ps.setString(3, film.getTitle());
      ps.executeUpdate();

      // Movie updated
      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next())
        result = Boolean.parseBoolean((rs.getString(1)));

      // Close database connection
      try {
        rs.close();
      } catch (Exception e) {
      }
      try {
        ps.close();
      } catch (Exception e) {
      }

    } catch (Exception e) {
      System.out.println("Error connection " + e.getMessage());
      e.printStackTrace();
    }
    return result;
  }
  
  /**
   * Delete the selected movie from the database.
   * 
   * @param id | Movie ID.
   * @param connection | Allows connection to mySQL database.
   * @return True if the movie was deleted, false if not.
   */
  public boolean deleteMovie(int id, Connection connection) {

    boolean result = false;

    // Delete all images related to the movie before
    if (!this.deleteImages(id, connection))
      return result;

    // Query preparation
    String sql = "DELETE FROM Film WHERE FilmID = ?;";

    try {
      // Launching the query
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setInt(1, id);
      ps.executeUpdate();

      // Movie deleted
      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next())
        result = Boolean.parseBoolean((rs.getString(1)));

      // Close database connection
      try {
        rs.close();
      } catch (Exception e) {
      }
      try {
        ps.close();
      } catch (Exception e) {
      }

    } catch (Exception e) {
      System.out.println("Error connection " + e.getMessage());
      e.printStackTrace();
    }
    return result;
  }

  // TODO
  /**
   * Add an image into the database.
   * 
   * @param picture | New picture.
   * @param connection | Allows connection to mySQL database.
   * @return True if the image was inserted, false if not.
   */
  public boolean addImage(Picture picture, Connection connection) {

    boolean result = false;

    // Query preparation
    String sql = "INSERT INTO Picture (PictureLocation, FilmID) values (?, ?);";

    try {
      // Launching the query
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, picture.getFilmLocation());
      ps.setInt(2, picture.getFilmID());
      ps.executeUpdate();

      // Image inserted
      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next())
        result = Boolean.parseBoolean((rs.getString(1)));

      // Close database connection
      try {
        rs.close();
      } catch (Exception e) {
      }
      try {
        ps.close();
      } catch (Exception e) {
      }

    } catch (Exception e) {
      System.out.println("Error updateMovie " + e.getMessage());
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Delete an image from the database.
   * 
   * @param id | Picture ID.
   * @param connection | Allows connection to mySQL database.
   * @return True if the image was deleted, false if not.
   */
  public boolean deleteImage(int id, Connection connection) {

    boolean result = false;
    String location = null;

    // Query preparation
    String sqlSelect = "SELECT PictureLocation FROM Picture WHERE PictureID = ?;";
    String sqlDelete = "DELETE FROM Picture WHERE PictureID = ?;";

    try {
      // Launching the select query
      PreparedStatement ps = connection.prepareStatement(sqlSelect);
      ps.setInt(1, id);
      ResultSet rsSelect = ps.executeQuery();

      // Location of the selected image
      if (rsSelect.next()) {
        location = rsSelect.getString("PictureLocation");
      } else {
        return result;
      }

      // Delete image from the server
      Picture.deleteImageFromServ(location);

      // Launching the delete query
      ps = connection.prepareStatement(sqlDelete, Statement.RETURN_GENERATED_KEYS);
      ps.setInt(1, id);
      ps.executeUpdate();

      // Images deleted
      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next())
        result = Boolean.parseBoolean((rs.getString(1)));

      // Close database connection
      try {
        rs.close();
      } catch (Exception e) {
      }
      try {
        ps.close();
      } catch (Exception e) {
      }

    } catch (Exception e) {
      System.out.println("Error connection " + e.getMessage());
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Delete all images of the selected movie
   * 
   * @param id | Movie ID.
   * @param connection | Allows connection to mySQL database.
   * @return True if the images was deleted, false if not.
   */
  public boolean deleteImages(int id, Connection connection) {

    boolean result = false;
    List<String> picLocations = new ArrayList<>();

    // Query preparation
    String sqlSelect = "SELECT PictureLocation FROM Picture WHERE FilmID = ?;";
    String sqlDelete = "DELETE FROM Picture WHERE FilmID = ?;";

    try {
      // Launching the select query
      PreparedStatement ps = connection.prepareStatement(sqlSelect);
      ps.setInt(1, id);
      ResultSet rsSelect = ps.executeQuery();

      // Location List of the selected images
      while (rsSelect.next()) {
        picLocations.add((rsSelect.getString("PictureLocation")));
      }

      // Delete all images from the server
      for (String location : picLocations) {
        Picture.deleteImageFromServ(location);
      }

      // Launching the delete query
      ps = connection.prepareStatement(sqlDelete, Statement.RETURN_GENERATED_KEYS);
      ps.setInt(1, id);
      ps.executeUpdate();

      // Images deleted
      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next())
        result = Boolean.parseBoolean((rs.getString(1)));

      // Close database connection
      try {
        rs.close();
      } catch (Exception e) {
      }
      try {
        ps.close();
      } catch (Exception e) {
      }

    } catch (Exception e) {
      System.out.println("Error connection " + e.getMessage());
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Check if the movie already exist.
   * 
   * @param film | The verified movie.
   * @param connection | Allows connection to mySQL database.
   * @return True if the movie already exist, false if not.
   */
  public boolean movieExist(Film movie, Connection connection) {

    boolean result = false;
    
    // Query preparation
    String sql =
        "SELECT FilmID FROM `Film` WHERE FilmDateSort = ? AND FilmDescription = ? AND FilmName = ?;";
    try {
      // Launching the query
      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setDate(1, movie.getReleaseDate());
      ps.setString(2, movie.getDescription());
      ps.setString(3, movie.getTitle());
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

    } catch (Exception e) {
      System.out.println("Error addMovie " + e.getMessage());
      e.printStackTrace();
    }
    return result;
  }
}
