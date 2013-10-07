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
	getAutoRotate: function(successCallback, errorCallback, params) {
		return cordova.exec(successCallback,
			errorCallback,
			'Settings',
			'getAutoRotate',
			params);
	},
	setAutoRotate: function(successCallback, errorCallback, params) {
		return cordova.exec(successCallback,
			errorCallback,
			'Settings',
			'setAutoRotate',
			params);
	}
	
}
module.exports = settings;



