var settings = {

	getBluetooth: function(successCallback, errorCallback, param) {
		return cordova.exec(successCallback,
			errorCallback,
			'Settings',
			'getBluetooth',
			params);
	}
	
}
module.exports = settings;



