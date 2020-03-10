package bean;

public class User {
	
	Integer id;
	String username;
	String password;
	Boolean isAdmin;
	
	public User() {
		this.id = null;
		this.username = null;
		this.password = null;
		this.isAdmin = null;
	}

	public User(String username, String password, Boolean isAdmin) {
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
	}	
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.isAdmin = false;
	}	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}