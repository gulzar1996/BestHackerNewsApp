# Offline Hacker News App


  * It has been written **100% in Kotlin**. ❤️
* Patterns and frameworks
	* MVP
* Dependency Injection
  * [Dagger Android 2.11](https://github.com/google/dagger/releases/tag/dagger-2.11) to manage App and Activity-scoped dependencies.
* Database
	* Realm is used to persist data and acts as Single Source of truth for all data related queries.
* Remote Call APIs
	* [Retrofit 2](http://square.github.io/retrofit/) to perform HTTP requests.
* Communication between app layers
    * [RxJava2](https://github.com/ReactiveX/RxJava) and [RxAndroid](https://github.com/ReactiveX/RxAndroid) for interacting between view layers, pagination and local database. 
