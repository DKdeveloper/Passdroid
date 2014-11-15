package pl.dkdeveloper.passdroid;

import java.util.List;

import pl.dkdeveloper.logic.LogicManager;
import pl.dkdeveloper.model.Category;
import pl.dkdeveloper.model.Password;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArrayAdapterPassword extends ArrayAdapter<Password> {
	
	Context mContext;
    int layoutResourceId;
    List<Password> data = null;
    LayoutInflater mInflater;
    LogicManager manager;

	
	public ArrayAdapterPassword(Context mContext, int layoutResourceId, List<Password> data) {
		super(mContext, layoutResourceId, data);
		
		 this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 this.layoutResourceId = layoutResourceId;
         this.mContext = mContext;
         this.data = data;
         this.manager = new LogicManager();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PasswordViewHolder viewHolder;
		
		if (convertView == null) {			
			convertView = mInflater.inflate(R.layout.password_list_item, parent, false);
			
			viewHolder = new PasswordViewHolder();
			viewHolder.tbName = (TextView)convertView.findViewById(R.id.tbName);
			viewHolder.tbLogin = (TextView)convertView.findViewById(R.id.tbLogin);
			viewHolder.tbPassword = (TextView)convertView.findViewById(R.id.tbPassword);
			convertView.setTag(viewHolder);			
		}
		else
		{
			viewHolder = (PasswordViewHolder)convertView.getTag();
		}	
		
		viewHolder.tbName.setText(data.get(position).getName());
		viewHolder.tbLogin.setText(data.get(position).getLogin());
		viewHolder.tbPassword.setText(data.get(position).getPassword());
		
		return convertView;
	}
}
