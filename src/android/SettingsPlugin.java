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
				String message = arg_object.getString("action");
				this.getBluetooth(callbackContext);
				return true;
			} else if (action.equals("setBluetooth")) {
				String message = arg_object.getString("action");
				this.setBluetooth(message, callbackContext);
				return true;
			} else if (action.equals("getAutoRotate")) {
				String message = arg_object.getString("action");
				this.getAutoRotate(callbackContext);
				return true;
			} else if (action.equals("setAutoRotate")) {
				String message = arg_object.getString("action");
				this.setAutoRotate(message, callbackContext);
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

