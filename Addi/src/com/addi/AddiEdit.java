// Copyright (C) 2011 Free Software Foundation FSF
//
// This file is part of Addi.
//
// Addi is free software; you can redistribute it and/or modify it
// under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 3 of the License, or (at
// your option) any later version.
//
// Addi is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with Addi. If not, see <http://www.gnu.org/licenses/>.

package com.addi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.*;

import com.addi.R;
import com.addi.R.id;
import com.addi.R.layout;

import android.app.Activity;
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

public class AddiEdit extends AddiBase {

	private File _mFile;
	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {	   
       setContentView(R.layout.edit);
       super.onCreate(savedInstanceState);
       onNewIntent(getIntent());
   }
   
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	    setIntent(intent);
	    
	    setResult(3);
	    
	    String fileName = null;
	    try {
	    	fileName = getIntent().getData().getEncodedPath();
	    } catch (NullPointerException e) {
	    	try {
		    	fileName = intent.getStringExtra("fileName");
		    } catch (NullPointerException ex) {	
		    }
	    }
	    
	    _mFile = new File(fileName);
	    
	    if (_mFile == null) {
	    	setResult(0);
	    	finish();
	    } else {
	    	if (_mFile.exists()) {
	    		try {
	    			_mCmdEditText.setText(getContents(_mFile));
	    		} catch (IOException e) {
	    			setResult(0);
	    			finish();
	    		}
	    	}
	    }
	    
	} 
	
	  /**
	  * Fetch the entire contents of a text file, and return it in a String.
	  * This style of implementation does not throw Exceptions to the caller.
	  *
	  * @param aFile is a file which already exists and can be read.
	 * @throws IOException 
	  */
	  static public String getContents(File aFile) throws IOException {
	    //...checks on aFile are elided
	    StringBuilder contents = new StringBuilder();
	    
	    //use buffering, reading one line at a time
	    //FileReader always assumes default encoding is OK!
	    BufferedReader input =  new BufferedReader(new FileReader(aFile));
	    try {
	    	String line = null; //not declared within while loop
	        /*
	        * readLine is a bit quirky :
	        * it returns the content of a line MINUS the newline.
	        * it returns null only for the END of the stream.
	        * it returns an empty String if two newlines appear in a row.
	        */
	        while (( line = input.readLine()) != null){
	          contents.append(line);
	          contents.append(System.getProperty("line.separator"));
	        }
	      }
	      finally {
	        input.close();
	      }
	  
	    
	    return contents.toString();
	  }

	  /**
	  * Change the contents of text file in its entirety, overwriting any
	  * existing text.
	  *
	  * This style of implementation throws all exceptions to the caller.
	  *
	  * @param aFile is an existing file which can be written to.
	  * @throws IllegalArgumentException if param does not comply.
	  * @throws FileNotFoundException if the file does not exist.
	  * @throws IOException if problem encountered during write.
	  */
	  static public void setContents(File aFile, String aContents) throws IOException {
		
	    //use buffering
	    Writer output = new BufferedWriter(new FileWriter(aFile));
	    try {
	      //FileWriter always assumes default encoding is OK!
	      output.write( aContents );
	    }
	    finally {
	      output.close();
	    }
	  }
	  
	  public void handleEnter() {
			int start = _mCmdEditText.getSelectionStart();
			int end = _mCmdEditText.getSelectionEnd();
			String textToInsert = "\n";
			_mCmdEditText.getText().replace(Math.min(start, end), Math.max(start, end), textToInsert, 0, textToInsert.length());
	  }
   
	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	      MenuInflater inflater = getMenuInflater();
	      inflater.inflate(R.menu.edit_menu, menu);
	      return true;
	  }
	  
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	      switch (item.getItemId()) {
	          case R.id.editMenuSave: 
	          		try {
    		   			setContents(_mFile,_mCmdEditText.getText().toString());
    		   			setResult(1);
			    		finish();
					} catch (IOException e) {
						setResult(0);
			    		finish();
					}
					break;
	          case R.id.editMenuSaveRun: 
	          		try {
    		   			setContents(_mFile,_mCmdEditText.getText().toString());
    		   			setResult(2);
			    		finish();
					} catch (IOException e) {
						setResult(0);
			    		finish();
					}
					break;
	          case R.id.editMenuQuit: 
	          		setResult(3);
  	    			finish();
  	    			break;
	      }
	      return true;
	  }
}