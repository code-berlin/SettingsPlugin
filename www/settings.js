var settings = {

	getBluetooth: function(successCallback, errorCallback, params) {
		return cordova.exec(successCallback,
			errorCallback,
			'Settings',
			'getBluetooth',
			params);
	},
	setBluetooth: function(successCallback, errorCallback, params) {
		return cordova.exec(successCallback,
			errorCallback,
			'Settings',
			'setBluetooth',
			params);
	},
	getVolume: function(successCallback, errorCallback, params) {
		return cordova.exec(successCallback,
			errorCallback,
			'Settings',
			'getVolume',
			params);
	}
	
}
module.exports = settings;



