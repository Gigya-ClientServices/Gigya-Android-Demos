# Gigya-Android-Demos

This is an Android Demo project written in java with Android Studio that provide demos of Gigya's native Android SDK and related functionality. It provides demos of the following:

 * Login (Native Social)
 * Login with RaaS
 * Logout
 * Facebook and Google+ Native Login
 * Making an API Request
 * Session Linking with GSWebBridge
 * Comments Plugin View
 * Publish User Action with Facebook Additional Permission Request
 
## Configuration
**IMPORTANT** Ensure your version of Android Studio and ADB is updated to the latest version. 

1. Extract the the Android sample application in your Documents folder.
2. Open the project folder in Android Studio.
3. Choose to accept and change any folder location warnings displayed.
4. From the Project Browser, find and open the ```./app/res/values/strings.xml``` file.
5. Edit the ```app_id``` in the file to reflect your Facebook App.
6. Edit the ```gigya_api_key``` to reflect your Gigya API Key.
7. Save and close the file.
8. Follow the steps in [Facebooks documentation](https://developers.facebook.com/docs/android/getting-started#release-key-hash) on adding this android app and signing key to your Facebook App.
9. From the Menu, select **Build > Clean Project**. 
10. From the Menu, select **Build > Make Project**. 
