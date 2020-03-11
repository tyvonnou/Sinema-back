package servlet;

import java.io.IOException;
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
import base.UserBdd;
import bean.User;

/**
 * Servlet implementation class Connection
 */
@WebServlet("/Connection")
public class Connection extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Connection() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet Connection");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doPost Connection");
		User u  = new ObjectMapper().readValue(request.getReader(), User.class);
		System.out.println("User demandé : Pseudo = "+ u.getUsername() + " Mdp = "+u.getPassword()+ " Est admin = "+ u.getIsAdmin());
		// Gestion des erreurs
		Hashtable<String, String > err = new Hashtable<String, String>();
		err.put("username","Veuillez saisir un username");
		err.put("password","Veuillez saisir un password");
		err.put("isAdmin","Veuillez saisir une isAdmin");
		response.setContentType("application/json");
		JsonGenerator generator = new JsonFactory().createGenerator(response.getOutputStream());
		generator.setCodec(new ObjectMapper());
		generator.writeObject(err); 
		generator.close();
		Base base = new Base();
		base.ouvrir();
		java.sql.Connection connection = base.getConnection();
		UserBdd userBdd = new UserBdd();
		userBdd.connection(u, connection);
		base.fermer();
	}

}
