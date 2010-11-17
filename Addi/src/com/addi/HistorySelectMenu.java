package com.addi;

import java.util.LinkedList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

public class HistorySelectMenu extends Activity {
	
	private String[] data;
	
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
       data=getHistoryQueue();
		super.onCreate(savedInstanceState);
		
		ListView listView = new ListView(this);
		listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data));
		listView.setItemsCanFocus(true);
		
		setContentView(listView);
	}
	
	private String[] getHistoryQueue()
	{
		Bundle bundle=this.getIntent().getExtras();
		return bundle.getStringArray("historyQueue");
	}
}
