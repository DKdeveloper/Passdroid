package pl.dkdeveloper.passdroid;

import java.util.List;

import pl.dkdeveloper.logic.LogicManager;
import pl.dkdeveloper.model.Category;
import pl.dkdeveloper.model.Password;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ArrayAdapterPassword extends ArrayAdapter<Password> {
	
	Context mContext;
    int layoutResourceId;
    List<Password> data = null;
    LayoutInflater mInflater;
    LogicManager manager;
    Category category = null;

	
	public ArrayAdapterPassword(Context mContext, int layoutResourceId, List<Password> data, Category category) {
		super(mContext, layoutResourceId, data);
		
		 this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 this.layoutResourceId = layoutResourceId;
         this.mContext = mContext;
         this.data = data;
         this.manager = new LogicManager();
         this.category = category;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final PasswordViewHolder viewHolder;
		
		if (convertView == null) {			
			convertView = mInflater.inflate(R.layout.password_list_item, parent, false);
			
			viewHolder = new PasswordViewHolder();
			viewHolder.tbName = (TextView)convertView.findViewById(R.id.tbName);
			viewHolder.tbLogin = (TextView)convertView.findViewById(R.id.tbLogin);
			viewHolder.tbPassword = (TextView)convertView.findViewById(R.id.tbPassword);
			viewHolder.btnDelete = (ImageButton) convertView.findViewById(R.id.btnDeletePassword);
			viewHolder.btnEdit = (ImageButton) convertView.findViewById(R.id.btnPasswordEdit);
			convertView.setTag(viewHolder);			
		}
		else
		{
			viewHolder = (PasswordViewHolder)convertView.getTag();
		}	
		
		viewHolder.btnDelete.setTag(position);
		viewHolder.btnDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!manager.isAuthenticated()) return;
									
				final int tag = (Integer) v.getTag();
				final String passName = data.get(tag).getName();
				
				new AlertDialog.Builder(mContext)
			    .setTitle("Jesteœ pewien?")
			    .setMessage("Czy chcesz usun¹æ has³o " + passName + "?")
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        	 data.remove(tag);
			                manager.getStore().removePasswordByName(category, passName);
			                try {
								manager.saveDatabase(manager.getStore(), PassdroidApplication.getPassword());
							} catch (Exception e) {
								e.printStackTrace();
							}
			                notifyDataSetChanged();
			        }
			     })
			    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        	dialog.cancel();
			        }
			     })
			    .setIcon(android.R.drawable.ic_dialog_alert)
			     .show();
			}
		});
		
		viewHolder.btnEdit.setTag(position);
		viewHolder.btnEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!manager.isAuthenticated()) return;
				
				final int tag = (Integer) v.getTag();
                final String passwordName = data.get(tag).getName();
                final String passwordLogin = data.get(tag).getLogin();
                final String passwordPass = data.get(tag).getPassword();
                
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        		alertDialog.setTitle("Edycja has³a");
        		final LinearLayout ll = new LinearLayout(mContext);		
        		
        		final EditText inputName = new EditText(mContext);
        		final EditText inputLogin = new EditText(mContext);
        		final EditText inputPass = new EditText(mContext);
        		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
        				LinearLayout.LayoutParams.MATCH_PARENT,
        				LinearLayout.LayoutParams.MATCH_PARENT,
        				LinearLayout.VERTICAL);
        		
        		inputName.setLayoutParams(lp);
        		inputName.setHint("Nazwa has³a");
        		inputName.setText(passwordName);
        		inputLogin.setLayoutParams(lp);
        		inputLogin.setHint("Login");
        		inputLogin.setText(passwordLogin);
        		inputPass.setLayoutParams(lp);
        		inputPass.setHint("Has³o");
        		inputPass.setText(passwordPass);
        		
        		ll.setOrientation(LinearLayout.VERTICAL);
        		ll.addView(inputName);
        		ll.addView(inputLogin);
        		ll.addView(inputPass);

        		alertDialog.setView(ll);		
        		
        		// Setting Positive "Yes" Button
        		alertDialog.setPositiveButton("Zapisz",
        				new DialogInterface.OnClickListener() {
        					public void onClick(DialogInterface dialog, int which) {
        						
        						String editName = inputName.getText().toString();
        						String editLogin = inputLogin.getText().toString();
        						String editPass = inputPass.getText().toString();
        						
        						Password newPassword = new Password(editName, editLogin, editPass);
        						
        						//TODO: Validation for Category Name
        						manager.getStore().updatePasswordByName(category, passwordName, newPassword);
        						try {
        							manager.saveDatabase(manager.getStore(),PassdroidApplication.getPassword());
        						} catch (Exception e) {
        							e.printStackTrace();
        							return;
        						}
        						notifyDataSetChanged();
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
        		alertDialog.show();
				
			}
		});
		
		viewHolder.tbPassword.setOnTouchListener(new OnTouchListener() {	
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Log.d("YOLO", "OnTouchListener");
				if(!manager.isAuthenticated()) return false;
				
				viewHolder.tbPassword.setVisibility(View.VISIBLE);
				
				return false;
			}
		});
		
		viewHolder.tbName.setText(data.get(position).getName());
		viewHolder.tbLogin.setText(data.get(position).getLogin());
		viewHolder.tbPassword.setText(data.get(position).getPassword());
		
		return convertView;
	}
}
