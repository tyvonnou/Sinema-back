package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
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
import base.MovieDtb;
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
				String title = request.getParameter("title");
				String des = request.getParameter("description");
				Date dte = Date.valueOf(request.getParameter("releaseDate").substring(0,10));
				Film f = new Film(title,des,dte);
				System.out.println("Film reçu : Titre = "+f.getTitle()+" Description = "+f.getDescription()+ " Date de sortie = "+f.getReleaseDate());	
				// Gestion des erreurs
				Hashtable<String, String > err = new Hashtable<String, String>();
				err.put("titre","Veuillez saisir un titre");
				err.put("description","Veuillez saisir une description");
				err.put("releaseDate","Veuillez saisir une releaseDate");
				response.setContentType("application/json");
				JsonGenerator generator = new JsonFactory().createGenerator(response.getOutputStream());
				generator.setCodec(new ObjectMapper());
				generator.writeObject(err); 
				generator.close();
				// Connection a la base 
				Base base = new Base();
				base.open();
				Connection connection = base.getConnection();
				MovieDtb filmBdd = new MovieDtb();
				// Si le film existe pas
				if (!filmBdd.filmExist(f, connection)) {
					System.out.println("le film n'existe pas");
				} else {
					System.out.println("suppression...");
					filmBdd.deleteMovie(f, connection);
					if (filmBdd.filmExist(f, connection)) {
						System.out.println("Nope");
					} else {
						System.out.println("OK");
					}

				}
				base.close();
	}

}
