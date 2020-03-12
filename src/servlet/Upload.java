package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import base.Base;
import base.MovieDtb;

/*
 * Notre serlvet permettant de récupérer les fichiers côté serveur. Elle répondra à l'URL /upload
 * dans l'application Web considérée.
 */
@WebServlet(urlPatterns = "/Upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 5 * 5)
public class Upload extends HttpServlet {

  private static final long serialVersionUID = 1273074928096412095L;

  /*
   * Chemin dans lequel les images seront sauvegardées.
   */
  public static final String IMAGES_FOLDER = "WEB-INF/images";

  public String uploadPath;

  /*
   * Récupération et sauvegarde du contenu de chaque image.
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse resp)
      throws ServletException, IOException {
    ResourceBundle rb = ResourceBundle.getBundle("config/config");
//    Base base = new Base();
    uploadPath = request.getServletContext().getRealPath(IMAGES_FOLDER);
    File uploadDir = new File(uploadPath);
    if (!uploadDir.exists())
      uploadDir.mkdir();
    for (Part part : request.getParts()) {
      // Stockage serveur
      String fileName = getFileName(part);
      
      // TODO: Pop into the database
      part.getContentType();
      Path dst = Path.of(rb.getString("bucket"), fileName);
      part.write(dst.toString());
//      Files.move(src, dst);
      // Stockage MySQL
      // FilmBdd filmbdd = new FilmBdd();
      // Connection connection = base.getConnection();
      // filmbdd.ajouterImage(Integer.parseInt(request.getParameter("FilmID")), fullPath,
      // connection);
    }
  }

  /*
   * Récupération du nom du fichier dans la requête.
   */
  private String getFileName(Part part) {
    for (String content : part.getHeader("content-disposition").split(";")) {
      if (content.trim().startsWith("filename"))
        return content.substring(content.indexOf("=") + 2, content.length() - 1);
    }
    return "Default.file";
  }

}
// Exemple de formulaire pour utiliser la servlet
/*
 * <body style="text-align: center">
 * 
 * <header> <h1>Veuillez choisir les images à uploader.</h1> </header>
 * 
 * <form method="post" action="upload" enctype="multipart/form-data"> Fichiers sélectionnés : <input
 * type="file" name="multiPartServlet" accept="image/*" multiple
 * onchange="readFilesAndDisplayPreview(this.files);" /> <br/> <input type="submit" value="Upload"
 * /> <br/>
 * 
 * <div id="list"></div> </form> </body>
 */
