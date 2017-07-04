package com.xiaoyou.xworld.xiaoyoupf.wxapi;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xiaoyou.xworld.xiaoyoupf.Constant;
import com.xiaoyou.xworld.xiaoyoupf.MainActivity;
import com.xiaoyou.xworld.xiaoyoupf.utils.HttpRequest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		Log.i("OnCreate", "===========>OnCreate WXEntryActivity!");
		super.onCreate(savedInstanceState);
		this.finish();
		Constant.wxAPI = WXAPIFactory.createWXAPI(this, Constant.appId, false);
		Constant.wxAPI.registerApp(Constant.appId); 
		Constant.wxAPI.handleIntent(getIntent(), this);		
	}
	
	@Override
	protected void onNewIntent(Intent intent){
		super.onNewIntent(intent);
		setIntent(intent);
		Constant.wxAPI = WXAPIFactory.createWXAPI(this, Constant.appId, false);
		Constant.wxAPI.registerApp(Constant.appId); 
		Constant.wxAPI.handleIntent(intent, this);
		Log.i("OnNewIntent", "===========>OnNewIntent!");
	}

	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub
		Log.i("OnReq", "===========>OnReq!");
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.i("OnResp", "===========>OnResp!");
		
		if(resp instanceof SendAuth.Resp){
			this.OnLoginResp((SendAuth.Resp)resp);
		}
		
		if(resp instanceof SendMessageToWX.Resp){
			this.OnShareResp((SendMessageToWX.Resp)resp);
		}	
		
		this.finish();
	}
	
	private void OnLoginResp(SendAuth.Resp resp){
		String result = "";
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = ">>>Login Success!";
			//Need to write http manager and token manager
			String str = HttpRequest.sendGet("https://api.weixin.qq.com/sns/oauth2/access_token"
					, "appid=" + Constant.appId + "&secret=" + Constant.secret + "&code" + resp.code + "&grant_type=authorization_code");
			result = str;
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = ">>>Login Cancel!";
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = ">>>Login Refuse!";
			break;
			
		default:
			result = "Back!";
			break;
		}
		if(Constant.isDebug){
			Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
		}
	}
	
	private void OnShareResp(SendMessageToWX.Resp resp){
		String result = "";
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = ">>>Share Success!";
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = ">>>Share Cancel!";
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = ">>>Share Refuse!";
			break;
			
		default:
			result = "Back!";
			break;
		}
		if(Constant.isDebug){
			Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
		}
	}

}
