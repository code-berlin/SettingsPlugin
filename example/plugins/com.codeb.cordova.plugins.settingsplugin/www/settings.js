var settingsPlugin = {
	/**
	 * Returns Bluetooth status.
	 * @param {Function} successCallback - Function to be called after successful response (args: {component: 'bluetooth', value: [isEnabled]}).
	 * @param {Function} errorCallback - Function being called if something went wrong (args: error).
	 */
	getBluetooth: function(successCallback, errorCallback)
	{
		successCallback = successCallback || null;
		errorCallback = errorCallback || console.error;
		cordova.exec(successCallback, errorCallback, 'SettingsPlugin', 'getBluetooth', []);
	},
	/**
	 * Turn Bluetooth on/off.
	 * @param {Boolean} on - True for Bluetooth turned on.
	 * @param {Function} successCallback - Function to be called after successful response (args: {component: 'bluetooth', value: [isEnabled]}).
	 * @param {Function} errorCallback - Function being called if something went wrong (args: error).
	 */
	setBluetooth: function(on, successCallback, errorCallback)
	{
		successCallback = successCallback || null;
		errorCallback = errorCallback || console.error;
		cordova.exec(successCallback, errorCallback, 'SettingsPlugin', 'setBluetooth', [on]);
	},
	/**
	 * Returns the brightness value.
	 * @param {Function} successCallback - Function to be called after successful response (args: {component: 'brightness', value: [brightnessValue]}).
	 * @param {Function} errorCallback - Function being called if something went wrong (args: error).
	 */
	getBrightness: function(successCallback, errorCallback)
	{
		successCallback = successCallback || null;
		errorCallback = errorCallback || console.error;
		cordova.exec(successCallback, errorCallback, 'SettingsPlugin', 'getBrightness', []);
	},
	/**
	 * Set the screen brightness.
	 * @param {Number} value - Value between 0.0 and 1.0 (1.0 = 100%)
	 * @param {Function} successCallback - Function to be called after successful response (args: {component: 'brightness', value: [brightnessValue]}).
	 * @param {Function} errorCallback - Function being called if something went wrong (args: error).
	 */
	setBrightness: function(value, successCallback, errorCallback)
	{
		successCallback = successCallback || null;
		errorCallback = errorCallback || console.error;
		cordova.exec(successCallback, errorCallback, 'SettingsPlugin', 'setBrightness', [value]);
	},
	/**
	 * Returns the value of rotation direction.
	 * @param {Function} successCallback - Function to be called after successful response (args: {component: 'autoRotate', value: [isEnabled]}).
	 * @param {Function} errorCallback - Function being called if something went wrong (args: error).
	 */
	getAutoRotate: function(successCallback, errorCallback)
	{
		successCallback = successCallback || null;
		errorCallback = errorCallback || console.error;
		cordova.exec(successCallback, errorCallback, 'SettingsPlugin', 'getAutoRotate', []);
	},
	/**
	 * Turn on/off auto-rotation.
	 * Turning auto-rotate off will show app in portrait mode only.
	 * @param {Boolean} on - True for auto-rotation turned on.
	 * @param {Function} successCallback - Function to be called after successful response (args: {component: 'autoRotate', value: [isEnabled]}).
	 * @param {Function} errorCallback - Function being called if something went wrong (args: error).
	 */
	setAutoRotate: function(on, successCallback, errorCallback)
	{
		successCallback = successCallback || null;
		errorCallback = errorCallback || console.error;
		cordova.exec(successCallback, errorCallback, 'SettingsPlugin', 'setAutoRotate', [on]);
	},
	/**
	 * Returns the state (on/off) of the network location service and GPS location service.
	 * @param {Function} successCallback - Function to be called after successful response (args: {component: 'locationServices', value: [isNetworkEnabled], networkValue: [isNetworkEnabled], gpsValue: [isGPSEnabled]}).
	 * @param {Function} errorCallback - Function being called if something went wrong (args: error).
	 */
	getLocationServices: function(successCallback, errorCallback)
	{
		successCallback = successCallback || null;
		errorCallback = errorCallback || console.error;
		cordova.exec(successCallback, errorCallback, 'SettingsPlugin', 'getLocationServices', []);
	},
	/**
	 * Will send the user to the phone's location services settings.
	 */
	setLocationServices: function()
	{
		cordova.exec(null, null, 'SettingsPlugin', 'setLocationServices', []);
	},
	/**
	 * Helper function to check if app is in background on older Android versions.
	 * @param {Function} successCallback - Function to be called after successful response (args: {component: 'appPaused', value: [isAppPaused]}).
	 * @param {Function} errorCallback - Function being called if something went wrong (args: error).
	 */
	isAppPaused: function(successCallback, errorCallback)
	{
		successCallback = successCallback || null;
		errorCallback = errorCallback || console.error;
		cordova.exec(successCallback, errorCallback, 'SettingsPlugin', 'isAppPaused', []);
	}

};
module.exports = settingsPlugin;