package com.codeb.cordova.plugins.settingsplugin;
 
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.net.wifi.WifiManager;
import android.bluetooth.BluetoothAdapter;
import android.util.Log;
import android.view.WindowManager;
import android.view.Window;
import android.provider.Settings;

/**
 * This class echoes a string called from JavaScript.
 */
public class SettingsPlugin extends CordovaPlugin {

	private static final String LOG_TAG = "Settings Plugin";
	
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		try {
			JSONObject arg_object = args.getJSONObject(0);
			if (action.equals("getBluetooth")) {
				//String message = arg_object.getString("action");
				this.getBluetooth(callbackContext);
				return true;
			} else if (action.equals("setBluetooth")) {
				String message = arg_object.getString("action");
				this.setBluetooth(message, callbackContext);
				return true;
			} else if (action.equals("getAutoRotate")) {
				//String message = arg_object.getString("action");
				this.getAutoRotate(callbackContext);
				return true;
			} else if (action.equals("setAutoRotate")) {
				String message = arg_object.getString("action");
				this.setAutoRotate(message, callbackContext);
				return true;			
			} else if (action.equals("setBrightness")) {
				String message = arg_object.getString("action");
				this.setBrightness(message, callbackContext);
				return true;			
			}  else if (action.equals("getBrightness")) {
				String message = arg_object.getString("action");
				this.getBrightness(message, callbackContext);
				return true;			
			} else {
				Log.d(LOG_TAG, "invalid action");
				callbackContext.error("invalid action");
			}
			return false;
		} catch(Exception e) {
            callbackContext.error(e.getMessage());
            return false;
		}
    }
	
	
	
	
	private void getBluetooth(CallbackContext callbackContext) {
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		boolean result = bluetoothAdapter.isEnabled();
		Log.d(LOG_TAG, "Bluetooth enabled: " + result);
		callbackContext.success(Boolean.toString(result));
	}

    private void setBluetooth(String action, CallbackContext callbackContext) {
        Log.d(LOG_TAG, "Execute setBluetooth");
		if (action != null && action.length() > 0) {
           if (action.equals("activate")) {
				BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
				mBluetoothAdapter.enable();
				callbackContext.success("enabled");
		   } else if (action.equals("deactivate")) {
				BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
				mBluetoothAdapter.disable();
				callbackContext.success("disabled");
		   } else {
				callbackContext.error("Expected true / false string argument.");
		   }
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
	
	private void setBrightness(String action, CallbackContext callbackContext) {
		Log.d(LOG_TAG, "Execute setBrightness");
		if (action != null && action.length() > 0) {
		   if (action.equals("activate")) {
			   float brightness = 1F;
			   int brightnessInt = (int)(brightness*255);

			   Activity activity = this.cordova.getActivity();
			   Settings.System.putInt(activity.getContentResolver(),
					   Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
					   Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightnessInt);
			   
			   cordova.getActivity().runOnUiThread(new Runnable() {
		            public void run() {		            	
		            	float brightness = 1F;
		            	Activity activity = cordova.getActivity();
		            	activity.getWindow().getAttributes();
		            	WindowManager.LayoutParams layout = activity.getWindow().getAttributes();
		 				layout.screenBrightness = brightness;
		 				activity.getWindow().setAttributes(layout);				
		            }
		        }); 
			   callbackContext.success("enabled");
				
		   } else if (action.equals("deactivate")) {
			   float brightness = 0.2F;
			   int brightnessInt = (int)(brightness*255);
			   
			   Activity activity = this.cordova.getActivity();
			   Settings.System.putInt(activity.getContentResolver(),
					   Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
					   Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightnessInt);
			   
			   cordova.getActivity().runOnUiThread(new Runnable() {
		            public void run() {
		            	float brightness = 0.2F;		            	
		            	Activity activity = cordova.getActivity();
		            	activity.getWindow().getAttributes();
		            	WindowManager.LayoutParams layout = activity.getWindow().getAttributes();
		 				layout.screenBrightness = brightness;
		 				activity.getWindow().setAttributes(layout);				
		            }
		        }); 
			   callbackContext.success("disabled"); 
		   } else {
				callbackContext.error("Expected true / false string argument.");
		   }
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }	
	}
	
	private void getBrightness(String action, CallbackContext callbackContext) {
        Log.d(LOG_TAG, "Execute getBrightness");
		if (action != null && action.length() > 0) {
			Activity activity = this.cordova.getActivity();
			try {
				int brightness = Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
				Log.e(LOG_TAG, Integer.toString(brightness));
			} catch (Settings.SettingNotFoundException e) {
		        Log.e(LOG_TAG, e.toString());
		    }
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
	
	private void getAutoRotate(CallbackContext callbackContext) {
		Activity activity = this.cordova.getActivity();
		boolean result = Settings.System.getInt(activity.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
		Log.d(LOG_TAG, "Auto Rotate enabled: " + result);
		callbackContext.success(Boolean.toString(result));
	}

    private void setAutoRotate(String action, CallbackContext callbackContext) {
        Log.d(LOG_TAG, "Execute setAutoRotate");
		if (action != null && action.length() > 0) {
           if (action.equals("activate")) {
				Activity activity = this.cordova.getActivity();
				Settings.System.putInt(activity.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1);			
				callbackContext.success("enabled");
		   } else if (action.equals("deactivate")) {
				Activity activity = this.cordova.getActivity();
				Settings.System.putInt(activity.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
				callbackContext.success("disabled");
		   } else {
				callbackContext.error("Expected true / false string argument.");
		   }
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

	
}

