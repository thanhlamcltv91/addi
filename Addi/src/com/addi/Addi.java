package com.addi;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ThreadGroup;


import com.addi.R;
import com.addi.R.id;
import com.addi.R.layout;
import com.addi.core.interpreter.*;

import android.app.Activity;
import android.content.Intent; 
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Addi extends Activity {
	
   private ArrayAdapter<String> _mOutArrayAdapter;
   private ListView _mOutView;
   private EditText _mCmdEditText;
   private Button _mRunButton;
   private Interpreter _interpreter;
   private String _mResults = "";
   private String _prevCmd = "";	
   private boolean _blockExecute = false;
   private String _command;
   private Activity _act;
   private SubMenu _sumAboutAddi;

   // Need handler for callbacks to the UI thread
   public final Handler _mHandler = new Handler() {
	    public void handleMessage(Message msg) {
	    	_mOutArrayAdapter.add(msg.getData().getString("text"));
	    };
   };
   
   // Create runnable for posting
   final Runnable mUpdateResults = new Runnable() {
       public void run() {
           updateResultsInUi();
       }
   };
   
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
	   /*support multi language pack (Auto select) */
	   Resources res = getResources();
	   Configuration conf =res.getConfiguration();
	   DisplayMetrics dm=res.getDisplayMetrics();
	   res.updateConfiguration(conf, dm);
	   
	   super.onCreate(savedInstanceState);
       setContentView(R.layout.main);
       
       _interpreter = new Interpreter(true);
       
       _mOutView = (ListView)findViewById(R.id.out);
       _mCmdEditText = (EditText)findViewById(R.id.edit_command);
       _mRunButton = (Button)findViewById(R.id.button_run);
       
       _mOutArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
       _mOutView.setAdapter(_mOutArrayAdapter);
       _mOutArrayAdapter.clear();

       executeCmd("startup;",false);
       
       _mRunButton.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
               // Send a message using content of the edit text widget
               String command = _mCmdEditText.getText().toString();
               executeCmd(command,true);
           }
       }); 
       
       _mCmdEditText.setOnEditorActionListener(mWriteListener);

   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu){;
	   /*Add menu button  */
	   boolean menuBtnResult=super.onCreateOptionsMenu(menu);
	   _sumAboutAddi=menu.addSubMenu(0,0,0,R.string.mbtn_AboutAddi_title);
	   return menuBtnResult;
   }
   
   public boolean onOptionsItemSelected(MenuItem item)
   {
	   /*Menu button Click Handler*/
	   switch (item.getItemId())
	   {
	   	case 0: /*about Addi*/
	   		Uri projectUri=Uri.parse("http://addi.googlecode.com");   		
	   		Intent BrowerIntent = new Intent(Intent.ACTION_VIEW,projectUri);
	   		startActivity(BrowerIntent);
	   		break;
	   }
	   return super.onOptionsItemSelected(item); 
   }
   private TextView.OnEditorActionListener mWriteListener =
       new TextView.OnEditorActionListener() {
       public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
           // If the action is a key-up event on the return key, send the message
           if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
               String command = view.getText().toString();
               executeCmd(command,true);
           }
           return true;
       }
   };
   
   private void updateResultsInUi() {
       // Back in the UI thread -- update our UI elements based on the data in mResults
	   if (_mResults.equals("PARSER: CCX: continue") == false) {
		   _mOutArrayAdapter.add(_mResults);
		   _prevCmd = "";
	   } 
	   _blockExecute = false;
   }

	public void executeCmd(final String command, boolean displayCommand) {
		if (_blockExecute == false) {
			final Activity act = this;
			if (displayCommand) {
				_mOutArrayAdapter.add(">>  " + command);
			}
			_blockExecute = true;
			
			_command = command;
			_act = this;

			// Fire off a thread to do some work that we shouldn't do directly in the UI thread
			ThreadGroup threadGroup = new ThreadGroup("executeCmdGroup");
			Thread t = new Thread(threadGroup, mRunThread, "executeCmd", 1000000) {};
			t.start();

            _mCmdEditText.setText("");
		}

   }
	
   // Create runnable for thread run
   final Runnable mRunThread = new Runnable() {
       public void run() {
			_mResults = _interpreter.executeExpression(_prevCmd + _command + "\n",_act,_mHandler);
			_prevCmd = _prevCmd + _command  + "\n";
			_mHandler.post(mUpdateResults);
       }
   };
   
}