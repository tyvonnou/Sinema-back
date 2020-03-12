package helper;

import java.io.File;
import javax.servlet.http.Part;

public class Image {

  // TODO: Write the method
  /**
   * Add an image from the server.
   * 
   * @param image | Part of the image.
   */
  public static void addImageOnServ(Part image) {}

  /**
   * Delete an image from the server.
   * 
   * @param location | Location of the image.
   */
  public static void deleteImageFromServ(String location) {
    File picture = new File(location);
    picture.delete();
  }
}
