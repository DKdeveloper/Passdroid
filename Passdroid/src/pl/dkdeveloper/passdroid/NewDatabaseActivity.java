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
		String path = getFilesDir().getPath()+ "/" + getString(R.string.passdroid_db_name);
		String fakePath = getFilesDir().getPath()+ "/" + getString(R.string.passdroid_fake_db_name);
		manager.setFakeDatabasePath(fakePath);
		try {
			manager.createDatabase(path,tbPassword.getText().toString());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		Intent intent = new Intent(this, MainActivity.class);		
		startActivity(intent);
		finish();
	 }
}
