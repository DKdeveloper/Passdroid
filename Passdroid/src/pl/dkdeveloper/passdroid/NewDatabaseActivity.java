package pl.dkdeveloper.passdroid;

import pl.dkdeveloper.logic.LogicManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

public class NewDatabaseActivity extends Activity {

	LogicManager manager;
	TextView tbPassword;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_database);
		
		manager = new LogicManager();
		tbPassword = (TextView)findViewById(R.id.tbNewPassword);
	}
	
	public void btnSaveNewDb_onClick(View view) {
		String path = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DOWNLOADS).getPath()
		+ "/" + getString(R.string.passdroid_db_name);
		
		manager.createDatabase(path,tbPassword.getText().toString());
		
		Intent intent = new Intent(this, MainActivity.class);		
		startActivity(intent);
	 }
}
