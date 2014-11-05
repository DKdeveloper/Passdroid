package pl.dkdeveloper.passdroid;

import java.util.List;

import pl.dkdeveloper.logic.LogicManager;
import pl.dkdeveloper.model.Category;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
			convertView.setTag(viewHolder);			
		}
		else
		{
			viewHolder = (CategoryViewHolder)convertView.getTag();
		}	
		
		viewHolder.tbCategoryName.setText(manager.getCategoryList().get(position).getCategoryName());
		return convertView;
	}
}
