package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import bean.Film;
import base.Base;
import base.FilmBdd;

/**
 * Servlet implementation class Lister
 */
@WebServlet("/Lister")
public class Lister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Lister() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet Lister");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost Lister");

		Base base = new Base();
		base.ouvrir();
		Connection connection = base.getConnection();
		FilmBdd filmBdd = new FilmBdd();
		ArrayList <Film> films = filmBdd.listerFilms(connection);
		base.fermer();
		
		response.setContentType("application/json");
		JsonGenerator generator = new JsonFactory().createGenerator(response.getOutputStream());
		generator.setCodec(new ObjectMapper());
		generator.writeObject(films); 
		generator.close();
	}

}
