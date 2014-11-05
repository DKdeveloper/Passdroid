package pl.dkdeveloper.passdroid;

import pl.dkdeveloper.logic.LogicManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class CategoryActivity extends Activity {

	ListView lvCategory;
	LogicManager manager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		
		manager = new LogicManager();
		lvCategory = (ListView)findViewById(R.id.lvCategory);
		
		// our adapter instance

        ArrayAdapterCategory adapter = new ArrayAdapterCategory(this, R.layout.category_list_item, manager.getCategoryList());

        // create a new ListView, set the adapter and item click listener
        lvCategory.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.category, menu);
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
