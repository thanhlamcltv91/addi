package com.addi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;


public class EditTextExtend extends EditText {
	
	private AddiBase _parent = null;

    public EditTextExtend(Context context, AttributeSet atts)
    {
        super(context,atts);
        _parent = (AddiBase)context;
    }
	
	//@Override 
	//protected void onSelectionChanged(int selStart, int selEnd) {
		//if (_parent != null) {
		//	_parent.updateSuggestions();
		//}
	//}
	 
	@Override      
	public boolean onCheckIsTextEditor() {   
		return false;     
	} 
	
}
