package bean;

import java.sql.Date;

public class Film {
	
	Integer id;
	String title;
	String description;
	Date releaseDate;
	
	public Film() {
		this.id = null;
		this.title = null;
		this.description = null;
		this.releaseDate = null;
	}

	public Film(String title, String description, Date releaseDate) {
		this.title = title;
		this.description = description;
		this.releaseDate = releaseDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	
}