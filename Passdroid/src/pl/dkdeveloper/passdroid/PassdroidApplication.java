package pl.dkdeveloper.passdroid;

import android.app.Application;
import android.content.Context;

public class PassdroidApplication extends Application{

    private static Context context;

    public void onCreate(){
        super.onCreate();
        PassdroidApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return PassdroidApplication.context;
    }
}