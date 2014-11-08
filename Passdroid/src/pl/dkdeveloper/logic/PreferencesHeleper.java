package pl.dkdeveloper.logic;

import pl.dkdeveloper.passdroid.PassdroidApplication;
import pl.dkdeveloper.passdroid.R;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHeleper {
	
	public static void setString(String key, String value)
	{
		Context context = PassdroidApplication.getAppContext();
		SharedPreferences sharedPref = context
				.getSharedPreferences(context.getString(R.string.passdroid_preferences)
						,Context.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(key,value);
		editor.commit();
	}
	
	public static String getString(String key)
	{
		Context context = PassdroidApplication.getAppContext();
		SharedPreferences sharedPref = context
				.getSharedPreferences(context.getString(R.string.passdroid_preferences)
						,Context.MODE_PRIVATE);
		String value = sharedPref.getString(key, "");
		return value;
	}
}
