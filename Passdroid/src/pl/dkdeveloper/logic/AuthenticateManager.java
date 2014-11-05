package pl.dkdeveloper.logic;

public class AuthenticateManager {

	public AuthenticateManager() {
		// TODO Auto-generated constructor stub
	}
	
	public AuthenticationResult Authenticate(String password) {
		
		AuthenticationResult result = new AuthenticationResult();
		if(!password.isEmpty() && password.equals("123") ) {
			result.LoginResult = LoginResultEnum.SuccesS;
		}
		
		return result;
	}

}
