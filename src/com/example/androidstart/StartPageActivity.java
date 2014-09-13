package com.example.androidstart;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

public class StartPageActivity extends Activity implements
		Animation.AnimationListener {
	TextView textView1, textView2;
	// �����϶��� tableAni1~2 �� ���(���̺�ο� �����̹Ƿ�)
	// �����ϋ��� ���̺�ο찡 �Ѱ��̹Ƿ� �̹����� ����(imageVeiw1~4)
	Animation textAni1, textAni2, imageAni1, imageAni2, tableAni1, tableAni2;
	ImageView imageView1, imageView2, imageView3, imageView4;
	TableRow tableRow1, tableRow2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_page);

		// �� �Ӽ��� ã�� ����
		textView1 = (TextView) findViewById(R.id.textview1);// �ؽ�Ʈ��� ���μ��� ���
															// �������� õõ�� ��Ÿ���⸸ �ϴ�
															// �ִϸ��̼� ���
		textView2 = (TextView) findViewById(R.id.textview2);
		imageView1 = (ImageView) findViewById(R.id.imageview1);// �̹������ ���θ���Ͻ�
																// ���̺�ο찡 �ϳ��̹Ƿ�
																// ���ε�������� ����
																// ������ �ִϸ��̼� ����
		imageView2 = (ImageView) findViewById(R.id.imageview2);
		imageView3 = (ImageView) findViewById(R.id.imageview3);
		imageView4 = (ImageView) findViewById(R.id.imageview4);

		tableRow1 = (TableRow) findViewById(R.id.tablerow);// ���̺�ο�� ���θ���Ͻ�
															// ���̺�ο캰 �ִϸ��̼�
															// �����̹Ƿ� ���̺�ο캰��
															// �ִϸ��̼� ����
		tableRow2 = (TableRow) findViewById(R.id.tablerow2);

		// �ִϸ��̼��� ���� ����
		textAni1 = AnimationUtils.loadAnimation(this, R.anim.text_anim);// �ؽ�Ʈ��
																		// �ִϸ��̼�
																		// ����
																		// �ܼ���
																		// õõ��
																		// �����°�
																		// ����
		textAni2 = AnimationUtils.loadAnimation(this, R.anim.text_anim2);// 3�ʵ�
																			// ������
																			// �ؽ�Ʈ��
																			// �ִϸ��̼�
		imageAni1 = AnimationUtils.loadAnimation(this, R.anim.custom_anim);// �����϶���
																			// �̹���
																			// �ִϸ��̼�.
																			// ȸ���ϸ鼭
																			// õõ��
																			// ����
		imageAni2 = AnimationUtils.loadAnimation(this, R.anim.custom_anim2);// 1��
																			// 0~3�ʿ�
																			// ��Ÿ����
																			// 2��
																			// 3~6�ʿ�
																			// �ð�����
																			// �ΰ�
																			// ��Ÿ��
		tableAni1 = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
		tableAni2 = AnimationUtils.loadAnimation(this, R.anim.custom_anim2);

		textView1.startAnimation(textAni1);// ���μ��� ��� �ؽ�Ʈ��� �Ȱ��� ����ϹǷ� ���ǹ��տ��� ����
		textView2.startAnimation(textAni2);// �Ʒ��� �ؽ�Ʈ��� 3�ʵ� ��Ÿ��

		// ȭ�����
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {// ȭ����
																									// �����Ͻ�
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// ���η�
																				// �����ϰ�

			tableRow1.startAnimation(tableAni1);// �����̹Ƿ� ���̺�ο� �������� �ִϸ��̼� �۵�
			tableRow2.startAnimation(tableAni2);// �ι�° ���̺�ο�� 3�ʵ� ��Ÿ��
		}

		else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// ȭ����
																										// �����Ͻ�
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// ����
																				// �����ϰ�

			imageView1.startAnimation(imageAni1);// ���δ� ���̺�ο찡 �ϳ��̹Ƿ� �̹����� �ִϸ��̼�
													// ����
			imageView2.startAnimation(imageAni1);// imageAni1�� �ٷ� �����̹Ƿ� �̹�����
													// 1~2�̰�
			imageView3.startAnimation(imageAni2);// �̹�����3~4�� 3�ʵڽ����̹Ƿ�
			imageView4.startAnimation(imageAni2);// imageAni2 ���
		}

		textAni2.setAnimationListener(this);// text2�� ���� �������� ������ �ִϸ��̼��̹Ƿ�
											// text2�� ������ ��Ƽ��Ƽ ��ȯ

	}

	public void onAnimationEnd(Animation anim) {
		startActivity(new Intent(this, MenuActivity.class));// �ִϸ��̼� ������ �޴���Ƽ��Ƽ��
															// �̵�
		finish();// ��ŸƮ��Ƽ��Ƽ�� ����
	}

	public void onAnimationRepeat(Animation anim) {
	}

	public void onAnimationStart(Animation anim) {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

}
