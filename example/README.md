#SettingsPlugin Example

This is a Cordova example app of all the features this plugin uses. 

The file ```www/index.html``` shows how the plugin can be used. 

### Run the app
If you run ```cordova_add_run_android.bat``` on Windows or ```cordova_add_run_android.sh``` on Unix-based systems, it will automatically create, build and run the app on a connected device.

Otherwise, you need to do the following steps manually:
- Create a folder name platforms: ```mkdir platforms```
- Add android platform to cordova example project: ```cordova platform add android```
- Run the on an Android device: ```cordova run android```