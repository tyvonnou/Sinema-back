package manager;

import javax.servlet.http.HttpServletRequest;

public class Manager {

  boolean logged;
  String identity;
  
  public Manager() {
    this.logged = false;
    this.identity = null;
  }


  public static Manager init(HttpServletRequest request) {
    Manager manager = (Manager) request.getSession().getAttribute("manager");
    if (manager == null) {
      manager = new Manager();
      request.getSession().setAttribute("manager", manager);
    }
    return manager;
  }

  public boolean isLogged() {
    return logged;
  }

  public void setLogged(boolean logged) {
    this.logged = logged;
  }

  public String getIdentity() {
    return identity;
  }

  public void setIdentity(String identity) {
    this.identity = identity;
  }

}
