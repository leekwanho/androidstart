package com.example.androidstart;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity implements
		AdapterView.OnItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		String[] items = { "지도보기", "전화기능", "환경설정", "도움말" };
		// 어댑터 준비
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
				R.layout.menu_item, items);
		// 어댑터 연결
		ListView menuList = (ListView) findViewById(R.id.listViewMenu);
		menuList.setAdapter(adapt);

		menuList.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
		TextView textView = (TextView) v;
		String strText = textView.getText().toString();
		if (strText.equals("지도보기")) {
			startActivity(new Intent(MenuActivity.this, MapSearchActivity.class));// 지도
																			// 엑티비티
		} else if (strText.equals("전화기능")) {
			startActivity(new Intent(MenuActivity.this, CallActivity.class));// 전화
																				// 엑티비티
		} else if (strText.equals("환경설정")) {
			startActivity(new Intent(MenuActivity.this, ProfileActivity.class));// 환경설정
																				// 엑티비티
		} else if (strText.equals("도움말")) {
			startActivity(new Intent(MenuActivity.this, HelpActivity.class));// 도움말
																				// 엑티비티
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.start_page, menu);
		return true;
	}

}
