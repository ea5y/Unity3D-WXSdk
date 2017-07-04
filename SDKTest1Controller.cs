using UnityEngine;
using System.Collections;

public class SDKTest1Controller : MonoBehaviour {
    private AndroidJavaObject currentActivity;

    private AndroidJavaClass wxShare;
    private AndroidJavaClass wxLogin;
    private AndroidJavaClass wxPay;

    private string appId = "wx3f8bf58d6211872c";

    public static SDKTest1Controller Instance;


    private void Awake()
    {
        Instance = this;

        //Get currentActivity
        using(var unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer"))
        {
            this.currentActivity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
        }

        //Get wx Instance
        this.wxShare = new AndroidJavaClass("com.xiaoyou.xworld.xiaoyoupf.WXShare");
        this.wxShare.CallStatic("GetUnityContext", this.currentActivity);

        this.wxShare.CallStatic("Init", appId);
    }

    #region Login
    public void Login()
    {

    }
    #endregion

    #region Pay
    public void Pay()
    {

    }
    #endregion

    #region Share
    public void ShareText()
    {
        this.wxShare.CallStatic("ShareText", "test");
    }

    public void ShareImage()
    {
        var bytes = this.CaptureScreenshot(new Rect(0, 0, Screen.width, Screen.height));
        this.wxShare.CallStatic("ShareImage", bytes);
    }

    private byte[] CaptureScreenshot(Rect rect)
    {
        Texture2D screenShot = new Texture2D((int)rect.width, (int)rect.height, TextureFormat.RGB24, false);

        screenShot.ReadPixels(rect, 0, 0);
        screenShot.Apply();

        var result = screenShot.EncodeToPNG();
        return result;
    }

    private void OnGUI()
    {
        if (GUI.Button(new Rect(20, 40, 180, 60), "ShareText"))
        {
            this.ShareText();
        }

        if (GUI.Button(new Rect(20, 110, 180, 60), "ShareImage"))
        {
            this.ShareImage();
        }
    }
    #endregion
}
