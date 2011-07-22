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

import android.view.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ThreadGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.lang.*;

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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
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
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Addi extends Activity implements OnKeyListener,OnKeyboardActionListener {	
	private ArrayAdapter<String> _mOutArrayAdapter;
	private ListView _mOutView;
	public  EditText _mCmdEditText;
	private Interpreter _interpreter;
	private String _mResults = "";
	private String _prevCmd = "";	
	private boolean _blockExecute = false;
	private String _command;
	private Activity _act;
	private Vector<String> _oldCommands = new Vector<String>();  
	private int _oldCommandIndex = -1;
	private String _partialCommand;
	private String _addiEditString;
	private ArrayList<String> _listLabels;
	String _version = new String();
	private  LinearLayout _mainView;
	private KeyboardView _myKeyboardView;
	private Keyboard _myKeyboard;
	private Keyboard _myKeyboardShifted;
	private String _mWordSeparators;
	private boolean _mCompletionOn;
	private boolean _mPredictionOn;
	private CandidateView _mCandidateView;
    private CompletionInfo[] _mCompletions;
    private StringBuilder _mComposing = new StringBuilder();
    private imsExtend _imsExtend = new imsExtend();

	// Need handler for callbacks to the UI thread
	public final Handler _mHandler = new Handler() {
		public void handleMessage(Message msg) { 
			if (msg.getData().getString("text").startsWith("STARTUPADDIEDITWITH=")) {
				_addiEditString = msg.getData().getString("text").substring(20);
				Intent addiEditIntent = new Intent(Addi.this, AddiEdit.class);
				addiEditIntent.putExtra("fileName", msg.getData().getString("text").substring(20)); // key/value pair, where key needs current package prefix.
				startActivityForResult(addiEditIntent,1); 
			} else if (msg.getData().getString("text").startsWith("STARTUPADDIPLOTWITH=")) {
				Intent addiPlotIntent = new Intent();
				addiPlotIntent.setClassName("com.addiPlot", "com.addiPlot.addiPlot");
				addiPlotIntent.putExtra("plotData", msg.getData().getString("text").substring(20)); // key/value pair, where key needs current package prefix.
				try {
					startActivity(addiPlotIntent);
				} catch (ActivityNotFoundException e) {
					_mOutArrayAdapter.add("You should download AddiPlot for this to work.");
				}
			} else if (msg.getData().getString("text").startsWith("PRINTADDIVERSION")) {
				_mOutArrayAdapter.add("Addi version: " + _version);
			} else {
				_mOutArrayAdapter.add(msg.getData().getString("text"));
			}
		};
	};

	protected void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		if (requestCode == 1) {
			if (resultCode == 0) {  // error
				_mOutArrayAdapter.add("Directory not found or permissions incorrect.");
			} else if (resultCode == 1) {  //save
				_mOutArrayAdapter.add("File saved.");
			} else if (resultCode == 2) {  //save and run
				_mOutArrayAdapter.add("File save, now attempting to run.");
				int lastIndx = _addiEditString.lastIndexOf("/");
				String dir = _addiEditString.substring(0, lastIndx);
				String script = _addiEditString.substring(lastIndx+1);
				script = script.substring(0, script.length()-2);
				executeCmd("cd(\"" + dir + "\"); " + script,true);
			} else if (resultCode == 3) {  //quit
				_mOutArrayAdapter.add("Exited without saving file.");
			}
		}
	}

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
		
		_mWordSeparators = getResources().getString(R.string.word_separators);
		
		setContentView(R.layout.main);

		_interpreter = new Interpreter(true);
		Interpreter.setCacheDir(getCacheDir());

		_mOutView = (ListView)findViewById(R.id.out);
		_mCmdEditText = (EditText)findViewById(R.id.edit_command);

		_mainView = (LinearLayout)findViewById(R.id.wrapView);
		
		makeKeyboardView();
		_mainView.addView(_myKeyboardView);

		super.onCreate(savedInstanceState);

		try {
			PackageInfo pi = getPackageManager().getPackageInfo("com.addi", 0);
			_version = pi.versionName;     // this is the line Eclipse complains
		}
		catch (PackageManager.NameNotFoundException e) {
			// eat error, for testing
			_version = "?";
		}

		_listLabels = new ArrayList<String>();
		try {
			String fileName4 = "addiListView";	
			FileInputStream input4 = openFileInput(fileName4);
			InputStreamReader input4reader = new InputStreamReader(input4);
			BufferedReader buffreader4 = new BufferedReader(input4reader);
			String line4;
			_listLabels.clear();
			while (( line4 = buffreader4.readLine()) != null) {
				_listLabels.add(line4);
			}
			input4.close();
			input4 = openFileInput(fileName4);

			String fileName5 = "addiVersion";	
			FileInputStream input5 = openFileInput(fileName5);
			InputStreamReader input5reader = new InputStreamReader(input5);
			BufferedReader buffreader5 = new BufferedReader(input5reader);

			String savedVersion = buffreader5.readLine();
			if (!savedVersion.startsWith(_version)) {
				_listLabels.add("********* Welcome to Addi " + _version + " *********");
				executeCmd("startup;",false);
			}
			input5.close();

		} catch (IOException e) {
			_listLabels.add("********* Welcome to Addi " + _version + " *********");
			executeCmd("startup;",false);
		}

		_mOutArrayAdapter = new ArrayAdapter<String>(this, R.layout.message, _listLabels);
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


		_mOutView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				_mCmdEditText.dispatchTouchEvent(event);
				return false;
			}
		});

		_mCmdEditText.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				enableKeyboardVisibility();
				return false;
			}
		});

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
						if ((_oldCommandIndex+1) < _oldCommands.size()) {
							if (_oldCommandIndex == -1) {
								_partialCommand = _mCmdEditText.getText().toString();
								_oldCommandIndex = 0;
								_mCmdEditText.setText(_oldCommands.get(_oldCommandIndex));
								_mCmdEditText.setSelection(_oldCommands.get(_oldCommandIndex).length());
							} else {
								_oldCommandIndex = _oldCommandIndex+1;
								_mCmdEditText.setText(_oldCommands.get(_oldCommandIndex));
								_mCmdEditText.setSelection(_oldCommands.get(_oldCommandIndex).length());
							}
						}
					} else if (keyCode == KeyEvent.KEYCODE_ENTER) {
						String command = _mCmdEditText.getText().toString();
						executeCmd(command,true);
						return true;
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

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{  
		
		int visibility = _myKeyboardView.getVisibility();
		_mainView.removeView(_myKeyboardView);
		super.onConfigurationChanged(newConfig);
		//boolean landscape = (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE);
		//_mainView.setOrientation( landscape ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL);
		makeKeyboardView();
		_mainView.addView(_myKeyboardView);
		_myKeyboardView.setVisibility(visibility);
		
	}

	/** Called when the activity is put into background. */
	@Override
	public void onPause() {
		try
		{    
			super.onPause();

			String fileName4 = "addiListView";	
			OutputStreamWriter out4 = new OutputStreamWriter(openFileOutput(fileName4, MODE_PRIVATE));
			int startIndex = 0;
			if (_listLabels.size() > 100) {
				startIndex = _listLabels.size() - 100;
			}
			for (int lineLoop = startIndex; lineLoop < _listLabels.size(); lineLoop++) {
				out4.write(_listLabels.get(lineLoop));
				out4.write("\n");
			}
			out4.close();

			String fileName5 = "addiVersion";	
			OutputStreamWriter out5 = new OutputStreamWriter(openFileOutput(fileName5, MODE_PRIVATE));
			out5.write(_version);
			out5.close();

			String fileName3 = "addiCommands";	

			OutputStreamWriter out3 = new OutputStreamWriter(openFileOutput(fileName3, MODE_PRIVATE));

			for (int lineLoop = 0; lineLoop < _oldCommands.size(); lineLoop++) {
				out3.write(_oldCommands.get(lineLoop));
				out3.write("\n");
			}

			out3.close();

			String fileName = "addiVariables";

			//create streams
			FileOutputStream output = openFileOutput(fileName, MODE_PRIVATE);

			_interpreter.globals.getLocalVariables().saveVariablesOnPause(output);

			String fileName2 = "addiPaths";	

			FileOutputStream output2 = openFileOutput(fileName2, MODE_PRIVATE);

			output2.write(_interpreter.globals.getWorkingDirectory().getAbsolutePath().getBytes());

			output2.close();

		}
		catch(java.io.IOException except)
		{
		}
	}

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
	
    /**
     * Helper function to commit any text being composed in to the editor.
     */
    private void commitTyped(InputConnection inputConnection) {
        if (_mComposing.length() > 0) {
            inputConnection.commitText(_mComposing, _mComposing.length());
            _mComposing.setLength(0);
            updateCandidates();
        }
    }

    /**
     * Helper to update the shift state of our keyboard based on the initial
     * editor state.
     */
    private void updateShiftKeyState(EditorInfo attr) {
        if (attr != null 
                && mInputView != null && mQwertyKeyboard == mInputView.getKeyboard()) {
            int caps = 0;
            EditorInfo ei = _imsExtend.getCurrentInputEditorInfo();
            if (ei != null && ei.inputType != EditorInfo.TYPE_NULL) {
                caps = _imsExtend.getCurrentInputConnection().getCursorCapsMode(attr.inputType);
            }
            mInputView.setShifted(mCapsLock || caps != 0);
        }
    }
    
    /**
     * Helper to determine if a given character code is alphabetic.
     */
    private boolean isAlphabet(int code) {
        if (Character.isLetter(code)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Helper to send a key down / key up pair to the current editor.
     */
    private void keyDownUp(int keyEventCode) {
        _imsExtend.getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_DOWN, keyEventCode));
        _imsExtend.getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_UP, keyEventCode));
    }
    
    /**
     * Helper to send a character to the editor as raw key events.
     */
    private void sendKey(int keyCode) {
        switch (keyCode) {
            case '\n':
                keyDownUp(KeyEvent.KEYCODE_ENTER);
                break;
            default:
                if (keyCode >= '0' && keyCode <= '9') {
                    keyDownUp(keyCode - '0' + KeyEvent.KEYCODE_0);
                } else {
                	_imsExtend.getCurrentInputConnection().commitText(String.valueOf((char) keyCode), 1);
                }
                break;
        }
    }
	
    /**
     * Update the list of available candidates from the current composing
     * text.  This will need to be filled in by however you are determining
     * candidates.
     */
    private void updateCandidates() {
        if (!_mCompletionOn) {
            if (_mComposing.length() > 0) {
                ArrayList<String> list = new ArrayList<String>();
                list.add(_mComposing.toString());
                setSuggestions(list, true, true);
            } else {
                setSuggestions(null, false, false);
            }
        }
    }
    
    public void setSuggestions(List<String> suggestions, boolean completions,
            boolean typedWordValid) {
        if (suggestions != null && suggestions.size() > 0) {
            _imsExtend.setCandidatesViewShown(true);
        } else if (_imsExtend.isExtractViewShown()) {
            _imsExtend.setCandidatesViewShown(true);
        }
        if (_mCandidateView != null) {
            _mCandidateView.setSuggestions(suggestions, completions, typedWordValid);
        }
    }
    
    private void handleBackspace() {
        final int length = _mComposing.length();
        if (length > 1) {
            _mComposing.delete(length - 1, length);
            _imsExtend.getCurrentInputConnection().setComposingText(_mComposing, 1);
            updateCandidates();
        } else if (length > 0) {
            _mComposing.setLength(0);
            _imsExtend.getCurrentInputConnection().commitText("", 0);
            updateCandidates();
        } else {
            keyDownUp(KeyEvent.KEYCODE_DEL);
        }
        updateShiftKeyState(_imsExtend.getCurrentInputEditorInfo());
    }

    private void handleShift() {
        if (mInputView == null) {
            return;
        }
        
        Keyboard currentKeyboard = mInputView.getKeyboard();
        if (mQwertyKeyboard == currentKeyboard) {
            // Alphabet keyboard
            checkToggleCapsLock();
            mInputView.setShifted(mCapsLock || !mInputView.isShifted());
        } else if (currentKeyboard == mSymbolsKeyboard) {
            mSymbolsKeyboard.setShifted(true);
            mInputView.setKeyboard(mSymbolsShiftedKeyboard);
            mSymbolsShiftedKeyboard.setShifted(true);
        } else if (currentKeyboard == mSymbolsShiftedKeyboard) {
            mSymbolsShiftedKeyboard.setShifted(false);
            mInputView.setKeyboard(mSymbolsKeyboard);
            mSymbolsKeyboard.setShifted(false);
        }
    }
    
    private void handleCharacter(int primaryCode, int[] keyCodes) {
        if (isInputViewShown()) {
            if (mInputView.isShifted()) {
                primaryCode = Character.toUpperCase(primaryCode);
            }
        }
        if (isAlphabet(primaryCode) && _mPredictionOn) {
            _mComposing.append((char) primaryCode);
            _imsExtend.getCurrentInputConnection().setComposingText(_mComposing, 1);
            updateShiftKeyState(_imsExtend.getCurrentInputEditorInfo());
            updateCandidates();
        } else {
        	_imsExtend.getCurrentInputConnection().commitText(
                    String.valueOf((char) primaryCode), 1);
        }
    }

    private void handleClose() {
        commitTyped(_imsExtend.getCurrentInputConnection());
        _myKeyboardView.setVisibility(View.VISIBLE);
    }

    private String getWordSeparators() {
        return _mWordSeparators;
    }
    
    public boolean isWordSeparator(int code) {
        String separators = getWordSeparators();
        return separators.contains(String.valueOf((char)code));
    }

    public void pickDefaultCandidate() {
        pickSuggestionManually(0);
    }
    
    public void pickSuggestionManually(int index) {
        if (_mCompletionOn && _mCompletions != null && index >= 0
                && index < _mCompletions.length) {
            CompletionInfo ci = _mCompletions[index];
            _imsExtend.getCurrentInputConnection().commitCompletion(ci);
            if (_mCandidateView != null) {
                _mCandidateView.clear();
            }
            updateShiftKeyState(_imsExtend.getCurrentInputEditorInfo());
        } else if (_mComposing.length() > 0) {
            // If we were generating candidate suggestions for the current
            // text, we would commit one of them here.  But for this sample,
            // we will just commit the current text.
            commitTyped(_imsExtend.getCurrentInputConnection());
        }
    }
    
    public void swipeRight() {
        if (_mCompletionOn) {
            pickDefaultCandidate();
        }
    }
    
    public void swipeLeft() {
        handleBackspace();
    }

    public void swipeDown() {
        handleClose();
    }

    public void swipeUp() {
    }
    
    public void onPress(int primaryCode) {
    }
    
    public void onRelease(int primaryCode) {
    }
    
	@Override
	public void onText(CharSequence arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {    
		return false;  
	}  

	@Override  
	public void onKey(int primaryCode, int[] keyCodes) {
		char charKeyCode = (char)primaryCode;
		String textToInsert = "" + charKeyCode;
		int start = _mCmdEditText.getSelectionStart();
		int end = _mCmdEditText.getSelectionEnd();
		_mCmdEditText.getText().replace(Math.min(start, end), Math.max(start, end),
				textToInsert, 0, textToInsert.length());
		//for (int keyCode : keyCodes) {    
		//}  
	} 

	private void enableKeyboardVisibility() {    
		int visibility = _myKeyboardView.getVisibility();  
		switch (visibility) {    
		case View.GONE:  
		case View.INVISIBLE:  
			_myKeyboardView.setVisibility(View.VISIBLE);  
			break;  
		}  
	}

	private void toggleKeyboardVisibility() {   
		int visibility = _myKeyboardView.getVisibility();  
		switch (visibility) {  
		case View.VISIBLE:  
			_myKeyboardView.setVisibility(View.INVISIBLE);  
			break;  
		case View.GONE:  
		case View.INVISIBLE:  
			_myKeyboardView.setVisibility(View.VISIBLE);  
			break;  
		}  
	}

	private void makeKeyboardView () {
		_myKeyboard = new Keyboard(this,R.xml.qwerty);
		_myKeyboardShifted = new Keyboard(this,R.xml.symbols_shift);
		_myKeyboardView = new KeyboardView(this, null);
		_myKeyboardView.setKeyboard(_myKeyboard);  
		_myKeyboardView.setEnabled(true);  
		_myKeyboardView.setPreviewEnabled(true);  
		_myKeyboardView.setOnKeyListener(this);  
		_myKeyboardView.setOnKeyboardActionListener(this); 
		_myKeyboardView.setVisibility(View.GONE);
		_myKeyboardView.setFocusable(false);
		_myKeyboardView.setFocusableInTouchMode(false);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0);
		lp.gravity = Gravity.BOTTOM;
		_myKeyboardView.setLayoutParams(lp);
	}

}