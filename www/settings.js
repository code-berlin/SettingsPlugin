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
	}
	
}
module.exports = settings;



