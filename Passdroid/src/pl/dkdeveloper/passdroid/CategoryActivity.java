package pl.dkdeveloper.passdroid;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import pl.dkdeveloper.logic.LogicManager;
import pl.dkdeveloper.model.Store;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
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
		lvCategory = (ListView) findViewById(R.id.lvCategory);

		// our adapter instance

		ArrayAdapterCategory adapter = new ArrayAdapterCategory(this,
				R.layout.category_list_item, manager.getDefaultCategoryList());

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
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		case R.id.action_save:
			Serializer serializer = new Persister();
			Store store = new Store();
			store.setCategories(manager.getDefaultCategoryList());
			File xmlFile = new File(Environment
					.getExternalStoragePublicDirectory(
							Environment.DIRECTORY_DOWNLOADS).getPath()
					+ "/DB.txt");
			try {
				serializer.write(store, xmlFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}
