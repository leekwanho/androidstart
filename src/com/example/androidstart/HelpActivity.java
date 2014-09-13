package com.example.androidstart;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.Inflater;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		
		InputStream iFile=getResources().openRawResource(R.raw.help);
		
		TextView textView=(TextView)findViewById(R.id.helptext);
		
		try{
			StringBuffer sBuffer=new StringBuffer();
			BufferedReader br=new BufferedReader(new InputStreamReader(iFile));
			String str=null;
			
			while((str=br.readLine())!=null){
				sBuffer.append(str + "\n");
			}
			br.close();
			textView.setText(sBuffer.toString());
			
		}catch(Exception e){}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.menu,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.MapSearch:
			startActivity(new Intent(this,MapSearchActivity.class));
			finish();
			break;
		case R.id.Telephony:
			startActivity(new Intent(this,CallActivity.class));
			finish();
			break;
		case R.id.Profile:
			startActivity(new Intent(this,ProfileActivity.class));
			finish();
			break;
		
		}
		return true;
		
	}
	
}
