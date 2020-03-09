package bean;

public class Livre {
	
	Integer idLivre = null;
	
	String titre = null;
	String auteur = null;
	Integer annee = null;
	
	public Livre() {
		
	}

	public Livre(String titre,
			String auteur,
			Integer annee) {
		this.titre = titre;
		this.auteur = auteur;
		this.annee = annee;
	}

	public Integer getIdLivre() {
		return idLivre;
	}
	public void setIdLivre(Integer idLivre) {
		this.idLivre = idLivre;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getAuteur() {
		return auteur;
	}
	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}
	public Integer getAnnee() {
		return annee;
	}
	public void setAnnee(Integer annee) {
		this.annee = annee;
	}
	
	
	
}