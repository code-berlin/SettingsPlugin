var settings = {

	getBluetooth: function(successCallback, errorCallback, params) {
		return cordova.exec(successCallback,
			errorCallback,
			'Settings',
			'getBluetooth',
			[params]);
	}
	
}
module.exports = settings;



