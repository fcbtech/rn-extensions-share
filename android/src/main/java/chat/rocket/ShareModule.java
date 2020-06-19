package chat.rocket;

import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import android.graphics.Bitmap;
import java.io.InputStream;

import java.io.File;
import java.util.ArrayList;

import javax.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ShareModule extends ReactContextBaseJavaModule {

  private File tempFolder;
  ReactApplicationContext context;
  public ShareModule(ReactApplicationContext reactContext) {
      super(reactContext);
      context=reactContext;
  }

  @Override
  public String getName() {
      return "ReactNativeShareExtension";
  }

  @ReactMethod
  public void close() {
    Activity currentActivity = getCurrentActivity();
    if (currentActivity != null){
      currentActivity.finish();
    }
  }

  @ReactMethod
  public void data(Promise promise) {
      promise.resolve(processIntent());
  }

  public WritableMap processIntent() {
        WritableMap map = Arguments.createMap();

        String text = "";
        String type = "";
        String action = "";

        Activity currentActivity = getCurrentActivity();

        if (currentActivity != null) {
            this.tempFolder = new File(currentActivity.getCacheDir(), "rcShare");
            Intent intent = currentActivity.getIntent();
            action = intent.getAction();
            type = intent.getType();
            if (type == null) {
                type = "";
            }
            map.putString("type", type);

            if (Intent.ACTION_SEND.equals(action) && "text/plain".equals(type)) {
                text = intent.getStringExtra(Intent.EXTRA_TEXT);
                map.putString("value", text);
            } else if (Intent.ACTION_SEND.equals(action)) {
                Uri uri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
                if (uri != null) {
                    map.putString("filepath", RealPathUtil.getRealPathFromURI(context,uri));
                    map.putString("value", uri.toString());
                    // Log.d("File Name ", RealPathUtil.getRealPathFromURI(context,uri));
                }
            }
        }

        return map;
    }
}
