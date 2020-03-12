package base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SessionDtb {

  /**
   * Check if the user entered exist in the database.
   * @param username | Name of the user.
   * @param password | Password of the user.
   * @param connection | Allows connection to mySQL database.
   * @return True if user exist, false if not.
   */
  public boolean connectSession(String username, String password, Connection connection) {

    boolean result = false;

    // Query preparation
    String sql = "SELECT * FROM UserInfo WHERE UserName = ? AND UserPassword = ? ;";

    try {
      // Launching the query
      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setString(1, username);
      ps.setString(2, password);
      ResultSet rs = ps.executeQuery();

      // User exist
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
      System.out.println("Error connection " + e.getMessage());
      e.printStackTrace();
    }
    return result;
  }
 
}
