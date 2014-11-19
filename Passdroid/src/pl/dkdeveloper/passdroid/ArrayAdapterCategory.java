package pl.dkdeveloper.passdroid;

import java.util.List;

import pl.dkdeveloper.logic.LogicManager;
import pl.dkdeveloper.model.Category;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ArrayAdapterCategory extends ArrayAdapter<Category> {
	
	Context mContext;
    int layoutResourceId;
    List<Category> data = null;
    LayoutInflater mInflater;
    LogicManager manager;

	
	public ArrayAdapterCategory(Context mContext, int layoutResourceId, List<Category> data) {
		super(mContext, layoutResourceId, data);
		
		 this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 this.layoutResourceId = layoutResourceId;
         this.mContext = mContext;
         this.data = data;
         this.manager = new LogicManager();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CategoryViewHolder viewHolder;
		
		if (convertView == null) {			
			convertView = mInflater.inflate(R.layout.category_list_item, parent, false);
			
			viewHolder = new CategoryViewHolder();			
			viewHolder.tbCategoryName = (TextView)convertView.findViewById(R.id.tbCategoryName);
			viewHolder.btnDelete = (ImageButton) convertView.findViewById(R.id.btnDeleteCategory);
			viewHolder.btnEdit = (ImageButton) convertView.findViewById(R.id.btnCategoryEdit);
			convertView.setTag(viewHolder);			
		}
		else
		{
			viewHolder = (CategoryViewHolder)convertView.getTag();
		}	
		
		viewHolder.btnDelete.setTag(position);
		viewHolder.btnDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!manager.isAuthenticated()) return;
									
				final int tag = (Integer) v.getTag();
				final String categoryName = data.get(tag).getCategoryName();
				
				new AlertDialog.Builder(mContext)
			    .setTitle("Jesteś pewien?")
			    .setMessage("Czy chcesz usunąć kategorię " + categoryName + "?")
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        	 data.remove(tag);
			                manager.getStore().removeCategoryByName(categoryName);
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
                final String categoryName = data.get(tag).getCategoryName();
                
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        		alertDialog.setTitle("Nazwa kategorii");
        		final EditText input = new EditText(mContext);
        		input.setText(categoryName);
        		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
        				LinearLayout.LayoutParams.MATCH_PARENT,
        				LinearLayout.LayoutParams.MATCH_PARENT);
        		input.setLayoutParams(lp);
        		alertDialog.setView(input);		
        		
        		// Setting Positive "Yes" Button
        		alertDialog.setPositiveButton("Zapisz",
        				new DialogInterface.OnClickListener() {
        					public void onClick(DialogInterface dialog, int which) {
        						
        						String newCategoryName = input.getText().toString();
        						//TODO: Validation for Category Name
        						manager.getStore().updateCategoryWithNewName(categoryName,newCategoryName);
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
		
		//viewHolder.tbCategoryName.setText(manager.getCategoryList().get(position).getCategoryName());
		viewHolder.tbCategoryName.setText(data.get(position).getCategoryName());
		viewHolder.tbCategoryName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, PasswordActivity.class);
				mContext.startActivity(intent);
			}
		});	
		return convertView;
	}
}
