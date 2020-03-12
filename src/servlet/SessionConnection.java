package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import base.Base;
import base.SessionDtb;
import manager.Manager;

/**
 * Servlet implementation class Connection
 */
@WebServlet("/SessionConnection")
public class SessionConnection extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public SessionConnection() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Manager manager = Manager.init(request);
    if (manager.isLogged()) {
      // User already logged
      // TODO setResponse for redirection
      return;
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    System.out.println("doPost Connection");
    boolean logged = false;

    // Get login details
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    // Database connection
    Base base = new Base();
    base.open();
    Connection connection = base.getConnection();
    SessionDtb sessionDtb = new SessionDtb();

    if (sessionDtb.connectSession(username, password, connection)) {
      // User exist in the database

      Manager manager = Manager.init(request);
      manager.setLogged(true);
      manager.setIdentity(username);
      logged = true;
    }

    // Response sent
    Hashtable<String, Boolean> responseJson = new Hashtable<String, Boolean>();
    responseJson.put("isLogged", logged);
    response.setContentType("application/json");
    JsonGenerator generator = new JsonFactory().createGenerator(response.getOutputStream());
    generator.setCodec(new ObjectMapper());
    generator.writeObject(generator);
    generator.close();
    base.close();
  }

}
