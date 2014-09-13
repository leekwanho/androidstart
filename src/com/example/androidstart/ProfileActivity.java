package com.example.androidstart;

import java.io.FileNotFoundException;
import java.io.IOException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends Activity {

	EditText nameText;
	EditText emailText;
	ImageButton imageButton;
	SharedPreferences setting;
	Button save;
	Uri imgUri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		setting=getSharedPreferences("MyPreference",MODE_PRIVATE);

		nameText=(EditText)findViewById(R.id.name);
		nameText.setText(receiveText("name"));//SharedPreferences에서 받아옴
		nameText.setOnKeyListener(myKeyListener);//리스너 등록

		emailText=(EditText)findViewById(R.id.email);
		emailText.setText(receiveText("email"));
		emailText.setOnKeyListener(myKeyListener);
		
		save=(Button)findViewById(R.id.save_button);
		
		imageButton=(ImageButton)findViewById(R.id.button_avatar);
			
		imgUri = Uri.parse(receiveText("uri"));//SharedPreferences에서 받아와 Uri로 저장
		imageButton.setImageURI(imgUri);//이미지등록
		
		
		
		//이미지버큰 롱클릭. 카메라호출
		imageButton.setOnLongClickListener(new Button.OnLongClickListener(){

			@Override
			public boolean onLongClick(View v) {
				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
						startActivityForResult(intent, 1);//카메라. 1번
				
				
				return false;
			}
			
		});
		
		
		
	}
	
	
	public void mOnClick(View v){
		switch(v.getId()){
		case R.id.save_button: //이름, 이메일 저장

			nameText=(EditText)findViewById(R.id.name);
			emailText=(EditText)findViewById(R.id.email);
			String name=nameText.getText().toString();	
			String email=emailText.getText().toString();

			saveText("name", name);
			saveText("email",email);
			
			break;
			
		case R.id.button_avatar://짧은 클릭. 갤러리 호출
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");
			startActivityForResult(intent, 0);//0번
			break;
		}	
	}
	
	
	
	@SuppressLint("SdCardPath")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode==RESULT_OK&&requestCode==0){//갤러리호출
			
			Bitmap imgBitmap = null;
			imgUri = data.getData();//이미지 받아옴
			
			//이미지 비트맵으로 변환
			try {
				imgBitmap = Media.getBitmap(getContentResolver(), imgUri);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//이미지 버튼 세팅
			imageButton.setImageBitmap(imgBitmap);
			
			
			//이미지uri 오브젝트통해 저장
			try{
				//이미지 포맷
				imgBitmap.compress(CompressFormat.JPEG, 100, openFileOutput("imgBitmap.jpg", MODE_PRIVATE));
				//uri저장
				saveText("uri","/data/data/com.example.androidstart/files/imgBitmap.jpg");//save
							
			}catch(Exception e){}
			
			
			
			
			
		}
		
		
		else if(resultCode==RESULT_OK&&requestCode==1){
			imgUri = data.getData();
			//saveText("uri",imgUri.toString());
			
			Bitmap imgBitmap = (Bitmap) data.getExtras().get("data");
			imageButton.setImageBitmap(imgBitmap);
			

			//이미지uri 오브젝트통해 저장
			try{
				//이미지 포맷
				imgBitmap.compress(CompressFormat.JPEG, 100, openFileOutput("imgBitmap.jpg", MODE_PRIVATE));
				//uri저장
				saveText("uri","/data/data/com.example.androidstart/files/imgBitmap.jpg");//save
							
			}catch(Exception e){}
			
		}
		
		
	}
	
	
	
	//엔터키 눌르면 버튼클릭하기 구현
	OnKeyListener myKeyListener=new OnKeyListener(){

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if(event.getAction()==KeyEvent.KEYCODE_ENTER){
				Button save=(Button)findViewById(R.id.save_button);
				save.performClick();//save버튼 클릭
			}
			
			return false;
		}
				
	};
	
	//SharedPreferences 저장
	private void saveText(String key,String value){
		Editor editor=setting.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	//SharedPreferences 불러오기
	private String receiveText(String input){
		String initText="";
		if(setting.contains(input)){
			initText=setting.getString(input, "");
		}
		
		return initText;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	
	
}




