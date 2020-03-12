package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.Base;
import base.MovieDtb;
import bean.Film;
import helper.Servlet;

/**
 * Servlet implementation class MovieServlet
 */
@WebServlet("/movie")
public class MovieServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public MovieServlet() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {}

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    System.out.println("doPost movie");
    
    // Get request parameters
    String title = request.getParameter("title");
    String description = request.getParameter("description");
    Date releaseDate = new Date(Long.parseLong(request.getParameter("releaseDate")));
    
    // Construct new movie
    Film film = new Film(title, description, releaseDate);

    // Open database connection
    Base base = new Base();
    base.open();
    Connection connection = base.getConnection();
    
    // Query execution
    try {
      film.id = MovieDtb.addMovie(film, connection);
    } catch (SQLException e) {
      response.setStatus(500);
      e.printStackTrace();
    }
    Servlet.formatResponse(response, film, "application/json");
    
    // Close database connection
    base.close();
  }

}
