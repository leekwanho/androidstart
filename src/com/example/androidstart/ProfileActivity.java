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
		nameText.setText(receiveText("name"));//SharedPreferences���� �޾ƿ�
		nameText.setOnKeyListener(myKeyListener);//������ ���

		emailText=(EditText)findViewById(R.id.email);
		emailText.setText(receiveText("email"));
		emailText.setOnKeyListener(myKeyListener);
		
		save=(Button)findViewById(R.id.save_button);
		
		imageButton=(ImageButton)findViewById(R.id.button_avatar);
			
		imgUri = Uri.parse(receiveText("uri"));//SharedPreferences���� �޾ƿ� Uri�� ����
		imageButton.setImageURI(imgUri);//�̹������
		
		
		
		//�̹�����ū ��Ŭ��. ī�޶�ȣ��
		imageButton.setOnLongClickListener(new Button.OnLongClickListener(){

			@Override
			public boolean onLongClick(View v) {
				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
						startActivityForResult(intent, 1);//ī�޶�. 1��
				
				
				return false;
			}
			
		});
		
		
		
	}
	
	
	public void mOnClick(View v){
		switch(v.getId()){
		case R.id.save_button: //�̸�, �̸��� ����

			nameText=(EditText)findViewById(R.id.name);
			emailText=(EditText)findViewById(R.id.email);
			String name=nameText.getText().toString();	
			String email=emailText.getText().toString();

			saveText("name", name);
			saveText("email",email);
			
			break;
			
		case R.id.button_avatar://ª�� Ŭ��. ������ ȣ��
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");
			startActivityForResult(intent, 0);//0��
			break;
		}	
	}
	
	
	
	@SuppressLint("SdCardPath")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode==RESULT_OK&&requestCode==0){//������ȣ��
			
			Bitmap imgBitmap = null;
			imgUri = data.getData();//�̹��� �޾ƿ�
			
			//�̹��� ��Ʈ������ ��ȯ
			try {
				imgBitmap = Media.getBitmap(getContentResolver(), imgUri);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//�̹��� ��ư ����
			imageButton.setImageBitmap(imgBitmap);
			
			
			//�̹���uri ������Ʈ���� ����
			try{
				//�̹��� ����
				imgBitmap.compress(CompressFormat.JPEG, 100, openFileOutput("imgBitmap.jpg", MODE_PRIVATE));
				//uri����
				saveText("uri","/data/data/com.example.androidstart/files/imgBitmap.jpg");//save
							
			}catch(Exception e){}
			
			
			
			
			
		}
		
		
		else if(resultCode==RESULT_OK&&requestCode==1){
			imgUri = data.getData();
			//saveText("uri",imgUri.toString());
			
			Bitmap imgBitmap = (Bitmap) data.getExtras().get("data");
			imageButton.setImageBitmap(imgBitmap);
			

			//�̹���uri ������Ʈ���� ����
			try{
				//�̹��� ����
				imgBitmap.compress(CompressFormat.JPEG, 100, openFileOutput("imgBitmap.jpg", MODE_PRIVATE));
				//uri����
				saveText("uri","/data/data/com.example.androidstart/files/imgBitmap.jpg");//save
							
			}catch(Exception e){}
			
		}
		
		
	}
	
	
	
	//����Ű ������ ��ưŬ���ϱ� ����
	OnKeyListener myKeyListener=new OnKeyListener(){

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if(event.getAction()==KeyEvent.KEYCODE_ENTER){
				Button save=(Button)findViewById(R.id.save_button);
				save.performClick();//save��ư Ŭ��
			}
			
			return false;
		}
				
	};
	
	//SharedPreferences ����
	private void saveText(String key,String value){
		Editor editor=setting.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	//SharedPreferences �ҷ�����
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




