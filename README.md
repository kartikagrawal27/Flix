# FLIX

## About

Love movies?! Look no further! Lookup your favorite movies using *Flix* and favorite them to easily access them later. 

## Build Instructions

### From Command Line

1. First clone this git repo
2. To build a debug APK, open a command line and navigate to the root of your project directory. To initiate a debug build, invoke the assembleDebug task:

  `gradlew assembleDebug`

3. This creates an APK named module_name-debug.apk in project_name/module_name/build/outputs/apk/. The file is already signed with the debug key and aligned with *zipalign*, so you can immediately install it on a device.

4. For deploying to Virtual or physical device please refer to instructions [here](https://developer.android.com/studio/build/building-cmdline)

### From Android Studio

Simple clone this git repo and open the project in Android Studio. Click on *Build* to simply build the app. 

If you want to run the app, click on *Run* and select the desired device, virtual or physical you would like to run the app on.

## Features Implemented

* Authentication
* Session management
* Search favorite movies
* Ability to favorite movies
* Detailed movie info
* User specific favorites
* Retention of favorites across sessions

## Technical aspects implemented
* Firebase Auth
* Bottom Navigation View
* Multiple fragments
* RecycleView Adapter
* Async Rest API calls
* Error handling for no results
* Error handing for no favorites
* Error handing for incorrect Poster URL
* Callback Interfaces for API requests

## References

* [Glide](https://github.com/bumptech/glide)
* [Volley](https://github.com/google/volley)
* [Recycler View](https://developer.android.com/guide/topics/ui/layout/recyclerview)
* [Firebase](https://firebase.google.com/)
* [Jackson](https://github.com/FasterXML/jackson-core)
* [Grid Decorator](https://stackoverflow.com/a/30701422)

# TODO

- [x] Add firebase auth
- [x] Define bottomNavigationView
- [x] Session-management
- [x] Fragments
- [x] RecyclerView Adapter
- [x] Define API handler
- [x] Add multithreading for API calls
- [x] Test the API handler
- [x] Add favorite functionality
- [x] Handle pagination for more search results
- [x] Create interface server callback
- [x] Implement detail movie activity
- [x] Test for Fragmentation
- [ ] Customize the login screen
- [x] Implement necessary activity lifecycle methods
- [ ] Add Unit, UI, integration tests
- [x] App icon
- [x] Update ReadMe with build instruction
