package pl.dkdeveloper.passdroid;

import pl.dkdeveloper.logic.LogicManager;
import pl.dkdeveloper.model.Category;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;


public class CategoryActivity extends Activity {

	ListView lvCategory;
	LogicManager manager;
	ArrayAdapterCategory adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);

		manager = new LogicManager();
		lvCategory = (ListView) findViewById(R.id.lvCategory);		

		adapter = new ArrayAdapterCategory(this,
				R.layout.category_list_item, manager.getCategoryList());		

		// create a new ListView, set the adapter and item click listener
		lvCategory.setAdapter(adapter);
		
		lvCategory.setOnItemClickListener(new OnItemClickListener() {
			@Override
			   public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
					Intent intent = new Intent(CategoryActivity.this, PasswordActivity.class);
					startActivity(intent);
			   } 
		});		
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
		alertDialog.setPositiveButton("Zapisz",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String category = input.getText().toString();
						Category c = new Category(category, manager.InitExamplePasswords());
						adapter.add(c);
						manager.getStore().addCategory(c);
						try {
							manager.saveDatabase(manager.getStore(),PassdroidApplication.getPassword());
						} catch (Exception e) {
							e.printStackTrace();
							return;
						}
					}
				});
		
		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("Anuluj",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Log.d("CATEGORY", "dialog.cancel()");
						dialog.cancel();
					}
				});

		// closed

		// Showing Alert Message
		alertDialog.show();

	}
}
