package com.codeb.cordova.plugins.settingsplugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.WindowManager;

/**
 * This class echoes a string called from JavaScript.
 */
public class SettingsPlugin extends CordovaPlugin {

	private static final String TAG = "Settings Plugin";

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		Log.d(TAG, "execute");
		try {
			if (action.equals("getBluetooth")) {
				this.getBluetooth(callbackContext);
				return true;
			} else if (action.equals("setBluetooth")) {
				this.setBluetooth(callbackContext, args);
				return true;
			} else if (action.equals("getBrightness")) {
				this.getBrightness(callbackContext);
				return true;
			} else if (action.equals("setBrightness")) {
				this.setBrightness(callbackContext, args);
				return true;
			} else if (action.equals("getAutoRotate")) {
				this.getAutoRotate(callbackContext);
				return true;
			} else if (action.equals("setAutoRotate")) {
				this.setAutoRotate(callbackContext, args);
				return true;
			} else if (action.equals("getLocationServices")) {
				this.getLocationServices(callbackContext);
				return true;
			} else if (action.equals("setLocationServices")) {
				this.setLocationServices(callbackContext);
				return true;
			} else {
				Log.d(TAG, "invalid action");
				callbackContext.error("invalid action");
			}
			return false;
		} catch (Exception e) {
			Log.d(TAG, e.toString());
			callbackContext.error(e.getMessage());
			return false;
		}
	}

	/**
	 * Get Bluetooth on/off status.
	 * 
	 * @param callbackContext JavaScript callback context
	 */
	private void getBluetooth(CallbackContext callbackContext) {
		Log.d(TAG, "Execute getBluetooth");
		
		returnValue(callbackContext, "bluetooth", BluetoothAdapter.getDefaultAdapter().isEnabled());
	}

	/**
	 * Turn on/off Bluetooth.
	 * 
	 * @param callbackContext JavaScript callback context
	 * @param args Array should consist of one boolean value 
	 */
	private void setBluetooth(CallbackContext callbackContext, JSONArray args) {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		boolean enable;
		
		Log.d(TAG, "Execute setBluetooth");

		try {
			enable = args.getBoolean(0);
			if (enable) {
				mBluetoothAdapter.enable();
			} else {
				mBluetoothAdapter.disable();
			}
			
		} catch (JSONException e) {
			callbackContext.error("JSON Error");
			Log.w(TAG, e.getMessage());
			return;
		}
		
		returnValue(callbackContext, "bluetooth", enable);
	}

	/**
	 * Get brightness status.
	 * Returns a double value to JavaScript.
	 * 
	 * @param callbackContext JavaScript callback context
	 */
	private void getBrightness(CallbackContext callbackContext) {
		float brightness;

		Log.d(TAG, "Execute getBrightness");

		try {
			brightness = ((float) Settings.System.getInt(this.cordova.getActivity().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS)) / 255;
		} catch (SettingNotFoundException e1) {
			callbackContext.error("Setting not found on this device");
			Log.w(TAG, e1.getMessage());
			return;
		}

		returnValue(callbackContext, "brightness", brightness);
	}

	/**
	 * Turn display brightness up/down using a double value.
	 * 
	 * @param callbackContext JavaScript callback context
	 * @param args Array should consist of one double value between
	 */
	private void setBrightness(CallbackContext callbackContext, JSONArray args) {
		Log.d(TAG, "Execute setBrightness");

		try {
			final float brightness = (float) args.getDouble(0);
			int brightnessInt = (int) (brightness * 255);
			Activity activity = this.cordova.getActivity();
			Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
			Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightnessInt);
			
			this.cordova.getActivity().runOnUiThread(new Runnable() {
				public void run() {
					Activity activity = cordova.getActivity();
					activity.getWindow().getAttributes();
					WindowManager.LayoutParams layout = activity.getWindow().getAttributes();
					layout.screenBrightness = brightness;
					activity.getWindow().setAttributes(layout);
				}
			});

			returnValue(callbackContext, "brightness", brightness);
			
		} catch (JSONException e) {
			callbackContext.error("JSON Error");
			Log.w(TAG, e.getMessage());
			return;
		}
	}

	/**
	 * Get auto-rotate on/off status.
	 * 
	 * @param callbackContext JavaScript callback context
	 */
	private void getAutoRotate(CallbackContext callbackContext) {
		boolean isEnabled;
		
		Log.d(TAG, "Execute getAutoRotate");
		
		try {
			isEnabled = Settings.System.getInt(this.cordova.getActivity().getContentResolver(), Settings.System.ACCELEROMETER_ROTATION) == 1;
		} catch (SettingNotFoundException e1) {
			callbackContext.error("Setting not found on this device");
			Log.w(TAG, e1.getMessage());
			return;
		}
		
		returnValue(callbackContext, "autoRotate", isEnabled);
	}

	/**
	 * Turn on/off auto-rotate.
	 * 
	 * @param callbackContext JavaScript callback context
	 * @param args Array should consist of one boolean value 
	 */
	private void setAutoRotate(CallbackContext callbackContext, JSONArray args) {
		boolean enable;
		
		Log.d(TAG, "Execute setAutoRotate");

		try {
			enable = args.getBoolean(0);
		} catch (JSONException e) {
			callbackContext.error("JSON Error");
			Log.w(TAG, e.getMessage());
			return;
		}

		if (enable) {
			Settings.System.putInt(cordova.getActivity().getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1);
		} else {
			Settings.System.putInt(cordova.getActivity().getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
		}
		
		returnValue(callbackContext, "autoRotate", enable);
	}

	/**
	 * Get location services' on/off status.
	 * Returns what services are on (network or/and gps).
	 * 
	 * @param callbackContext JavaScript callback context
	 */
	private void getLocationServices(CallbackContext callbackContext) {
		Log.d(TAG, "Execute getLocationService");
		HashMap<String, Boolean> resultMap = new HashMap<String, Boolean>();
		boolean isNetworkEnabled, isGPSEnabled;
		LocationManager lm;
		
		lm = (LocationManager) cordova.getActivity().getSystemService(Context.LOCATION_SERVICE);

		isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		
		resultMap.put("value", isNetworkEnabled||isGPSEnabled);
		resultMap.put("networkValue", isNetworkEnabled);
		resultMap.put("gpsValue", isGPSEnabled);

		returnValue(callbackContext, "locationServices", resultMap);
	}

	/**
	 * Shows the device's location service settings.
	 */
	private void setLocationServices(CallbackContext callbackContext) {
		Log.d(TAG, "Execute setLocationService");

		cordova.getActivity().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	}
	
	//////////////////////////////////////////////
	/////////// Callback response part ///////////
	
	/**
	 * Sent result back to JavaScript callback.
	 * @param callbackContext JavaScript callback context
	 * @param component The component changed or checked 
	 * @param value Value of component
	 */
	private void returnValue(CallbackContext callbackContext, String component, boolean value) {
		JSONObject response = new JSONObject();
		
		Log.d(TAG, component + ": " + value);
		
		try {
			response.put("component", component).put("value", value);
		} catch (JSONException e) {
			callbackContext.error("JSON Error");
			return;
		}

		callbackContext.success(response);
	}
	
	/**
	 * Sent result back to JavaScript callback.
	 * @param callbackContext JavaScript callback context
	 * @param component The component changed or checked 
	 * @param value Value of component
	 */
	private void returnValue(CallbackContext callbackContext, String component, double value) {
		JSONObject response = new JSONObject();
		
		Log.d(TAG, component + ": " + value);
		
		try {
			response.put("component", component).put("value", value);
		} catch (JSONException e) {
			callbackContext.error("JSON Error");
			return;
		}

		callbackContext.success(response);
	}
	
	/**
	 * Sent result back to JavaScript callback.
	 * @param callbackContext JavaScript callback context
	 * @param component The component changed or checked 
	 * @param map Map that will be merged into the response JSONObject
	 */
	private void returnValue(CallbackContext callbackContext, String component, HashMap<String, Boolean> map) {
		JSONObject response = new JSONObject();
		try {
			response.put("component", component);
			for (Iterator<Map.Entry<String, Boolean>> keyValue = map.entrySet().iterator(); keyValue.hasNext();) {
				Map.Entry<String, Boolean> entry = (Map.Entry<String, Boolean>) keyValue.next();
				
				Log.d(TAG, component + ":  " + entry.getKey() + ": " + entry.getValue());
				
				response.put(entry.getKey(), entry.getValue());
			}
		} catch (JSONException e) {
			callbackContext.error("JSON Error");
			return;
		}

		callbackContext.success(response);
	}
}