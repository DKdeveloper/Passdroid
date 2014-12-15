package pl.dkdeveloper.logic;

import pl.dkdeveloper.passdroid.PassdroidApplication;

public class AuthenticateManager {

	public AuthenticateManager() {
	}

	//Metoda uwierzytelniajaca uzytkownika
	public AuthenticationResult Authenticate(String password) {
		LogicManager manager = new LogicManager();
		AuthenticationResult result = new AuthenticationResult();
		//Jesli haslo jest alarmowe zwracamy o tym informacje 
		if (password.equals(manager.getFakePassword())) {
			result.LoginResult = LoginResultEnum.FakePassword;	
			PassdroidApplication.setPassword("");
		} else {
			try {
				//Odszyfrowanie i zaladowanie bazy danych do pamieci
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
