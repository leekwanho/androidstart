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

		String[] items = { "��������", "��ȭ���", "ȯ�漳��", "����" };
		// ����� �غ�
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
				R.layout.menu_item, items);
		// ����� ����
		ListView menuList = (ListView) findViewById(R.id.listViewMenu);
		menuList.setAdapter(adapt);

		menuList.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
		TextView textView = (TextView) v;
		String strText = textView.getText().toString();
		if (strText.equals("��������")) {
			startActivity(new Intent(MenuActivity.this, MapSearchActivity.class));// ����
																			// ��Ƽ��Ƽ
		} else if (strText.equals("��ȭ���")) {
			startActivity(new Intent(MenuActivity.this, CallActivity.class));// ��ȭ
																				// ��Ƽ��Ƽ
		} else if (strText.equals("ȯ�漳��")) {
			startActivity(new Intent(MenuActivity.this, ProfileActivity.class));// ȯ�漳��
																				// ��Ƽ��Ƽ
		} else if (strText.equals("����")) {
			startActivity(new Intent(MenuActivity.this, HelpActivity.class));// ����
																				// ��Ƽ��Ƽ
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.start_page, menu);
		return true;
	}

}
