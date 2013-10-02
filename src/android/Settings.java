package com.codeb.cordova.plugins.settings;
 
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * This class echoes a string called from JavaScript.
 */
public class Settings extends CordovaPlugin {

	private static final String LOG_TAG = "Settings Plugin";
	
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		try {
			if (action.equals("getBluetooth")) {
				String message = args.getString(0);
				this.getBluetooth(message, callbackContext);
				return true;
			}
			return false;
		} catch(Exception e) {
            callbackContext.error(e.getMessage());
            return false;
		}
    }

    private void getBluetooth(String message, CallbackContext callbackContext) {
        Log.d(LOG_TAG, "Exicute getBluetooth");
		if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}

