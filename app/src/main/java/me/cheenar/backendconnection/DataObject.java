package me.cheenar.backendconnection;

import android.graphics.Bitmap;

import org.json.JSONObject;

public class DataObject {
    private String mText1;
    private String mText2;
    private Bitmap bitmap;
    private JSONObject object;
 
    DataObject (JSONObject object,String text1, String text2, Bitmap bitmap){
        mText1 = text1;
        this.object = object;
        mText2 = text2;
        this.bitmap = bitmap;;
    }

    public JSONObject getObject()
    {
        return object;
    }

    public Bitmap getBitmap()
    {
        return bitmap;
    }
 
    public String getmText1() {
        return mText1;
    }
 
    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }
 
    public String getmText2() {
        return mText2;
    }
 
    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }
}