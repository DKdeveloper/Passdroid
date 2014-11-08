package pl.dkdeveloper.passdroid;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import pl.dkdeveloper.logic.AuthenticateManager;
import pl.dkdeveloper.logic.AuthenticationResult;
import pl.dkdeveloper.logic.LogicManager;
import pl.dkdeveloper.logic.LoginResultEnum;
import pl.dkdeveloper.model.Store;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView tbPassword;
	Button btnLogin;
	LogicManager manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		manager = new LogicManager();
		tbPassword = (TextView)findViewById(R.id.tbPassword);
		btnLogin = (Button)findViewById(R.id.btnLogin);
		
		if(!manager.databaseExist())
		{
			tbPassword.setVisibility(0);
			btnLogin.setVisibility(0);
		}
		
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void btnLogin_onClick(View view) {
		
		if (tbPassword.getText().length() > 0) {
			AuthenticateManager manager = new AuthenticateManager();
			AuthenticationResult result = manager.Authenticate(tbPassword.getText().toString());
			
			if(result.IsSuccess && result.LoginResult == LoginResultEnum.SuccesS) {
				// we can put message as reult in intent
				Intent intent = new Intent(this, CategoryActivity.class);		
				startActivity(intent);
			}
			else if (result.IsSuccess && result.LoginResult == LoginResultEnum.FakePassword) {
				// open fake category activity or put message in intent
			}
		}
		else {
			Toast.makeText(this, "Wrong password!", Toast.LENGTH_LONG);
		}
	}
	
	public void btnNewDb_OnClick(View view) {
		Intent intent = new Intent(this, NewDatabaseActivity.class);		
		startActivity(intent);
	 }
}

