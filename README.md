# Political Preparedness

Political Preparedness is an Android Kotlin application built to supply civic data intended to 
provide educational opportunities to the U.S. electorate using data provided by the Google Civic 
Information API.

## Project Overview

This app demonstrates the following views and techniques:

* [Retrofit](https://square.github.io/retrofit/) to make api calls to an HTTP web service.
* [Moshi](https://github.com/square/moshi) which handles the deserialization of the returned JSON 
  to Kotlin data objects. 
* [Glide](https://bumptech.github.io/glide/) to load and cache images by URL.
* [Room](https://developer.android.com/training/data-storage/room) for local database storage.
  
It leverages the following components from the Jetpack library:

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* [Data Binding](https://developer.android.com/topic/libraries/data-binding/) with binding adapters
* [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) with the 
  SafeArgs plugin for parameter passing between fragments


## Setting up the Repository

To get started with this project, simply pull the repository and import the project into Android
Studio. From there, deploy the project to an emulator or device. 

* NOTE: In order for this project to pull data, you will need to insert your API key into 
  "local.properties" file like so:
  apiKey="YOUR_API_KEY_HERE". 
  
You can generate an API Key from the [Google Developers Console](https://console.developers.google.com/)
  

