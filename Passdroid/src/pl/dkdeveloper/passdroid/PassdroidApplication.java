package pl.dkdeveloper.passdroid;

import pl.dkdeveloper.model.Store;
import android.app.Application;
import android.content.Context;

public class PassdroidApplication extends Application{

    private static Context context;
    private static Store store = null;
    private static String password = null;
    private static boolean editMode = false;

    public void onCreate(){
        super.onCreate();
        PassdroidApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return PassdroidApplication.context;
    }

	public static Store getStore() {
		return store;
	}

	public static void setStore(Store store) {
		PassdroidApplication.store = store;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		PassdroidApplication.password = password;
	}

	public static boolean isEditMode() {
		return editMode;
	}

	public static void setEditMode(boolean editMode) {
		PassdroidApplication.editMode = editMode;
	}
}