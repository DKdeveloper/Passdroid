package pl.dkdeveloper.passdroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogsHelper {
	
	public static void showErorDialog(Context context,String title, String message)
	{
		new AlertDialog.Builder(context)
	    .setTitle(title)
	    .setMessage(message)
	    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	dialog.cancel();
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	    .show();
	}

}
