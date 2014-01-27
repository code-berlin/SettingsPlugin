#SettingsPlugin

This is a cordova plugin that can change device settings from inside the app during runtime.

It currently supports Android devices only.

###Features:
 - Change brightness of the device
 - Turn Bluetooth on/off
 - Turn auto-rotate on/off (allow app rotation or stay in portrait)

###Version

0.1

##Installation

 - Go to your cordova project's root folder
 - Execute ```cordova plugin add https://github.com/code-berlin/SettingsPlugin.git```
 - Start implementing your JavaScript code.

##Usage

From anywhere in your JavaScript code you can call the settings plugin by using the variable ```settingsPlugin```.


###Getter
Every getter function accepts a neccesary success callback function and an optional callback function for cases of failure. Its basic call looks like this:

```settingsPlugin.getComponent(successCb[, errorCb]);```

The success functions get's an argument holding the response of the request. It's a JSON holding at least the components name and it's value.
####Response JSON object:
```JavaScript
{
    component: 'component name',
    value: anyValue
}
```

The error callback holds a String argument with a message on what might have been wrong.

####Example
If you want to get the state of the Bluetooth adapter, just write:
```JavaScript
settingsPlugin.getBluetooth(
    function(response){
        console.log(response.component + ' is ' + (response.value ? 'on' : 'off')); // bluetooth is on/off
    },
    function(error){
        console.error('Something went wrong: ' + error); // Something went wrong: JSON error
    }
);
```

###Setter

Every setter function of the plugin accepts three arguments - the value to be set, an optional success and an optional failure callback function.

```settingsPlugin.setComponent(value[, successCb[, errorCb]]);```

The value can be anything depending an the what you want to set. Bluetooth can be turned on and off using a boolean value (true / false) but the brightness can be set using a Number value between 0.0 and 1.0. Use reference for further information.

The success callback function hold the same rsponse object like the getter function.

```JavaScript
settingsPlugin.setBluetooth(
    true,
    function(response){
        console.log(response.component + ' is ' + (response.value ? 'on' : 'off')); // bluetooth is on/off
    },
    function(error){
        console.error('Something went wrong: ' + error); // Something went wrong: JSON error
    }
);
```

##Reference

###Auto-rotate
Turn auto-rotation of app on and off. With auto-rotate turned off, the app stays in portrait mode.
```JavaScript
getAutoRotate(successCb[, errorCb])
setAutoRotate(value[, successCb[, errorCb]])
```
The setter accepts a ```boolean``` value (```true``` for on).
###Bluetooth
Turn Bluetooth adapter on or off.
```JavaScript
getBluetooth(successCb[, errorCb])
setBluetooth(value[, successCb[, errorCb]])
```
The setter accepts a ```boolean``` value (```true``` for on).
###Brightness
Set the device's screen brightness. 
```JavaScript
getBluetooth(successCb[, errorCb])
setBluetooth(value[, successCb[, errorCb]])
```
The setter accepts a ```Number``` value between 0 and 1. 0.78 would be 78% of the maximum screen brightness.
