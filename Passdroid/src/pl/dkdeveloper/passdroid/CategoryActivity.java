package pl.dkdeveloper.passdroid;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import pl.dkdeveloper.logic.LogicManager;
import pl.dkdeveloper.model.Category;
import pl.dkdeveloper.model.Store;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryActivity extends Activity {

	ListView lvCategory;
	LogicManager manager;
	private Store store;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);

		manager = new LogicManager();
		lvCategory = (ListView) findViewById(R.id.lvCategory);

		// our adapter instance
		try {
			
			store = manager.getStore();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayAdapterCategory adapter = new ArrayAdapterCategory(this,
				R.layout.category_list_item, store.getCategories());

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
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void btnAddCategory_onClick(View view) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("Category name");
		final EditText input = new EditText(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		input.setLayoutParams(lp);
		alertDialog.setView(input);

		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton("Save",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String category = input.getText().toString();
						Store store = manager.getStore();
						store.getCategories().add(new Category(category));
						finish();
						startActivity(getIntent());
					}
				});
		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		// closed

		// Showing Alert Message
		alertDialog.show();

	}
}
