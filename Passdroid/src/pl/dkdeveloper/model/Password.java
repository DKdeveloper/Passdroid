package pl.dkdeveloper.model;

import org.simpleframework.xml.Default;

@Default
public class Password {

	public Password() {
	}
	
	public Password(String name, String password) {
		Name = name;
		Password = password;
	}
	
	public Password(String name, String login, String password) {
		Name = name;
		Login = login;
		Password = password;		
	}
		
	private String Name;
	private String Login = "";
	private String Password;	
	
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	
	public String getLogin() {
		return Login;
	}

	public void setLogin(String login) {
		Login = login;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}


}
