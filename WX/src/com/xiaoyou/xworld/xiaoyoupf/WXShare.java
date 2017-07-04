package com.xiaoyou.xworld.xiaoyoupf;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xiaoyou.xworld.xiaoyoupf.utils.Utils;

public class WXShare {
	public static void GetUnityContext(Context context){
		Log.i("WX", ">>>Enter GetUnityContext");
		Constant.unityContext = context.getApplicationContext();
		Constant.unityActivity = (Activity)context;
	}
	
	public static void Init(String appId){
		Log.i("WX", ">>>Enter Init");
		Constant.appId = appId;
		Constant.wxAPI = WXAPIFactory.createWXAPI(Constant.unityContext, Constant.appId, true);
		Constant.wxAPI.registerApp(Constant.appId); 		
	}
	
    public static void ShareText(String text){
    	Log.i("WX", ">>>Enter ShareText");
    	WXTextObject textObj = new WXTextObject();
    	textObj.text = text;
    	
    	WXMediaMessage msg = new WXMediaMessage();
    	msg.mediaObject = textObj;
    	msg.description = text;
    	
    	SendMessageToWX.Req req = new SendMessageToWX.Req();
    	req.transaction = Utils.BuildTransaction("text");
    	
    	req.message = msg;
    	req.scene = SendMessageToWX.Req.WXSceneTimeline;
    	
    	Constant.wxAPI.sendReq(req);    	
    }
    
    public static void ShareImage(byte[] bytes){
    	Log.i("WX", ">>>Enter ShareImage");
    	Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    	WXImageObject imgObj = new WXImageObject(bitmap);
    	WXMediaMessage msg = new WXMediaMessage();
    	msg.mediaObject = imgObj;
    	
    	
    	Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 300, 200, true);
    	bitmap.recycle();
    	msg.thumbData = Utils.BmpToByteArray(thumbBmp, true);
    	
    	SendMessageToWX.Req req = new SendMessageToWX.Req();
    	req.transaction = Utils.BuildTransaction("img");
    	
    	req.message = msg;
    	req.scene = SendMessageToWX.Req.WXSceneSession;
    	
    	Constant.wxAPI.sendReq(req);
    }
    
    public static void ShareAudio(){
    	
    }
    
    public static void ShareVedio(){
    	
    }
    
    public static void ShareHtml(){
    	
    }
    
}
