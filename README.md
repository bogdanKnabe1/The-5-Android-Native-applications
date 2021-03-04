
  ![ttystudio GIF](https://media.giphy.com/media/llarwdtFqG63IlqUR1/giphy.gif)

  # 4-Android-Native-Applications
  > The 5 Android Native applications with out any external libraries. Using vanilla java/kotlin and android SDK.

  |  #  |            Project             | STATUS |
  | :-: | :----------------------------: | :-------: |
  | 01  | [FlexPictures](https://github.com/NinpoU-u/The-4-Android-Native-applications/tree/master/FlexPictures/app) | X |
  | 02  | [Music app](https://github.com/NinpoU-u/The-6-Android-Native-applications/tree/master/Musio) | alpha |
  | 03  | [News app](https://github.com/NinpoU-u/The-6-Android-Native-applications/tree/master/Newarc) | X |
  | 04  | [Taxi helper](https://github.com/NinpoU-u/The-6-Android-Native-applications/tree/master/Pureable/app) | X |
  | 05  | [Reminder](https://github.com/NinpoU-u/The-5-Android-Native-applications/tree/master/Android1) | DONE |
  

  ## Music app

  Musio is a simple music streaming service that implements many basic concepts for building an android application. Here are used:
  - RecyclerView
  - Picasso
  - Retrofit(now Volley)
  - BottomNavigationView
  - CircleImageView
  - Single activity pattern
  - REST api
  - and many other and many other small features like blurred background custom toolbar etc.
  
  Now we have:
  - Implemented Search sond fragment and Music player fragment
  - We can find any song and listn it
  - We have simple music player and we can extend it with more fetures in future
  - We have simple UI with more flexibility.
  - A very clear UI construction scheme based on our architecture. We have one activity which contains a container in which the necessary fragments (screens) are replaced
  - Implemented fragment communication with out ViewModel library.
  - And many other small things can be finded in this project.
  
  Would be implement in future:
  - Network with Retrofit
  - Naviation library from jetpack for more cleaner code related to fragments and user experience
  - Implement Home screen and Albums screen
  - User profile and registration
  
  Some pictures of Musio
  
  ![MusioMusicPlayer](https://user-images.githubusercontent.com/47458290/87451151-26ba4100-c608-11ea-9676-4d1626a463f6.png)
  
  ![MusioSearch](https://user-images.githubusercontent.com/47458290/87451158-291c9b00-c608-11ea-9258-583d2ada1cae.png)
  
  ![musioTrackList](https://user-images.githubusercontent.com/47458290/87451164-2ae65e80-c608-11ea-8d35-358a61f7d5ff.png)

  ## FlexPicture app
  
  FlexPicture - this is a very simple application that essentially has only one screen, the whole point in the algorithm for selecting images. 
  The application provides a simple interface where the user is shown pictures on topics that are of interest to him and depending on the choice, the application itself decides   which picture to show next. Factors can be many things from the real world, for example: politics, culture, pop culture, music, etc.
  
  Used:
  - ViewModel Jetpack library
  - Navigation 
  - CardStackView
  - BottomNavigationView

  Now we have:
  - Implemented Card stack view(which it is possible to flip in different directions of the card)

  ## Purable app (Named TaxiX)
  
  TaxiX - it’s just a taxi client that allows you to work with such important components as cards, authentication, saving states, transferring and saving information.
  The application essentially composes the main screen on which the map is displayed, the driver who is registered in the system. 
  The map shows the places with the greatest demand for trips, they are updated in real time and allow the driver to receive the greatest benefit. 

  Used:
  - Firebase authentication
  - Firebase realtime database
  - RxJava for requests and some background work
  - Jetpack: Navigation, NavigationUi, lifecycle-extensions
  - Google map
  
  Now we have:
  - Simple authentication with phone and gmail
  - Working map
  - Navigation drawer
  - Splash screen
  
  ## Android1 (Named Fitt)
  
  Fitt - simple fitness calendar this is just an application written in Kotlin as a training project. 
  In this project, i tried to figure out how to properly use the architectural components of the android design pattern (MVVM). Create some convenient sample for future applications with these new technologies.
  
  Here are used:
  - ViewModel
  - LiveData
  - Room
  - RecyclerView
  - Lifecycle components
  - Navigation
  - Kotlin
  - Kotlin coroutines
 
 And i was prefer to use android sdk solution such as the:
  - BroadcastReceiver
  - AlarmManager
  - NotificationManager
  - Fragments
  
  The application will be further developed and will receive a number of updates related to performance improvements, animations and more functionality.
  
  ## Connection

  Reach out to me at one of the following places!

  - Telegram at <a href="https://t.me/NinpoU_u" target="_blank">`My telegramm`</a>
  - Twitter at <a href="https://twitter.com/Bogdan21724971" target="_blank">`@NinpoU_u`</a>
  - Gmail at <a>`bogdan.knabe1@gmail.com`</a>
  ---

  ## License

  [![License](http://img.shields.io/:license-mit-blue.svg?style=flat-square)](http://badges.mit-license.org)
  ---
