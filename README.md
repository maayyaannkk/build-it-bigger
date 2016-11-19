# build-it-bigger
Joke telling app using GCE backend and gradle

In this project, I have used Gradle to build a joke-telling app, factoring functionality into libraries and flavors to keep the build simple.
I have also configured a Google Cloud Endpoints development server to supply the jokes.

<b>Other features: </b>
<ul>
  <li>Project contains a Java library for supplying jokes</li>
  <li>Project contains an Android library with an activity that displays jokes passed to it as intent extras.</li>
  <li>Project contains a Google Cloud Endpoints module that supplies jokes from the Java library. Project loads jokes from GCE module via an async task.</li>
  <li>Project contains connected tests to verify that the async task is indeed loading jokes.</li>
  <li>Project contains paid/free flavors. The paid flavor has no ads, and no unnecessary dependencies.</li>
  <li>App retrieves jokes from Google Cloud Endpoints module and displays them via an Activity from the Android Library.</li>
  <li>The free app variant displays interstitial ads between the main activity and the joke-displaying activity.</li>
  <li>The app displays a loading indicator while the joke is being fetched from the server.</li>
  <li>A Gradle task that starts the GCE dev server, runs all the Android tests, and shuts down the dev server.</li>
</ul>
