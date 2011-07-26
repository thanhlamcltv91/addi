package com.addi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.ListView;


public class ListViewExtend extends ListView {
	
	private Addi mParent = null;
	
	public ListViewExtend(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
	
    public ListViewExtend(Context context, AttributeSet atts,int ds)
    {
        super(context,atts,ds);
    }
    
    public ListViewExtend(Context context, AttributeSet atts)
    {
        super(context,atts);
    }
	
	public void setParent(Addi myParent) {
		mParent = myParent;
	}
	
	@Override
	public void onSizeChanged (int w, int h, int oldw, int oldh) {
		if (mParent != null) {
			mParent.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		}
	}
	
}
