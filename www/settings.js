var settingsPlugin = {

	getBluetooth: function(successCallback, errorCallback, params) {
		return cordova.exec(successCallback,
			errorCallback,
			'SettingsPlugin',
			'getBluetooth',
			params);
	},
	setBluetooth: function(successCallback, errorCallback, params) {
		return cordova.exec(successCallback,
			errorCallback,
			'SettingsPlugin',
			'setBluetooth',
			params);
	},
	getAutoRotate: function(successCallback, errorCallback, params) {
		return cordova.exec(successCallback,
			errorCallback,
			'SettingsPlugin',
			'getAutoRotate',
			params);
	},
	setAutoRotate: function(successCallback, errorCallback, params) {
		return cordova.exec(successCallback,
			errorCallback,
			'SettingsPlugin',
			'setAutoRotate',
			params);
	},
	setBrightness: function(successCallback, errorCallback, params) {
		return cordova.exec(successCallback,
			errorCallback,
			'SettingsPlugin',
			'setBrightness',
			params);
	},
	getBrightness: function(successCallback, errorCallback, params) {
		return cordova.exec(successCallback,
			errorCallback,
			'SettingsPlugin',
			'getBrightness',
			params);
	}
	
}
module.exports = settingsPlugin;



