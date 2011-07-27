package com.addi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;


public class EditTextExtend extends EditText {
	
	private Addi _parent = null;

    public EditTextExtend(Context context, AttributeSet atts)
    {
        super(context,atts);
    }
	
	public void setParent(Addi myParent) {
		_parent = myParent;
	}
	
	@Override
	public void onSelectionChanged(int selStart, int selEnd) {
		if (_parent != null) {
			if (_parent._selectionSaved) {
				_parent._selectionSaved = false;
				int visibility = _parent._myKeyboardView.getVisibility();
				if (View.GONE == visibility) {
					//just show keyboard, don't change cursor position 
					_parent._mCmdEditText.setSelection(_parent._oldStartSelection, _parent._oldEndSelection);
					_parent._myKeyboardView.setVisibility(View.VISIBLE);
				} else {
					_parent._mCmdEditText.setSelection(_parent._startSelection, _parent._endSelection);
				}
			}
		}
	}
	
}
