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

import com.addi.R;

import android.view.KeyEvent;
import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;

public class AddiBase extends Activity {

	public  EditTextExtend _mCmdEditText;
	public KeyboardViewExtend _myKeyboardView;
	private CandidateView _mCandidateView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		/*support multi language pack (Auto select) */
		Resources res = getResources();
		Configuration conf =res.getConfiguration();
		DisplayMetrics dm=res.getDisplayMetrics();
		res.updateConfiguration(conf, dm);

		super.onCreate(savedInstanceState);

		_mCmdEditText = (EditTextExtend)findViewById(R.id.edit_command);
		_myKeyboardView = (KeyboardViewExtend)findViewById(R.id.keyboard);
		_mCandidateView = (CandidateView)findViewById(R.id.candidate);

		_mCmdEditText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				enableKeyboardVisibility();
			}
		});

	}

	public void handleBackspace() {
		int start = _mCmdEditText.getSelectionStart();
		int end = _mCmdEditText.getSelectionEnd();
		String textToInsert = "";
		if (start != end) {
			_mCmdEditText.getText().replace(Math.min(start, end), Math.max(start, end), textToInsert, 0, textToInsert.length());
		} else if (start != 0) {
			_mCmdEditText.getText().replace(start-1, start, textToInsert, 0, textToInsert.length());
		}
	}
	
	public void handleEnter() {
	}

	public void sendText(String textToInsert) {
		int start = _mCmdEditText.getSelectionStart();
		int end = _mCmdEditText.getSelectionEnd();
		_mCmdEditText.getText().replace(Math.min(start, end), Math.max(start, end),
				textToInsert, 0, textToInsert.length());
		_mCandidateView.updateSuggestions(_mCmdEditText.getText().toString(),true,true);
	}

	public void enableKeyboardVisibility() {    
		int visibility = _myKeyboardView.getVisibility();  
		switch (visibility) {    
		case View.GONE:  
		case View.INVISIBLE:  
			_myKeyboardView.setVisibility(View.VISIBLE);  
			break;  
		}  
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{ 
		super.onConfigurationChanged(newConfig);
		_myKeyboardView.myOnConfigurationChanged(newConfig);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			int visibility = _myKeyboardView.getVisibility();
			if (visibility == View.VISIBLE) {
				_myKeyboardView.setVisibility(View.GONE);
				return true;
			}
			return super.onKeyDown(keyCode, event);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		int visibility = _myKeyboardView.getVisibility();
		if (visibility == View.VISIBLE) {
			_myKeyboardView.setVisibility(View.GONE); 
		} else {
			finish();
			System.exit(0);
		}
		return;
	}

}