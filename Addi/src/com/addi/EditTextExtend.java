package com.addi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;


public class EditTextExtend extends EditText {
	
	private Addi _parent = null;

    public EditTextExtend(Context context, AttributeSet atts)
    {
        super(context,atts);
        _parent = (Addi)context;
    }
	
/*	@Override
	public void onSelectionChanged(int selStart, int selEnd) {
		if (_parent != null) {
			if (_parent._selectionSaved) {
				_parent._selectionSaved = false;
				_parent._oldSelectionSaved = false;
				if (View.GONE == _parent._oldVisibility) {
					//just show keyboard, don't change cursor position 
					_parent._mCmdEditText.setSelection(_parent._oldStartSelection, _parent._oldEndSelection);
					_parent._myKeyboardView.setVisibility(View.VISIBLE);
					_parent._oldVisibility = _parent._myKeyboardView.getVisibility();
				} else {
					_parent._mCmdEditText.setSelection(_parent._startSelection, _parent._endSelection);
				}
			} else if (_parent._oldSelectionSaved) {
				_parent._oldSelectionSaved = false;
				int visibility = _parent._myKeyboardView.getVisibility();
				if (View.GONE == visibility) {
					//just show keyboard, don't change cursor position 
					_parent._mCmdEditText.setSelection(_parent._oldStartSelection, _parent._oldEndSelection);
					_parent._myKeyboardView.setVisibility(View.VISIBLE);
				} else {
					_parent._mCmdEditText.setSelection(_parent._oldStartSelection, _parent._oldEndSelection);
				}
			}
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		_parent._selectionSaved = false;
		_parent._oldSelectionSaved = false;
	    if (keyCode == KeyEvent.KEYCODE_BACK  && event.getRepeatCount() == 0) {
	    	int visibility = _parent._myKeyboardView.getVisibility();
	    	if (View.VISIBLE == visibility) {
	    		_parent._myKeyboardView.setVisibility(View.GONE);
	    		return true;
	    	}
	        return false;
	    }
	    return super.onKeyDown(keyCode, event);
	}
*/
	@Override      
	public boolean onCheckIsTextEditor() {   
		return false;     
	} 
	
}
