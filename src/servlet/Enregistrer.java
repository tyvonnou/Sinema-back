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
 * Servlet implementation class Enregistrer
 */
@WebServlet("/Enregistrer")
public class Enregistrer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Enregistrer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet Enregistrer");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost Enregistrer");
		// Reception du film
		String title = request.getParameter("title");
		String des = request.getParameter("description");
		Date dte = Date.valueOf(request.getParameter("releaseDate").substring(0,10));
		Film f = new Film(title,des,dte);
		System.out.println("Film reçu : Titre = "+f.getTitle()+" Description = "+f.getDescription()+ " Date de sortie = "+f.getReleaseDate());	
		
		// Connection a la base 
		Base base = new Base();
		base.open();
		Connection connection = base.getConnection();
		MovieDtb filmBdd = new MovieDtb();
		// Si le film existe 
		int id = -1;
		
		if (filmBdd.filmExist(f, connection)) {
		} else {
			id = filmBdd.addMovie(f, connection);
		}
		
		Hashtable<String, Integer > res = new Hashtable<String, Integer>();
		res.put("FilmID",id);
		response.setContentType("application/json");
		JsonGenerator generator = new JsonFactory().createGenerator(response.getOutputStream());
		generator.setCodec(new ObjectMapper());
		generator.writeObject(res); 
		generator.close();
		base.close();
		
	}

}
