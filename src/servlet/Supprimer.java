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
import base.FilmBdd;
import bean.Film;

/**
 * Servlet implementation class Supprimer
 */
@WebServlet("/Supprimer")
public class Supprimer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Supprimer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet Supprimer");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doPost Supprimer");
		// Reception du film
		Film f  = new ObjectMapper().readValue(request.getReader(), Film.class);
		System.out.println("Film reçu : Titre = "+f.getTitle()+" Description = "+f.getDescription()+ " Date de sortie = "+f.getReleaseDate());	

		Hashtable<String, String > err = new Hashtable<String, String>();
		err.put("Titre","Veuillez saisir un Titre");
		err.put("Description","Veuillez saisir une Description");
		err.put("Date de sortie ","Veuillez saisir une Date de sortie");
		
		response.setContentType("application/json");
		JsonGenerator generator = new JsonFactory().createGenerator(response.getOutputStream());
		generator.setCodec(new ObjectMapper());
		generator.writeObject(err); 
		generator.close();
		
		Base base = new Base();
		base.ouvrir();
		Connection connection = base.getConnection();
		FilmBdd filmBdd = new FilmBdd();
		filmBdd.supprimerFilm(f, connection);
		base.fermer();
	}

}
