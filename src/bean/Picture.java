package bean;

public class Picture {

		Integer idFilm;
		String location;
		
		public Picture() {
			this.idFilm = null;
			this.location = null;
		}
		
		public Picture(Integer idFilm, String location) {
			this.idFilm = idFilm;
			this.location = location;
		}

		public Integer getIdFilm() {
			return idFilm;
		}

		public void setIdFilm(Integer idFilm) {
			this.idFilm = idFilm;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}
		
}
