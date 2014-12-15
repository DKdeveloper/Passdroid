package pl.dkdeveloper.passdroid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.dkdeveloper.logic.LogicManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.TextView;

public class NewDatabaseActivity extends Activity {

	LogicManager manager;
	TextView tbPassword;
	FrameLayout frameLayout;
	List<Integer> listPoint;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_database);
		
		manager = new LogicManager();
		tbPassword = (TextView)findViewById(R.id.tbNewPassword);
		frameLayout = (FrameLayout) findViewById(R.id.frameNewPassword);
		
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
	
	public void btnSaveNewDb_onClick(View view) {
		String path = getFilesDir().getPath()+ "/" + getString(R.string.passdroid_db_name);
		String fakePath = getFilesDir().getPath()+ "/" + getString(R.string.passdroid_fake_db_name);
		manager.setFakeDatabasePath(fakePath);
		try {
			manager.createDatabase(path,tbPassword.getText().toString());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		Intent intent = new Intent(this, MainActivity.class);		
		startActivity(intent);
		finish();
	 }
}
