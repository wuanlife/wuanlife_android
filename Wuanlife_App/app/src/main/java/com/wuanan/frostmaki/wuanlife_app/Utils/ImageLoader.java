package com.wuanan.frostmaki.wuanlife_app.Utils;
import android.util.Log;
import android.widget.*;
import android.graphics.*;
import java.net.*;
import java.io.*;
import android.os.*;

public class ImageLoader
{
	private ImageView mImageView;
	private String murl;
	private	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg)
		{
			// TODO: Implement this method
			super.handleMessage(msg);
			if(mImageView.getTag().equals(murl)){
			mImageView.setImageBitmap((Bitmap)msg.obj);
			}
		}

	
	
};
	public void showImageByThread(ImageView imageview,final String url){
		mImageView=imageview;
		murl=url;
		new Thread(){
		public void run(){
			super.run();
	 Bitmap bitmap=
			 getBitmapFromUrl(url);
	 Message message=Message.obtain();
	 message.obj=bitmap;
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
			String strURL = str.replaceAll("%3A", ":").replaceAll("%2F", "/");
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

