package com.xiaoyou.xworld.xiaoyoupf;

import com.tencent.mm.sdk.modelmsg.SendAuth;

public class WXLogin {
	public static void Login(){
		SendAuth.Req req = new SendAuth.Req();
		
		req.scope = "snsapi_userinfo";
		req.state = "wechat_sdk_demo_test";
		Constant.wxAPI.sendReq(req);
	}
}
