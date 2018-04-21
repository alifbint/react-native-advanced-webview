# react-native-advanced-web-view
## Getting started

`$ npm install alifbint/react-native-advanced-web-view --save`

### Mostly automatic installation

`$ react-native link react-native-advanced-web-view`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
    - Add `import com.alifbint.advancedwebview;` to the imports at the top of the file
    - Add `new AdvancedWebviewPackage()` to the list returned by the `getPackages()` method
2. Add Permission on Android `android/app/src/main/AndroidManifest.xml`
    ```
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-feature android:name="android.hardware.location.gps" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.CAMERA"/>
    ```
3. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-advanced-web-view'
  	project(':react-native-advanced-web-view').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-advanced-web-view/android')
  	```
4. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-advanced-web-view')
  	```


## Usage
```javascript
import AdvancedWebView from 'react-native-advanced-web-view';
```
  
### Documentation
more Documentation you can read from [React Native Webview Documentation](https://facebook.github.io/react-native/docs/webview.html)
#### Extra Props :
##### `enabledUploadAndroid`
this props allow WebView to Browse File from local directory for upload to website
| Type                 | Required      |
| -------------------- |:-------------:|
| bool                 | No            |
##### `enabledDownloadAndroid`
this props allow WebView to Download file from Website to local directory
| Type                 | Required      |
| -------------------- |:-------------:|
| bool                 | No            |