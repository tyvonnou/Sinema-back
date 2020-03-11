package servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import base.Base;
import base.FilmBdd;

/*
 * Notre serlvet permettant de récupérer les fichiers côté serveur.
 * Elle répondra à l'URL /upload dans l'application Web considérée.
 */
@WebServlet( urlPatterns = "/upload" )
@MultipartConfig( fileSizeThreshold = 1024 * 1024, 
                  maxFileSize = 1024 * 1024 * 5,
                  maxRequestSize = 1024 * 1024 * 5 * 5 )
public class Upload extends HttpServlet {

    private static final long serialVersionUID = 1273074928096412095L;
    
    /*
     * Chemin dans lequel les images seront sauvegardées.
     */
    public static final String IMAGES_FOLDER = "/Images";
        
    public String uploadPath;
    
    /*
     * Si le dossier de sauvegarde de l'image n'existe pas, on demande sa création.
     */ 
    @Override
    public void init() throws ServletException {
        uploadPath = getServletContext().getRealPath( IMAGES_FOLDER );
        File uploadDir = new File( uploadPath );
        if ( ! uploadDir.exists() ) uploadDir.mkdir();
    }
       
    /*
     * Récupération et sauvegarde du contenu de chaque image.
     */ 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp)
            throws ServletException, IOException {
    	 	Base base = new Base();
 			base.ouvrir();
	        for ( Part part : request.getParts() ) {
	        	// Stockage serveur 
	            String fileName = getFileName( part );
	            String fullPath = uploadPath + File.separator + fileName;
	            part.write( fullPath );
	            // Stockage MySQL
	            FilmBdd filmbdd = new FilmBdd();
	    		Connection connection = base.getConnection();
	            filmbdd.ajouterImage(Integer.parseInt(request.getParameter("FilmID")), fullPath, connection);      
	        }
	        base.fermer();
    }

    /*
     * Récupération du nom du fichier dans la requête.
     */
    private String getFileName( Part part ) {
        for ( String content : part.getHeader( "content-disposition" ).split( ";" ) ) {
            if ( content.trim().startsWith( "filename" ) )
                return content.substring( content.indexOf( "=" ) + 2, content.length() - 1 );
        }
        return "Default.file";
    }

}
//Exemple de formulaire pour utiliser la servlet 
/*
*<body style="text-align: center">
     
     <header>
         <h1>Veuillez choisir les images à uploader.</h1>
     </header>
     
     <form method="post" action="upload" enctype="multipart/form-data">
         Fichiers sélectionnés : 
         <input type="file" name="multiPartServlet" accept="image/*" multiple
                onchange="readFilesAndDisplayPreview(this.files);" /> <br/>
         <input type="submit" value="Upload" /> <br/>        
         
         <div id="list"></div>   
     </form>
 </body>
*/