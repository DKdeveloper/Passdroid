package pl.dkdeveloper.passdroid;

import pl.dkdeveloper.logic.LogicManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class PasswordActivity extends Activity {

	ListView lvPasswords;
	LogicManager manager;
	ArrayAdapterPassword adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password);
		
		manager = new LogicManager();
		lvPasswords = (ListView) findViewById(R.id.lvPasswords);		

		adapter = new ArrayAdapterPassword(this,
				R.layout.password_list_item, manager.getPasswordByCategory("Work"));	// TODO przekazywanie w intencie nazwy kategorii	

		// create a new ListView, set the adapter and item click listener
		lvPasswords.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.password, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
