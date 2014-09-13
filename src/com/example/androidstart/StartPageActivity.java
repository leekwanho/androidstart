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
	// 세로일때는 tableAni1~2 를 사용(테이블로우 기준이므로)
	// 가로일떄는 테이블로우가 한개이므로 이미지별 설정(imageVeiw1~4)
	Animation textAni1, textAni2, imageAni1, imageAni2, tableAni1, tableAni2;
	ImageView imageView1, imageView2, imageView3, imageView4;
	TableRow tableRow1, tableRow2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_page);

		// 각 속성을 찾아 연결
		textView1 = (TextView) findViewById(R.id.textview1);// 텍스트뷰는 가로세로 모두
															// 공통으로 천천히 나타나기만 하는
															// 애니메이션 사용
		textView2 = (TextView) findViewById(R.id.textview2);
		imageView1 = (ImageView) findViewById(R.id.imageview1);// 이미지뷰는 가로모드일시
																// 테이블로우가 하나이므로
																// 따로따로출력을 위해
																// 각각에 애니매이션 적용
		imageView2 = (ImageView) findViewById(R.id.imageview2);
		imageView3 = (ImageView) findViewById(R.id.imageview3);
		imageView4 = (ImageView) findViewById(R.id.imageview4);

		tableRow1 = (TableRow) findViewById(R.id.tablerow);// 테이블로우는 세로모드일시
															// 테이블로우별 애니매이션
															// 적용이므로 테이블로우별로
															// 애니메이션 적용
		tableRow2 = (TableRow) findViewById(R.id.tablerow2);

		// 애니매이션을 각각 연결
		textAni1 = AnimationUtils.loadAnimation(this, R.anim.text_anim);// 텍스트뷰
																		// 애니매이션
																		// 설정
																		// 단순히
																		// 천천히
																		// 나오는걸
																		// 구현
		textAni2 = AnimationUtils.loadAnimation(this, R.anim.text_anim2);// 3초뒤
																			// 나오는
																			// 텍스트뷰
																			// 애니메이션
		imageAni1 = AnimationUtils.loadAnimation(this, R.anim.custom_anim);// 가로일때의
																			// 이미지
																			// 애니매이션.
																			// 회전하면서
																			// 천천히
																			// 나옴
		imageAni2 = AnimationUtils.loadAnimation(this, R.anim.custom_anim2);// 1은
																			// 0~3초에
																			// 나타나고
																			// 2는
																			// 3~6초에
																			// 시간차를
																			// 두고
																			// 나타남
		tableAni1 = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
		tableAni2 = AnimationUtils.loadAnimation(this, R.anim.custom_anim2);

		textView1.startAnimation(textAni1);// 가로세로 모두 텍스트뷰는 똑같이 사용하므로 조건문앞에서 실행
		textView2.startAnimation(textAni2);// 아래의 텍스트뷰는 3초뒤 나타남

		// 화면고정
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {// 화면이
																									// 세로일시
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 세로로
																				// 고정하고

			tableRow1.startAnimation(tableAni1);// 세로이므로 테이블로우 기준으로 애니메이션 작동
			tableRow2.startAnimation(tableAni2);// 두번째 테이블로우는 3초뒤 나타남
		}

		else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 화면이
																										// 가로일시
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 가로
																				// 고정하고

			imageView1.startAnimation(imageAni1);// 가로는 테이블로우가 하나이므로 이미지별 애니메이션
													// 실행
			imageView2.startAnimation(imageAni1);// imageAni1은 바로 실행이므로 이미지뷰
													// 1~2이고
			imageView3.startAnimation(imageAni2);// 이미지뷰3~4는 3초뒤실행이므로
			imageView4.startAnimation(imageAni2);// imageAni2 사용
		}

		textAni2.setAnimationListener(this);// text2가 가장 마지막에 끝나는 애니메이션이므로
											// text2가 끝나면 액티비티 전환

	}

	public void onAnimationEnd(Animation anim) {
		startActivity(new Intent(this, MenuActivity.class));// 애니메이션 끝나면 메뉴엑티비티로
															// 이동
		finish();// 스타트엑티비티는 종료
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
