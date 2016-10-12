package com.wuanan.frostmaki.wuanlife_113.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.wuanan.frostmaki.wuanlife_113.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ImageLoader
{
	private ImageView mImageView;
	private ImageButton mImageButton;
	private String murl;
	private Bitmap mbitmap;
	private	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO: Implement this method
			if (msg.what == 0) {
				if (mImageView.getTag().equals(murl)) {
					mImageView.setImageBitmap(mbitmap);

				}
			}
			if (msg.what==1){
				if (mImageButton.getTag().equals(murl)) {
					mImageButton.setImageBitmap(mbitmap);

				}
			}
		}

	};
	public void showImageByThread(ImageView imageview, String url) {
		mImageView = imageview;
		murl = url;
		mbitmap = null;
		new Thread() {
			public void run() {
				super.run();
				Bitmap bitmap = getBitmapFromUrl(murl);
				mbitmap=bitmap;
				Message message = Message.obtain();
				message.obj = bitmap;
				message.what=0;
				handler.sendMessage(message);
			}
		}.start();
	}
	public void showImageByThreadImageButton(ImageButton imageButton, String url) {
		mImageButton = imageButton;
		murl = url;
		mbitmap = null;
		new Thread() {
			public void run() {
				super.run();
				Bitmap bitmap = getBitmapFromUrl(murl);
				mbitmap=bitmap;
				Message message = Message.obtain();
				message.obj = bitmap;
				message.what=1;
				handler.sendMessage(message);
			}
		}.start();
	}
	public Bitmap getBitmapFromUrl(String urlString) {
		Bitmap bitmap;
		InputStream is = null ;
		try
		{
			String str = URLEncoder.encode(urlString, "UTF-8");
			String strURL = str.replaceAll("%3A", ":").replaceAll("%2F", "/").replace("%3F","?");
			Log.e("ImageLaoder  strURL  ", strURL);
			URL url=new URL(strURL);

				HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(3000);
				is=new BufferedInputStream(connection.getInputStream());
				bitmap=BitmapFactory.decodeStream(is);
			connection.disconnect();
			try
			{
				Thread.sleep(500);
			}
			catch (InterruptedException e)
			{}
			return bitmap;			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
		
	}
}

