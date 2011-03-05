package com.addi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ThreadGroup;
import java.util.Vector;

import com.addi.R;
import com.addi.R.id;
import com.addi.R.layout;
import com.addi.core.interpreter.*;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent; 
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
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
   private Vector<String> _oldCommands = new Vector<String>();  
   private int _oldCommandIndex = -1;
   private String _partialCommand;

   // Need handler for callbacks to the UI thread
   public final Handler _mHandler = new Handler() {
	    public void handleMessage(Message msg) {
	    	if (msg.getData().getString("text").startsWith("STARTUPADDIPLOTWITH=")) {
	 		   Intent addiPlotIntent = new Intent();
	 		   addiPlotIntent.setClassName("com.addiPlot", "com.addiPlot.addiPlot");
	 		   addiPlotIntent.putExtra("plotData", msg.getData().getString("text").substring(20)); // key/value pair, where key needs current package prefix.
	 		   try {
	 			   startActivity(addiPlotIntent);
	 		   } catch (ActivityNotFoundException e) {
	 			   _mOutArrayAdapter.add("You should download AddiPlot for this to work.");
	 		   }
	        } else {
	    	   _mOutArrayAdapter.add(msg.getData().getString("text"));
	        }
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
       Interpreter.setCacheDir(getCacheDir());
       
       _mOutView = (ListView)findViewById(R.id.out);
       _mCmdEditText = (EditText)findViewById(R.id.edit_command);
       //_mRunButton = (Button)findViewById(R.id.button_run);
       
       _mOutArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
       _mOutView.setAdapter(_mOutArrayAdapter);
       _mOutView.setDividerHeight(0);
       _mOutView.setDivider(new ColorDrawable(0x00FFFFFF));
       _mOutView.setFocusable(false);
       _mOutView.setFocusableInTouchMode(false);
       _mOutView.setClickable(false);
       _mOutView.setDescendantFocusability(393216);
       _mOutView.setFooterDividersEnabled(false);
       _mOutView.setHeaderDividersEnabled(false);
       _mOutView.setChoiceMode(0);
       
       _mOutArrayAdapter.clear();
       
       String version = new String();
       try {
    	   PackageInfo pi = getPackageManager().getPackageInfo("com.addi", 0);
    	   version = pi.versionName;     // this is the line Eclipse complains
       }
       catch (PackageManager.NameNotFoundException e) {
    	   // eat error, for testing
    	   version = "?";
       }
       _mOutArrayAdapter.add("********* Welcome to Addi " + version + " *********");

       executeCmd("startup;",false);
       
       _mOutView.setOnTouchListener(new OnTouchListener() {
    	   @Override
    	   public boolean onTouch(View view, MotionEvent event) {
    		   _mCmdEditText.dispatchTouchEvent(event);
    		   return false;
    	   }
       });
       
       //KEYCODE_DPAD_DOWN
       
       _mCmdEditText.setOnEditorActionListener(mWriteListener);
       
       _mCmdEditText.setOnKeyListener(new OnKeyListener() {

    	   @Override
    	   public boolean onKey(View view, int keyCode, KeyEvent event) {
    		   // TODO Auto-generated method stub
    		   if (event.getAction() == KeyEvent.ACTION_DOWN) {
    			   if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
    				   if (_oldCommandIndex == -1) {
    					   //do nothing
    				   } else if (_oldCommandIndex == 0) {
    					   _oldCommandIndex=_oldCommandIndex-1;
    					   _mCmdEditText.setText(_partialCommand);
    					   _mCmdEditText.setSelection(_partialCommand.length());
    				   } else {
    					   _oldCommandIndex=_oldCommandIndex-1;
    					   _mCmdEditText.setText(_oldCommands.get(_oldCommandIndex));
    					   _mCmdEditText.setSelection(_oldCommands.get(_oldCommandIndex).length());
    				   }
    			   } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
    				   if (_oldCommandIndex == -1) {
    					   _partialCommand = _mCmdEditText.getText().toString();
    					   _oldCommandIndex = 0;
    					   _mCmdEditText.setText(_oldCommands.get(_oldCommandIndex));
    					   _mCmdEditText.setSelection(_oldCommands.get(_oldCommandIndex).length());
    				   } else if ((_oldCommandIndex+1) < _oldCommands.size()) {
    					   _oldCommandIndex = _oldCommandIndex+1;
    					   _mCmdEditText.setText(_oldCommands.get(_oldCommandIndex));
    					   _mCmdEditText.setSelection(_oldCommands.get(_oldCommandIndex).length());
    				   }
    			   }
    		   }
    		   return false;
    	   }
    	   
       });
       
       try
       {    	
    	   String fileName = "addiVariables";
       	
       		//create streams
       		FileInputStream input = openFileInput(fileName);
      
       		_interpreter.globals.getLocalVariables().loadVariablesOnCreate(input);
       		
       		String fileName2 = "addiPaths";	
       		
       		FileInputStream input2 = openFileInput(fileName2);
       		
       		int length = input2.available();
       		
       		byte buffer[] = new byte[(int)length];
       		
       		input2.read(buffer);
       		
       		String dirStr = new String(buffer);
       		
       		File dir = new File(dirStr);
       		
       		if (dir.isDirectory()) {
       			_interpreter.globals.setWorkingDirectory(dir);
       		}
       		
       		input2.close();
       		
       		String fileName3 = "addiCommands";	
       		
       		FileInputStream input3 = openFileInput(fileName3);
       		
       	    InputStreamReader input3reader = new InputStreamReader(input3);
       	    BufferedReader buffreader = new BufferedReader(input3reader);
       	 
            String line;
       	 
            _oldCommands.clear();
       	    while (( line = buffreader.readLine()) != null) {
       	    	_oldCommands.add(line);
       	    }
       		
       		input3.close();
       		
       }
       catch(java.io.IOException except)
       {
       }

   }
   
   /** Called when the activity is put into background. */
   @Override
   public void onPause() {
       try
       {    
    	   super.onPause();
    	   String fileName = "addiVariables";
       	
       		//create streams
       		FileOutputStream output = openFileOutput(fileName, MODE_PRIVATE);
      
       		_interpreter.globals.getLocalVariables().saveVariablesOnPause(output);
       		
       	    String fileName2 = "addiPaths";	
       	    
       	    FileOutputStream output2 = openFileOutput(fileName2, MODE_PRIVATE);
       	
       	    output2.write(_interpreter.globals.getWorkingDirectory().getAbsolutePath().getBytes());
       	    
       	    output2.close();
       	    
       	    String fileName3 = "addiCommands";	
    	        	
    	    OutputStreamWriter out = new OutputStreamWriter(openFileOutput(fileName3, MODE_PRIVATE));
    	   
    	    for (int lineLoop = 0; lineLoop < _oldCommands.size(); lineLoop++) {
    	    	out.write(_oldCommands.get(lineLoop));
    	    	out.write("\n");
    	    }
    	    
    	    out.close();
       }
       catch(java.io.IOException except)
       {
       }
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
				_oldCommands.add(0, command);
				if (_oldCommands.size() == 100) {
					_oldCommands.remove(99);
				}
			}
			_oldCommandIndex = -1;
			
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