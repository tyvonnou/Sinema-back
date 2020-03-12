package picture;

import java.io.File;

public class Picture {

  /**
   * Delete an image from the server.
   * @param location | Location of the image.
   * @throws Exception | If the image wasn't deleted.
   */
  public static void deleteImageFromServ(String location) throws Exception {
    
    File picture = new File(location);
    if (picture.delete()) {
      System.out.println(picture.getName() + " deleted");
    } else {
      throw new Exception();
    }
  }
}
