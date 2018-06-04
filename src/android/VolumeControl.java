package com.kit.cordova.volumeControl;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.media.AudioManager;

/**
 * This class echoes a string called from JavaScript.
 */
public class VolumeControl extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }
        if(action.equals("setVolume")){
          int vol =args.getInt(0);
          this.setVolume(vol,callbackContext);
          return true;
        }
        if(action.equals("getVolume")){
          this.getVolume(callbackContext);
          return true;
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void setVolume(int vol,CallbackContext callbackContext){
      try {
        AudioManager mAudioManager = (AudioManager) this.cordova.getActivity().getSystemService(Context.AUDIO_SERVICE);
        if (vol>0) {
	      int maxVal = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		  int parVol = new Double(maxVal*vol/100).intValue();
          mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, parVol, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
          callbackContext.success(vol+","+parVol);
        } else {
          callbackContext.error("音量值不能为空");
        }
      }catch (Exception e){
        callbackContext.error(e.getMessage());
      }

    }

    private void getVolume(CallbackContext callbackContext){
      try {
        AudioManager mAudioManager = (AudioManager) this.cordova.getActivity().getSystemService(Context.AUDIO_SERVICE);
        
		JSONObject json = new JSONObject();
        json.put("maxVol",mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
		json.put("currentVol", mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
		
		//返回jsonobject,jsonarray,int,string几种格式数据
        callbackContext.success(json);
		
	  }catch (Exception e){
        callbackContext.error(e.getMessage());
      }
    }
}
