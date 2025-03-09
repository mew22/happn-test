# "HappnTest" Android App

This application has been developed as part of a technical test for a job opportunity at Happn.

Here is the skill test statement:

Project Overview
You are tasked with developing an Android application that displays a list of items and
allows users to access details for a selected item. The application must be written in
Kotlin, hosted privately on GitHub, utilize Coroutines for API calls, and employ Jetpack
Compose for the user interface.

Requirements
1. Git:
   ○ Create a Git for your project
   ○ Properly divide your work into appropriate commits
2. API:
   ● You will use the API from the Rijksmuseum located in Amsterdam, Netherlands.
   This API provide all items from the permanent collection of the museum as well
   as the detail for each of them.
   ○ The API documentation is available here: API
   ○ You will need an API key for this project, use this one: rIl6yb6x
3. Kotlin:
   ○ Develop the entire application using Kotlin, adhering to best practices and
   coding standards.
4. User Interface (UI):
   ○ Implement the user interface using Jetpack Compose.
   ○ Create two main screens:
   ■ Screen 1: List
   ● Display a list of items from the french collection of the
   Rijksmuseum. You don’t have to care of paging if you don’t
   want to. You can just fetch the first pages.
   ○ Endpoint: GET /api/[culture]/collection

■ Screen 2: Item Details
● When a user clicks on an item in the list, display detailed
information about the selected item.
○ Endpoint: GET /api/[culture]/collection/[object-number]

5. API Calls:
   ○ Use Coroutines to make asynchronous API calls to retrieve data for the
   filtered list and item details.
   ○ Ensure proper error handling and data parsing.

6. Offline Mode (Bonus):
   ○ Implement an "Offline Mode" feature that allows users to access
   previously fetched data when the device is offline.

7. Code Quality:
   ○ Maintain clean and well-documented code.
   ○ Follow the principles of SOLID, and ensure your code is maintainable and
   testable.

8. Additional Considerations:
   ○ Implement navigation between the filtered list and item details screens.
   ○ Handle configuration changes gracefully.
   ○ Handle edge cases and errors effectively.
   ○ Use of third party libraries must be justified.

### State of the application

First of all, I would like to point out that the application is incomplete in its current state. 
I did not manage to write all the tests I wanted (missing UI and integration tests) nor to implement paging data from the api. 
Enough of what is missing, let's talk about what has been done so far.

The application in its current state can display the list of art object fetched from the Rijksmuseum API. 
In addition to the list feature, it is possible to click on an item and fetch its details through another endpoint.

Some of the API calls to detail endpoint will fail at parsing on "principalMakers[i].nationality" as it is described in the [documentation](https://data.rijksmuseum.nl/docs/api/details) as
required but in practice it is optional.
While it would be easy to fix, it is actually a good opportunity to show the error screen.

Unit tests on the most important classes has been written.

An offline version has been implemented by caching the data in a local database.

### Setup
Insert API key into local.properties file as following
```
API_KEY="{key}"
```
Pick a build variant as described in Environment section, build and launch the app.


### Environment

To allow more flexibility over testing and development, I added 2 build variants mock and prod:
- "mock" variant will only use mock predefined data for network
- "prod" variant will use regular networking and caching library

### Quality measurements

In order to implement quality standards, I added to the project some Gradle task for:
- Linter and code smell checker, using KtLint and Detekt (./gradlew check and ./gradlew detekt)

### Technical choices

I choose to use Gradle KTS with build convention structure to manage the project configuration, while it is a powerful tool that allow autocompletion, it also provide all the features of Kotlin language.

I used Koin to manage the dependency injection. This library has its pros (simplicity, reduce verbosity, multiplatform friendly for future plans) and of course its cons (error prone because of runtime resolution).

I used Kotlin Flow to communicate the data between the different layers because it is a native Kotlin API and offers better integration with "reactive" operators.
It also avoid to have android objects like LiveData in the domain and make testing easier.

I used Jetpack Compose declarative UI framework coupled with the navigation library to navigate inside features screen.

An offline version has been implemented by caching the data with Room database. 
Here I choose to implement only one instance of the database as it make more sense for this project, but it would be fairly possible to use one instance by feature if necessary.

Retrofit and OkHttp make it really easy and scalable to interact with REST APIs.

I chose Coil to show the internet images as it is reliable and reduces the complexity of such operations.

Finally, I like using JUnit5 and Mockk as testing libraries. JUnit5 offers a lot of functions (@Nested test classes, customizable @ParameterizedTest...) and Mockk gracefully replaces Mockito for Kotlin projects.

### Architectural decisions

For the presentation part, I like to implement MVI pattern, that explicitly define Event and State of the UI with a viewModel to coordinate them.
Then I tried to implement the Clean Architecture following the SOLID principles.
It makes the applications scalable in different way. For instance, there is no business logic within the presentation layer and the code is, as much as possible, decoupled from the Android framework 
like all :data and :domain modules remain plain Kotlin modules (which makes it more easily testable).

Here I choose to split my module structure by feature, and then by layer (domain, data, ui) in order to be more explicit about the separation of concerns.
There are also following reasons that convince me to drive my architecture by feature: 

- Higher Modularity
  Package-by-feature has packages with high cohesion, high modularity, and low coupling between packages.

- Easier Code Navigation
  Maintenance programmers need to do a lot less searching for items, since all items needed for a given task are usually in the same directory. Some tools that encourage package-by-layer use package naming conventions to ease the problem of tedious code navigation. However, package-by-feature transcends the need for such conventions in the first place, by greatly reducing the need to navigate between directories.

- Higher Level of Abstraction
  Staying at a high level of abstraction is one of programming's guiding principles of lasting value. It makes it easier to think about a problem, and emphasizes fundamental services over implementation details. As a direct benefit of being at a high level of abstraction, the application becomes more self-documenting: the overall size of the application is communicated by the number of packages, and the basic features are communicated by the package names. The fundamental flaw with package-by-layer style, on the other hand, is that it puts implementation details ahead of high level abstractions - which is backwards.
  
- Separates Both Features and Layers
  The package-by-feature style still honors the idea of separating layers, but that separation is implemented using separate classes. The package-by-layer style, on the other hand, implements that separation using both separate classes and separate packages, which doesn't seem necessary or desirable.

- Minimizes Scope
  Minimizing scope is another guiding principle of lasting value. Here, package-by-feature allows some classes to decrease their scope from public to package-private. This is a significant change, and will help to minimize ripple effects. The package-by-layer style, on the other hand, effectively abandons package-private scope, and forces you to implement nearly all items as public. This is a fundamental flaw, since it doesn't allow you to minimize ripple effects by keeping secrets.

- Better Growth Style
  In the package-by-feature style, the number of classes within each package remains limited to the items related to a specific feature. If a package becomes too large, it may be refactored in a natural way into two or more packages. The package-by-layer style, on the other hand, is monolithic. As an application grows in size, the number of packages remains roughly the same, while the number of classes in each package will increase without bound.


### Real work environment and improvements reflexion

I tried to split my commits so they are reviewable but it is possible to split in even smaller chunks of code.
In a real work environment, we probably would have groomed the work to split it into small subtasks with some acceptance criteria for each.

There are some improvements that I would like to add later: 
- The application could have a paging strategy for the list feature using Pager3 to enforce a lower data consumption.
- Replace Koin with compile time dependency injection like Hilt or Dagger for safety

### Source of inspiration

- Multiple way of defining Clean Architecture Layers: https://proandroiddev.com/multiple-ways-of-defining-clean-architecture-layers-bbb70afa5d4a
- Dynamic feature delivery: https://developer.android.com/guide/playcore/feature-delivery
- Uncle bob screaming architecture: https://levelup.gitconnected.com/what-is-screaming-architecture-f7c327af9bb2
- Modular patterns: https://hackernoon.com/applying-clean-architecture-on-web-application-with-modular-pattern-7b11f1b89011
- Package by features with bounded contexts: https://reflectoring.io/java-components-clean-boundaries/ 
- 5 most populars package structures: https://www.techyourchance.com/popular-package-structures/ 

### Source code description

```yaml
- /app
    # Main activity
    - MainActivity.kt
    # Application class
    - App.kt
    # Inter-Feature navigation
    - AppNavHost.kt
    # Injection configuration
    - Injection.kt
    # Database definition
    - Database.kt
- /build-logic
    # Configuration for Application module
    - AppPlugin
    # Configuration for Application module with compose
    - ComposeAppPlugin
    # Configuration for Android Library module
    - AndroidLibPlugin
    # Configuration for Android Library module with compose
    - ComposeLibPlugin
    # Configuration for Koltin Library module
    - KotlinLibPlugin
    # Configuration for Detekt
    - DetektPlugin
# All the common, global and shared material and configuration
- /core
    # Shared UI component and design system
    - /ui
    # Common Kotlin tools like Flow, coroutine or Result extension
    - /common
    # Shared environment tool as Debug mode, Flavor mode
    - /environment
        # Abstraction interface (should be a Kotlin only module)
        - /gateway
        # Implementation (should be a Android library module)
        - /implementation
    # Shared monitoring tool for crashlytics, logger etc
    - /monitoring
        # Abstraction interface (should be a Kotlin only module)
        - /gateway
        # Implementation (should be a Android library module)
        - /implementation
    # Common db helper to create database
    - /db
        # Abstraction interface (should be a Kotlin only module)
        - /gateway
        # Implementation (should be a Android library module)
        - /implementation
    # Shared network configuration for Retrofit and Moshi
    - /network
# Features
- /feature
    # Feature module corresponding to Art List feature (display list of art object)
    - /artlist
        # Plain Kotlin module responsible for containing everything related to external services like databases, remote services, device apis, data providers
        - /data
        # Plain Kotlin module that hold business rules, entities, failures, value objects and repositories abstractions
        - /domain
        # Android module that hold compose views, fragments, views, viewModels
        - /ui
        # Android module that act as an umbrella module for all data, domain and ui of the feature. Hold dependency injection and routes.
        - /lib
    # Feature module corresponding to Art Detail feature (display detail of an art object)
    - /artdetail
        # Plain Kotlin module responsible for containing everything related to external services like databases, remote services, device apis, data providers
        - /data
        # Plain Kotlin module that hold business rules, entities, failures, value objects and repositories abstractions
        - /domain
        # Android module that hold compose views, fragments, views, viewModels
        - /ui
        # Android module that act as an umbrella module for all data, domain and ui of the feature. Hold dependency injection and routes.
        - /lib
```

### Screenshots ###

<img src="https://github.com/mew22/happn-test/blob/main/screenshot/screenshot_1.png"/>
<img src="https://github.com/mew22/happn-test/blob/main/screenshot/screenshot_2.png"/>
<img src="https://github.com/mew22/happn-test/blob/main/screenshot/screenshot_3.png"/>
<img src="https://github.com/mew22/happn-test/blob/main/screenshot/recording.gif"/>