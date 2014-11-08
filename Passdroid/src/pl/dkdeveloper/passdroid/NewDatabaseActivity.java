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
	TextView tbDbName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_database);
		
		manager = new LogicManager();
		tbDbName = (TextView)findViewById(R.id.tbNewDbName);
	}
	
	public void btnSaveNewDb_onClick(View view) {
		String path = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DOWNLOADS).getPath()
		+ "/" + tbDbName.getText() + ".txt";
		
		manager.createDatabase(path);
		
		Intent intent = new Intent(this, MainActivity.class);		
		startActivity(intent);
	 }
}
