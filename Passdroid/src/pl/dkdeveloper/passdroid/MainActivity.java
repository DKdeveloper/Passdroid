package pl.dkdeveloper.passdroid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import pl.dkdeveloper.logic.AuthenticateManager;
import pl.dkdeveloper.logic.AuthenticationResult;
import pl.dkdeveloper.logic.FakeStore;
import pl.dkdeveloper.logic.LogicManager;
import pl.dkdeveloper.logic.LoginResultEnum;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView tbPassword;
	Button btnLogin;
	LogicManager manager;
	FrameLayout frameLayout;
	Boolean ifPressed = false;
	List<Integer> listPoint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		manager = new LogicManager();
		tbPassword = (TextView) findViewById(R.id.tbPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		frameLayout = (FrameLayout) findViewById(R.id.framePassword);

		if (!manager.databaseExist()) {
			tbPassword.setVisibility(View.GONE);
			btnLogin.setVisibility(View.GONE);
		}

		//this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		
		frameLayout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {		
				if (listPoint == null || listPoint.isEmpty()) {
					listPoint = new ArrayList<Integer>();
				}
				listPoint.add(event.getPointerCount());

		         if ( event.getAction() == MotionEvent.ACTION_UP) {
		        	 tbPassword.append(String.valueOf(Collections.max(listPoint)));
		        	 listPoint = new ArrayList<Integer>();
		        	 return false;
		         }
		         
		         return true;		            
			}			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		if (id == R.id.action_set_alarm_password) {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

			// Setting Dialog Title
			alertDialog.setTitle("Has�o alarmowe");
			final EditText input = new EditText(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
			input.setLayoutParams(lp);
			alertDialog.setView(input);

			// Ustawienia przycisku Zapisz
			alertDialog.setPositiveButton("Zapisz",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							String fakePassword = input.getText().toString();
							manager.setFakePassword(fakePassword);
							Log.d("FAKE_PASSWORD", fakePassword);
						}
					});

			// Ustawienia przycisku Anuluj
			alertDialog.setNegativeButton("Anuluj",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Log.d("FAKE_PASSWORD", "dialog.cancel()");
							dialog.cancel();
						}
					});

			alertDialog.show();
		}

		return super.onOptionsItemSelected(item);
	}

	public void btnLogin_onClick(View view) {

		if (tbPassword.getText().length() > 0) {
			AuthenticateManager authManager = new AuthenticateManager();
			//Uwierzytelnianie
			AuthenticationResult result = authManager.Authenticate(tbPassword
					.getText().toString());

			if (result.IsSuccess
					&& result.LoginResult == LoginResultEnum.SuccesS) {
				//Jesli zalogowano poprawnie przejscie do kategorii
				Intent intent = new Intent(this, CategoryActivity.class);
				startActivity(intent);
				finish();
			} else if (result.IsSuccess
					&& result.LoginResult == LoginResultEnum.FakePassword) {
				// jesli haslo alarmowowe zaladowane falszywej bazy danych
				String path = manager.getDatabasePath();
				if (path == null || path.isEmpty()) {
					manager.setStore(FakeStore.getFakeStore());
				} else {
					manager.setStore(manager.loadFakeDb());
				}	
				Intent intent = new Intent(this, CategoryActivity.class);
				startActivity(intent);
				finish();
			}
			else {
				Toast.makeText(this, "Złe hasło!", Toast.LENGTH_LONG).show();				
			}
		} else {
			Toast.makeText(this, "Wprowadź hasło!", Toast.LENGTH_LONG).show();
		}
	}

	public void btnNewDb_OnClick(View view) {
		Intent intent = new Intent(this, NewDatabaseActivity.class);
		startActivity(intent);
	}
	
}
