package pl.dkdeveloper.passdroid;

import pl.dkdeveloper.logic.LogicManager;
import pl.dkdeveloper.model.Category;
import pl.dkdeveloper.model.Password;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

public class PasswordActivity extends Activity {

	ListView lvPasswords;
	LogicManager manager;
	ArrayAdapterPassword adapter;
	Category category;
	
	public static String EXTRA_INTENT_ELEMENT_PASSWORD = "CATEGORY";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password);
		
		Intent intent = getIntent();
		String sCategory = intent.getStringExtra(EXTRA_INTENT_ELEMENT_PASSWORD);		
		
		manager = new LogicManager();
		
		category = manager.getStore().getCategoryByName(sCategory);
		lvPasswords = (ListView) findViewById(R.id.lvPasswords);		

		adapter = new ArrayAdapterPassword(this,
				R.layout.password_list_item, manager.getPasswordByCategory(sCategory), category);	
		
		if(!PassdroidApplication.isEditMode())
			((Button) findViewById(R.id.btnAddPassword)).setVisibility(View.GONE);
		
		Log.d("YOLO", "Put Intent: "+sCategory);

		// create a new ListView, set the adapter and item click listener
		lvPasswords.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.password, menu);
		MenuItem item = menu.findItem(R.id.action_toggleEditModePassword);
		item.setTitle(PassdroidApplication.isEditMode() ? "Tryb odczytu" : "Tryb edycji");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_toggleEditModePassword:
			PassdroidApplication.setEditMode(!PassdroidApplication.isEditMode());
			finish();
			startActivity(getIntent());
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void btnAddPassword_onClick(View view) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("Nowe has³o");
		final LinearLayout ll = new LinearLayout(this);		
		
		final EditText inputName = new EditText(this);
		final EditText inputLogin = new EditText(this);
		final EditText inputPass = new EditText(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.VERTICAL);
		
		inputName.setLayoutParams(lp);
		inputName.setHint("Nazwa has³a");
		inputLogin.setLayoutParams(lp);
		inputLogin.setHint("Login");
		inputPass.setLayoutParams(lp);
		inputPass.setHint("Has³o");
		
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.addView(inputName);
		ll.addView(inputLogin);
		ll.addView(inputPass);

		alertDialog.setView(ll);
		
		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton("Zapisz",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String sName = inputName.getText().toString();
						String sLogin = inputLogin.getText().toString();
						String sPass = inputPass.getText().toString();
						
						Password p = new Password(sName, sLogin, sPass);
						adapter.add(p);
						//category.getPasswords().add(p);
						manager.getStore().UpdateCategory(category);
						try {
							manager.saveDatabase(manager.getStore(), PassdroidApplication.getPassword());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
