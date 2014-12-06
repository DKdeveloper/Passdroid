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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


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
		
		if(!PassdroidApplication.isEditMode())
			((Button) findViewById(R.id.btnAddCategory)).setVisibility(View.GONE);
		
		// create a new ListView, set the adapter and item click listener
		lvCategory.setAdapter(adapter);	
	}		
		
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.category, menu);
		MenuItem item = menu.findItem(R.id.action_toggleEditModeCategory);
		item.setTitle(PassdroidApplication.isEditMode() ? "Tryb odczytu" : "Tryb edycji");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_toggleEditModeCategory:
			PassdroidApplication.setEditMode(!PassdroidApplication.isEditMode());
			finish();
			startActivity(getIntent());
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
						if (!manager.getStore().checkCategoryExist(category)) {
							Category c = new Category(category, manager.InitExamplePasswords());
							adapter.add(c);
							manager.getStore().addCategory(c);
							try {
								manager.saveDatabase(manager.getStore(), PassdroidApplication.getPassword());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else {
							Toast.makeText(CategoryActivity.this, "Kategoria o podanej nazwie ju¿ istnieje", Toast.LENGTH_LONG).show();
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
	
public void btnAddPassword_onClick(View view) {
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("Nowe has³o");
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
						Category c = new Category(category, manager.InitExamplePasswords());
						adapter.add(c);
						manager.getStore().addCategory(c);
						try {
							manager.saveDatabase(manager.getStore(), PassdroidApplication.getPassword());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		
		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("Cancel",
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
