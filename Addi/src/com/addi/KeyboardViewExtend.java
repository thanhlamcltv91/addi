package com.addi;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

public class KeyboardViewExtend extends KeyboardView implements KeyboardView.OnKeyboardActionListener
{

	private Addi _parent = null;
	private Keyboard _myKeyboard = null;
	private Keyboard _myKeyboardShifted = null;

	public KeyboardViewExtend(Context c, AttributeSet a)
	{
		super(c,a);
		_parent = (Addi)c;
		
		setOnKeyboardActionListener(this);
		setEnabled(true);  
		setPreviewEnabled(true);   
		setVisibility(View.GONE);
		setFocusable(false);
		setFocusableInTouchMode(false);
		setBackgroundColor( Color.BLACK );
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0);
		lp.gravity = Gravity.BOTTOM;
		setLayoutParams(lp);
		makeKeyboardView();
	}

	public void swipeUp() {}
	
	public void swipeDown() {}
	
	public void swipeLeft() {
		_parent.sendKey(0,0,Keyboard.KEYCODE_DELETE);
	}
	
	public void swipeRight() {}
	
	public void onPress(int k) {}
	
	public void onRelease(int k) {}
	
	public void onText(CharSequence s) {
		for (int i=0; i<s.length(); i++) {
			_parent.sendKey(0,0,s.charAt(i));
		}
	}
	
	public void onKey(int k,int[] ignore) { 
		_parent.sendKey(0,0,k); 
	}
	
	public void makeKeyboardView () {
		_myKeyboard = new Keyboard(_parent, R.xml.qwerty);
		_myKeyboardShifted = new Keyboard(_parent,R.xml.symbols_shift);
		setKeyboard(_myKeyboard);  
	}
	
	public void myOnConfigurationChanged(Configuration newConfig)
	{ 
		makeKeyboardView();
	}
	
}