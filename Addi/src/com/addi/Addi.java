package com.addi;

import java.io.IOException;
import java.io.InputStream;

import com.addi.R;
import com.addi.R.id;
import com.addi.R.layout;
import com.addi.core.interpreter.*;

import android.app.Activity;
import android.content.Intent; 
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
   private static AssetManager _assetManager;
   private String _prevCmd = "";	
   
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.main);
       
       _assetManager = getResources().getAssets();
       
       _interpreter = new Interpreter(true);
       
       _mOutView = (ListView)findViewById(R.id.out);
       _mCmdEditText = (EditText)findViewById(R.id.edit_command);
       _mRunButton = (Button)findViewById(R.id.button_run);
       
       _mOutArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
       _mOutView.setAdapter(_mOutArrayAdapter);
       _mOutArrayAdapter.clear();
       _interpreter.setOutputAdapter(_mOutArrayAdapter);
       
       String result = _interpreter.executeExpression("startup;");
       _mOutArrayAdapter.add(result);
       
       _mRunButton.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
               // Send a message using content of the edit text widget
               String command = _mCmdEditText.getText().toString();
               executeCmd(command);
           }
       }); 
       
       _mCmdEditText.setOnEditorActionListener(mWriteListener);

   }
   
   private TextView.OnEditorActionListener mWriteListener =
       new TextView.OnEditorActionListener() {
       public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
           // If the action is a key-up event on the return key, send the message
           if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
               String command = view.getText().toString();
               executeCmd(command);
               view.setText("");
           }
           return true;
       }
   };
   
   public void executeCmd(String command) {
	 _mOutArrayAdapter.add(">>  " + command);	   
	 String result = _interpreter.executeExpression(_prevCmd + command + "\n");
	 if (result.equals("PARSER: CCX: continue") == true) {
		 _prevCmd = _prevCmd + command  + "\n";
	 } else {
	    _mOutArrayAdapter.add(result);
	    _prevCmd = "";
	 }
   }
   
	public static String readAsset(String asset) {

        // Programmatically load text from an asset and place it into the
        // text view.  Note that the text we are loading is ASCII, so we
        // need to convert it to UTF-16.
        try {
            InputStream is = _assetManager.open(asset);

            // We guarantee that the available method returns the total
            // size of the asset...  of course, this does mean that a single
            // asset can't be more than 2 gigs.
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            String text = new String(buffer);
            
            return text;

        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
	}
}

