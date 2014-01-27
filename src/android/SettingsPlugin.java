package com.codeb.cordova.plugins.settingsplugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.util.Log;
import android.view.WindowManager;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

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
		boolean isEnabled = BluetoothAdapter.getDefaultAdapter().isEnabled();

		Log.d(TAG, "Bluetooth enabled: " + isEnabled);

		JSONObject response = new JSONObject();
		try {
			response.put("component", "bluetooth").put("value", isEnabled);
		} catch (JSONException e) {
			callbackContext.error("JSON Error");
			return;
		}

		callbackContext.success(response);
	}

	/**
	 * Turn on/off Bluetooth.
	 * 
	 * @param callbackContext JavaScript callback context
	 * @param args Array should consist of one boolean value 
	 */
	private void setBluetooth(CallbackContext callbackContext, JSONArray args) {
		Log.d(TAG, "Execute setBluetooth");
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		JSONObject response = new JSONObject();
		boolean turnOn;

		try {
			turnOn = args.getBoolean(0);

			if (turnOn) {
				mBluetoothAdapter.enable();
			} else {
				mBluetoothAdapter.disable();
			}

			response.put("component", "bluetooth").put("value", turnOn);
			
		} catch (JSONException e) {
			callbackContext.error("JSON Error");
			Log.w(TAG, e.getMessage());
			return;
		}

		callbackContext.success(response);
	}

	/**
	 * Get brightness status.
	 * Returns a double value to JavaScript.
	 * 
	 * @param callbackContext JavaScript callback context
	 */
	private void getBrightness(CallbackContext callbackContext) {
		float brightness;
		JSONObject response = new JSONObject();

		Log.d(TAG, "Execute getBrightness");

		try {
			brightness = ((float) Settings.System.getInt(this.cordova.getActivity().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS)) / 255;
		} catch (SettingNotFoundException e1) {
			callbackContext.error("Setting not found on this device");
			return;
		}

		try {
			response.put("component", "brightness").put("value", brightness);
		} catch (JSONException e) {
			callbackContext.error("JSON Error");
			return;
		}

		Log.d(TAG, "Brightness: " + brightness * 100 + "%");
		callbackContext.success(response);
	}

	/**
	 * Turn display brightness up/down using a double value.
	 * 
	 * @param callbackContext JavaScript callback context
	 * @param args Array should consist of one double value between
	 */
	private void setBrightness(CallbackContext callbackContext, JSONArray args) {
		Log.d(TAG, "Execute setBrightness");
		JSONObject response = new JSONObject();

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

			Log.d(TAG, "Brightness: " + brightness * 100 + "%");

			response.put("component", "brightness").put("value", brightness);

		} catch (JSONException e) {
			callbackContext.error("JSON Error");
			Log.w(TAG, e.getMessage());
			return;
		}
		callbackContext.success(response);
	}

	/**
	 * Get auto-rotate on/off status.
	 * 
	 * @param callbackContext JavaScript callback context
	 */
	private void getAutoRotate(CallbackContext callbackContext) {
		Log.d(TAG, "Execute getAutoRotate");

		boolean isEnabled;
		try {
			isEnabled = Settings.System.getInt(this.cordova.getActivity().getContentResolver(), Settings.System.ACCELEROMETER_ROTATION) == 1;
		} catch (SettingNotFoundException e1) {
			callbackContext.error("Setting not found on this device");
			return;
		}
		Log.d(TAG, "Auto Rotate enabled: " + isEnabled);

		JSONObject response = new JSONObject();
		try {
			response.put("component", "autoRotate").put("value", isEnabled);
		} catch (JSONException e) {
			callbackContext.error("JSON Error");
			return;
		}

		callbackContext.success(response);
	}

	/**
	 * Turn on/off auto-rotate.
	 * 
	 * @param callbackContext JavaScript callback context
	 * @param args Array should consist of one boolean value 
	 */
	private void setAutoRotate(CallbackContext callbackContext, JSONArray args) {
		Log.d(TAG, "Execute setAutoRotate");
		JSONObject response = new JSONObject();
		boolean turnOn;

		try {
			turnOn = args.getBoolean(0);

			if (turnOn) {
				Settings.System.putInt(this.cordova.getActivity().getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1);
			} else {
				Settings.System.putInt(this.cordova.getActivity().getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
			}

			response.put("component", "autoRotate").put("value", turnOn);

		} catch (JSONException e) {
			callbackContext.error("JSON Error");
			Log.w(TAG, e.getMessage());
			return;
		}
		
		callbackContext.success(response);
	}

}
