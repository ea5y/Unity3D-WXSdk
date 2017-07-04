package com.xiaoyou.xworld.xiaoyoupf;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.xiaoyou.xworld.xiaoyoupf.R;
import com.xiaoyou.xworld.xiaoyoupf.wxapi.WXEntryActivity;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity  {
	private static MainActivity instance;
	
	public static MainActivity GetInstance(){
		if(instance == null)
			instance = new MainActivity();
		return instance;
	}
	
	public void GetUnityContext(Context context){
		Constant.unityContext = context.getApplicationContext();
		Constant.unityActivity = (Activity)context;
	}
	
	public void Init(String appId){
		Constant.appId = appId;
		Constant.wxAPI = WXAPIFactory.createWXAPI(Constant.unityContext, Constant.appId, true);
		Constant.wxAPI.registerApp(Constant.appId); 		
	}
	
    public void ShareText(String text){
    	Log.i("ShareText", text);
    	WXTextObject textObj = new WXTextObject();
    	textObj.text = text;
    	
    	WXMediaMessage msg = new WXMediaMessage();
    	msg.mediaObject = textObj;
    	msg.description = text;
    	
    	SendMessageToWX.Req req = new SendMessageToWX.Req();
    	req.transaction = BuildTransaction("text");
    	
    	req.message = msg;
    	req.scene = SendMessageToWX.Req.WXSceneTimeline;
    	
    	Constant.wxAPI.sendReq(req);    	
    }
    
    public void ShareImage(byte[] bytes){
    	Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    	WXImageObject imgObj = new WXImageObject(bitmap);
    	WXMediaMessage msg = new WXMediaMessage();
    	msg.mediaObject = imgObj;
    	
    	
    	Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 300, 200, true);
    	bitmap.recycle();
    	msg.thumbData = BmpToByteArray(thumbBmp, true);
    	
    	SendMessageToWX.Req req = new SendMessageToWX.Req();
    	req.transaction = BuildTransaction("img");
    	
    	req.message = msg;
    	req.scene = SendMessageToWX.Req.WXSceneSession;
    	
    	Constant.wxAPI.sendReq(req);
    }
    
    public byte[] BmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}
		
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
        
    public void ShareAudio(){
    	
    }
    
    public void ShareVedio(){
    	
    }
    
    public void ShareHtml(){
    	
    }
    
    
    private String BuildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    
    private void ShowMessage(final String msg){
    	Constant.unityActivity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(Constant.unityContext, msg, Toast.LENGTH_LONG).show();
			}
		});
    }
    
}
