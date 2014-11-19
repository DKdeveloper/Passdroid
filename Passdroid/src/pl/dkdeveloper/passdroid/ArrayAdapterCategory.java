package pl.dkdeveloper.passdroid;

import java.util.List;

import pl.dkdeveloper.logic.LogicManager;
import pl.dkdeveloper.model.Category;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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
									
				int tag = (Integer) v.getTag();
				String categoryName = data.get(tag).getCategoryName();
                data.remove(tag);
                manager.getStore().removeCategoryByName(categoryName);
                try {
					manager.saveDatabase(manager.getStore(), PassdroidApplication.getPassword());
				} catch (Exception e) {
					e.printStackTrace();
				}
                notifyDataSetChanged();
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
