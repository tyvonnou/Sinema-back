package bean;

public class Picture {

  Integer id;
  String location;
  String contentType;
  Integer idFilm;

  public Picture(String location, String contentType, Integer idFilm) {
    this.location = location;
    this.contentType = contentType;
    this.idFilm = idFilm;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public Integer getIdFilm() {
    return idFilm;
  }

  public void setIdFilm(Integer idFilm) {
    this.idFilm = idFilm;
  }
  
}
