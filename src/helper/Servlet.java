package helper;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Servlet {

  public static void formatResponse(HttpServletResponse response, Object responseValue,
      String contentType) {
    response.setContentType(contentType);
    try {
      JsonGenerator generator = new JsonFactory().createGenerator(response.getOutputStream());
      generator.setCodec(new ObjectMapper());
      generator.close();
      generator.writeObject(responseValue);
    } catch (IOException e) {
    }
  }
}
