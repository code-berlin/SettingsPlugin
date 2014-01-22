package com.codeb.cordova.plugins.settingsplugin;
 
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
//import android.os.Bundle;
//import android.net.wifi.WifiManager;
import android.bluetooth.BluetoothAdapter;
import android.util.Log;
import android.view.WindowManager;
//import android.view.Window;
import android.provider.Settings;

/**
 * This class echoes a string called from JavaScript.
 */
public class SettingsPlugin extends CordovaPlugin {

	private static final String LOG_TAG = "Settings Plugin";
	
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    	Log.d(LOG_TAG, "execute");
    	try {
			if (action.equals("getBluetooth")) {
				this.getBluetooth(callbackContext, args);
				return true;
			} else if (action.equals("setBluetooth")) {
				this.setBluetooth(action, callbackContext, args);
				return true;
			} else if (action.equals("getAutoRotate")) {
				this.getAutoRotate(callbackContext, args);
				return true;
			} else if (action.equals("setAutoRotate")) {
				this.setAutoRotate(action, callbackContext, args);
				return true;			
			} else if (action.equals("setBrightness")) {
				this.setBrightness(action, callbackContext, args);
				return true;			
			}  else if (action.equals("getBrightness")) {
				this.getBrightness(action, callbackContext, args);
				return true;			
			} else {
				Log.d(LOG_TAG, "invalid action");
				callbackContext.error("invalid action");
			}
			return false;
		} catch(Exception e) {
			Log.d(LOG_TAG, e.toString());
			callbackContext.error(e.getMessage());
            return false;
		}
    }
	
	
	
	
	private void getBluetooth(CallbackContext callbackContext, JSONArray args) {
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		boolean result = bluetoothAdapter.isEnabled();
		Log.d(LOG_TAG, "Bluetooth enabled: " + result);	
		String cb = "";
		try {
			JSONObject arg_object = args.getJSONObject(0);
			cb = arg_object.getString("cb");
		} catch (Exception e) {	
			Log.d(LOG_TAG, e.toString());
			Log.d(LOG_TAG, args.toString());
		}			
		callbackContext.success("{\"result\":\"" + Boolean.toString(result) + "\",\"component\":\"blueetooth\",\"cb\":\""+ cb + "\"}");
	}

    private void setBluetooth(String action, CallbackContext callbackContext, JSONArray args) {
        Log.d(LOG_TAG, "Execute setBluetooth");
        String cb = "";
		String val = "";
		if (action != null && action.length() > 0) {	
			try {
				JSONObject arg_object = args.getJSONObject(0);
				cb = arg_object.getString("cb");
				val = arg_object.getString("value");
			} catch (Exception e) {
				Log.d(LOG_TAG, e.toString());
				Log.d(LOG_TAG, args.toString());
			}
			if (val.equals("1")) {
				BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
				mBluetoothAdapter.enable();
				callbackContext.success("{\"result\":\"1\",\"component\":\"blueetooth\",\"cb\":\""+ cb + "\"}");
		   } else if (val.equals("0")) {
				BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
				mBluetoothAdapter.disable();
				callbackContext.success("{\"result\":\"0\",\"component\":\"blueetooth\",\"cb\":\""+ cb + "\"}");
		   } else {
				callbackContext.error("Expected true / false string argument.");
		   }
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
	
	private void setBrightness(String action, CallbackContext callbackContext, JSONArray args) {
		Log.d(LOG_TAG, "Execute setBrightness");
		String cb = "";
		String val = "";
		if (action != null && action.length() > 0) {	
			try {
				JSONObject arg_object = args.getJSONObject(0);
				cb = arg_object.getString("cb");
				val = arg_object.getString("value");
			} catch (Exception e) {
				Log.d(LOG_TAG, e.toString());
				Log.d(LOG_TAG, args.toString());
			}
			if (val.equals("1")) {
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
			   callbackContext.success("{\"result\":\"1\",\"component\":\"brightness\",\"cb\":\""+ cb + "\"}");
				
		   } else if (val.equals("0")) {
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
			   callbackContext.success("{\"result\":\"0\",\"component\":\"brightness\",\"cb\":\""+ cb + "\"}"); 
		   } else {
				callbackContext.error("Expected true / false string argument.");
		   }
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }	
	}
	
	private void getBrightness(String action, CallbackContext callbackContext, JSONArray args) {
        Log.d(LOG_TAG, "Execute getBrightness");
		if (action != null && action.length() > 0) {
			String cb = "";
			try {
				JSONObject arg_object = args.getJSONObject(0);
				cb = arg_object.getString("cb");
			} catch (Exception e) {	
				Log.d(LOG_TAG, e.toString());
				Log.d(LOG_TAG, args.toString());
			}
			Activity activity = this.cordova.getActivity();
			try {
				int brightness = Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
				Log.e(LOG_TAG, Integer.toString(brightness));
				callbackContext.success("{\"result\":\"" + Integer.toString(brightness) + "\",\"component\":\"brightness\",\"cb\":\""+ cb + "\"}"); 
			} catch (Settings.SettingNotFoundException e) {
		        Log.e(LOG_TAG, e.toString());
		    }
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
	
	private void getAutoRotate(CallbackContext callbackContext, JSONArray args) {
		Activity activity = this.cordova.getActivity();
		boolean result = Settings.System.getInt(activity.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
		Log.d(LOG_TAG, "Auto Rotate enabled: " + result);
		String cb = "";
		try {
			JSONObject arg_object = args.getJSONObject(0);
			cb = arg_object.getString("cb");
		} catch (Exception e) {	
			Log.d(LOG_TAG, e.toString());
			Log.d(LOG_TAG, args.toString());
		}
		callbackContext.success("{\"result\":\"" + Boolean.toString(result) + "\",\"component\":\"autoRotate\",\"cb\":\""+ cb + "\"}");
	}

    private void setAutoRotate(String action, CallbackContext callbackContext, JSONArray args) {
        Log.d(LOG_TAG, "Execute setAutoRotate");
        String cb = "";
		String val = "";
        if (action != null && action.length() > 0) {
        	try {
        		JSONObject arg_object = args.getJSONObject(0);
				cb = arg_object.getString("cb");
				val = arg_object.getString("value");
    		} catch (Exception e) {			
    			Log.d(LOG_TAG, e.toString());
    			Log.d(LOG_TAG, args.toString());
    		}
        	if (val.equals("1")) {
				Activity activity = this.cordova.getActivity();
				Settings.System.putInt(activity.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1);			
				callbackContext.success("{\"result\":\"1\",\"component\":\"autoRotate\",\"cb\":\""+ cb + "\"}");
		   } else if (val.equals("0")) {
				Activity activity = this.cordova.getActivity();
				Settings.System.putInt(activity.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
				callbackContext.success("{\"result\":\"0\",\"component\":\"autoRotate\",\"cb\":\""+ cb + "\"}");
		   } else {
				callbackContext.error("Expected true / false string argument.");
		   }
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

	
}

