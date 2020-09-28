# UrbanDictionary
Dictionary app that allows you to lookup words &amp; sort them based on likes & dislikes

### Overview
This application uses Urban-dictionary API for fetching words. It includes one activity & two fragments. App uses retrofit along with kotlin coroutines for fetching data, Hilt for dependency injection, Android architecture components like Room persistence library for database(offline-cache), data-binding for binding UI with data sources, Viewmodel for managing UI in lifecycle conscious way and finally Livedata to ensure UI matches the data-state. Also uses navigation-component for fragment transactions and shared preferences for saving last searched word. 
It follows MVVM architecture.
 
### UrbanDictionaryApplication 
  This class extends the Application class. This class is annotated with @HiltAndroidApp for Hilt to work. This annotation triggers Hilt's code generation which also serves as application-level dependency container.
  
#### UI
- #### MainActivity 
  This activity is host for two fragments named - MainFragment & PreferenceFragment.
- #### Fragments
  MainFragment serves as main screen for this app. It saves the last searched word so when app is reopened it starts with same word. When a word is searched, a method in viewmodel  is triggered to execute it. PreferenceFragment is used to sort the list of words based on likes or dislikes. Navigation component is used for navigation between these two fragments.
  
#### ViewModel
Viewmodel in response to the call from MainFragment, starts a coroutine which makes a call to a requestData() method in repository which can be suspended. Viewmodel also includes a LiveData object to observe result from repository.

#### Repository
This class includes offline cache. It receives a call from viewmodel invoking a suspend method in repository named requestData(). The method then checks for network connection, if exists it will execute the network call to get results . Once results are received, it inserts them into database. Finally, it fetches results from database to provide it to UI. If result from network is null, you can see a toast saying "word not found". If there is no network connection, it will directly make a call to database, searching for the given word. If given word exists, it will return list of words else will show a toast saying "Check your network connection".  

#### Room Database
Used to access data, in case there is no network. 
