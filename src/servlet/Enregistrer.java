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
import bean.Livre;

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
//		System.out.println("doPost Enregistrer");
//		
//		Livre l  = new ObjectMapper().
//				readValue(request.getReader(), Livre.class);
//		System.out.println("Livre reçu : titre = "+l.getTitre()+" auteur = "+l.getAuteur()+ " année = "+l.getAnnee());	
//		
//		
//		Hashtable<String, String > err = new Hashtable<String, String>();
//		err.put("titre","Veuillez saisir un titre");
//		err.put("auteur","Veuillez saisir un auteur");
//		err.put("annee","Veuillez saisir une année");
//		
//		response.setContentType("application/json");
//		JsonGenerator generator = new JsonFactory().
//				createGenerator(response.getOutputStream());
//		generator.setCodec(new ObjectMapper());
//		generator.writeObject(err); 
//		generator.close();
//		
//		Base base = new Base();
//		base.ouvrir();
//		base.enregistrerLivre(l);
//		base.fermer();
		
	}

}
