package bean;

import java.sql.Date;

public class Film {
	
	Integer idFilm;
	String name;
	Date dateRelease;
	String description;
	
	public Film() {
		this.idFilm = null;
		this.name = null;
		this.dateRelease = null;
		this.description = null;
	}

	public Film(String name, Date dateRelease, String description) {
		this.name = name;
		this.dateRelease = dateRelease;
		this.description = description;
	}

	public Integer getIdFilm() {
		return idFilm;
	}

	public void setIdFilm(Integer idFilm) {
		this.idFilm = idFilm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateRelease() {
		return dateRelease;
	}

	public void setDateRelease(Date dateRelease) {
		this.dateRelease = dateRelease;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}