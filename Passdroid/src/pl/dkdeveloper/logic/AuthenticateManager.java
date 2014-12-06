package pl.dkdeveloper.logic;

import pl.dkdeveloper.passdroid.PassdroidApplication;

public class AuthenticateManager {

	public AuthenticateManager() {
		// TODO Auto-generated constructor stub
	}

	public AuthenticationResult Authenticate(String password) {
		LogicManager manager = new LogicManager();
		AuthenticationResult result = new AuthenticationResult();
		if (password.equals(manager.getFakePassword())) {
			result.LoginResult = LoginResultEnum.FakePassword;	
			PassdroidApplication.setPassword("");
		} else {
			try {
				manager.loadDatabase(password);
				PassdroidApplication.setPassword(password);
				result.LoginResult = LoginResultEnum.SuccesS;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

}
