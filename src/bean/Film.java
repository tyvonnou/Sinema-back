package bean;

import java.sql.Date;

public class Film {

  public Integer id;
  public String title;
  public String description;
  public Date releaseDate;

  public Film(String title, String description, Date releaseDate) {
    this.id = null;
    this.title = title;
    this.description = description;
    this.releaseDate = releaseDate;
  }

}
