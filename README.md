# KotlinRedux

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Build Status](https://travis-ci.org/protoman92/KotlinRedux.svg?branch=master)](https://travis-ci.org/protoman92/KotlinRedux)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

Reactive Redux implementation for Kotlin. From the [original JavaScript implementation](https://github.com/reduxjs/redux):

> Redux is a predictable state container for JavaScript apps. [...]It helps you write applications that behave consistently, run in different environments (client, server, and native), and are easy to test.

In a Redux architecture, the entire app can be described as a function of some global state object. For a search app of some nature, a simplified state may look like:

```java
data class GlobalState(
  val query: String? = null,
  val result: SearchResult? = null
)
```

The global state's query will be reflected in a search box, while search results will be displayed by a list view.

## Motivation

Android app development is hugely complicated, and in my opinion, unnecessarily so. While I was excited over the release of architecture components, I feel that they:

- Rely too much on code generation;
- Are not more testable than traditional approaches;
- Are scattered all over the place;

Having worked on React for a while now, I have come to appreciate the beauty of **Redux**, in that it is a simple observer pattern that works well across platform lines (as long as implementations are available). What this library can replace:

- MV+ architectures/Architecture components - via decoupled handling of side effects;
- Dagger - via central dependency injection;
- RxJava - via [Redux Saga](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common);

## Principles

Redux follows 3 principles:

### Single source of truth

> The state of your whole application is stored in an object tree within a single store. This makes it easy to create universal apps, as the state from your server can be serialized and hydrated into the client with no extra coding effort. A single state tree also makes it easier to debug or inspect an application; it also enables you to persist your app's state in development, for a faster development cycle. Some functionality which has been traditionally difficult to implement - Undo/Redo, for example - can suddenly become trivial to implement, if all of your state is stored in a single tree.

### State is read-only

> The only way to change the state is to emit an action, an object describing what happened. This ensures that neither the views nor the network callbacks will ever write directly to the state. Instead, they express an intent to transform the state. Because all changes are centralized and happen one by one in a strict order, there are no subtle race conditions to watch out for. As actions are just plain objects, they can be logged, serialized, stored, and later replayed for debugging or testing purposes.

Generally, we would use data classes for their **copy** methods to ensure immutability.

### Changes are made with pure functions

> To specify how the state tree is transformed by actions, you write pure reducers. Reducers are just pure functions that take the previous state and an action, and return the next state. Remember to return new state objects, instead of mutating the previous state. You can start with a single reducer, and as your app grows, split it off into smaller reducers that manage specific parts of the state tree. Because reducers are just functions, you can control the order in which they are called, pass additional data, or even make reusable reducers for common tasks such as pagination.

For actions, we use sealed classes to encapsulate custom values:

```java
typealias IReducer<GlobalState> = (GlobalState, IReduxAction) -> GlobalState

sealed class Action : IReduxAction {
  data class SetQuery(val query: String?) : Action()
  data class SetResult(val result: SearchResult) : Action()
}

object Reducer : IReducer<GlobalState> {
  override fun invoke(p1: GlobalState, p2: IReduxAction) -> GlobalState {
    when (p2) {
      is Action -> when (p2) {
        is Action.SetQuery -> p1.copy(query = p2.query)
        is Action.SetResult -> p1.copy(result = p2.result)
      }

      else -> p1
    }
  }
}
```

## Main features

This library provides:

- A simple, thread-safe [Redux store](https://github.com/protoman92/KotlinRedux/blob/master/common/common-core/src/main/kotlin/org/swiften/redux/core/ThreadSafeStore.kt);
- [Middleware support](https://github.com/protoman92/KotlinRedux/blob/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Middleware.kt);
- [Prop injection](https://github.com/protoman92/KotlinRedux/blob/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt) for a Redux-compatible **LifecycleOwner**/**RecyclerView.Adapter** etc;
- A [Router middleware](https://github.com/protoman92/KotlinRedux/blob/master/common/common-core/src/main/kotlin/org/swiften/redux/core/RouterMiddleware.kt) implementation;
- An imperative mechanism for asynchronous work ([Redux-Thunk](https://github.com/protoman92/KotlinRedux/blob/master/common/common-thunk/src/main/kotlin/org/swiften/redux/thunk/ThunkMiddleware.kt));
- A complete side effect model ([Redux-Saga](https://github.com/protoman92/KotlinRedux/tree/master/common/common-saga/src/main/kotlin/org/swiften/redux/saga/common), inspired by [redux-saga](https://github.com/redux-saga/redux-saga)) to handle asynchronous work reactively;
- [Android-specific support](https://github.com/protoman92/KotlinRedux/tree/master/android);

## How it works

For the purpose of this short tutorial, I will refer to **Activity**, **Fragment** and **View** all as views.

React.js enforces a component tree that supports the passing of properties from parents to children, and children are supposed to render themselves based on these properties:

```javascript
render() {
  const { userID, userName } = this.props;

  return (
    <Container>
      <IDField userID={userID}/>
      <NameField userName={userName}/>
    </Container>
  );
}
```

There's no reason why we can't do that in Android. Let's define these parent-to-child properties as [OutProp](https://github.com/protoman92/KotlinRedux/blob/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L24). For example, the **OutProp** of a **RecyclerView.ViewHolder** should be its layout position, because said position is provided by its parent (the **RecyclerView.Adapter** instance). For simple **Fragments**, the **OutProp** may be **Unit**, because they don't have anything they are interested in from their immediate parents.

Aside from **OutProp** (which is immutable from the children's perspective), views can have internal **State** - mutable properties owned by said views. For example, for a **Fragment** that keeps track of a count, that count is the fragment's internal state. The UI should react to both changes in **OutProp** and **State** - the only difference is access to mutation.

### Your first Redux component

To start the Redux journey, make a **Fragment** (or anything that implements **LifecycleOwner**) implement [IPropContainer](https://github.com/protoman92/KotlinRedux/blob/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L48):

```java
data class State(val query: String?)
class Action(val updateQuery: (String?) -> Unit)

class Fragment1 : Fragment(), IPropContainer<GlobalState, Unit, State, Action> {
  override var reduxProps by ObservableReduxProps<State, Action> { _, next ->
    println(next.state?.query)
  }

  // This is one of the only two lifecycle methods that you will need to worry about.
  override fun beforePropInjectionStarts(...) {
    this.search_query.addTextChangedListener(object : TextWatcher() {
      override fun onTextChanged(s: CharSequence?, ...) {
        this@Fragment1.reduxProps.action?.updateQuery?.invoke(s?.toString())
      }
    })
  }

  // And this is the second one.
  override fun afterPropInjectionEnds() {
    // Do some teardown logic.
  }
}
```

As we can see, **Fragment1** has **OutProp** as **Unit**, supports a query value as its internal **State** and is able to handle **updateQuery** action on each keystroke on **search_query** **EditText**. Every time the global state changes, **Fragment1.reduxProps** will be fired and the property change callback will be fired.

Now we can go to our custom **Application** class, **MainApplication**, to set up prop injection for **Fragment1**:

### How property injection works

```java
override fun onCreate() {
  super.onCreate()
  val initialState = ...
  val storeReducer = ...
  val store = FinalStore(initialState, storeReducer)
  val injector = AndroidPropInjector(store)

  injector.injectActivitySerializable(this) { lifecycleOwner ->
      when (lifecycleOwner) {
        is Fragment1 -> this.injectLifecycle(Unit, it) // Oops, compile error
      }
  }
}
```

Why is it raising a compile error? As it turns out, we are only setting up **Fragment1** to receive data, but not how those data will be calculated. This is the job of the [IPropMapper](https://github.com/protoman92/KotlinRedux/blob/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L101):

```java
data class GlobalState(val query: String? = null)

object Redux {
  sealed class Action : IReduxAction {
    data class SetSearchQuery(val query: String?) : Action()
  }

  object Reducer : IReduxReducer<GlobalState> {
    // Map previous state to next state using an IReduxAction.
    override fun invoke(p1: GlobalState, p2: IReduxAction): GlobalState {
      when (p2) {
        is Action -> when (p2) {
          // If the action is a SetSearchQuery action, simply set the query in the
          // global state.
          is Action.SetSearchQuery -> p1.copy(query = p2.query)
        }

        // Otherwise, no valid action was caught, fall back to previous state.
        else -> p1
      }
    }
  }
}

class Fragment1 : Fragment(), IPropContainer<GlobalState, Unit, State, Action> {
  companion object : IPropMapper<GlobalState, Unit, State, Action> {
    // This function has access to the latest GlobalState every time the global
    // state is updated.
    override fun mapState(state: GlobalState, outProps: Unit): State {
      // Extract the query value from the global state.
      return State(state.query)
    }

    // This functio has access to the store's action dispatcher function.
    override fun mapAction(dispatch: IActionDispatcher, outProps: Unit): Action {
      return Action { dispatch(Redux.Action.SetSearchQuery(it)) }
    }
  }
}

// Now we can set up prop injection properly.
class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    val initialState = ...
    val storeReducer = ...
    val store = FinalStore(initialState, storeReducer)
    val injector = AndroidPropInjector(store)

    injector.injectActivitySerializable(this) { lifecycleOwner ->
        when (lifecycleOwner) {
          // Fragment1's companion object is now the mapper.
          is Fragment1 -> this.injectLifecycle(Unit, it, Fragment1)
        }
    }
  }
}
```

### Handling side effects with middlewares

As I was saying, every stroke on the keyboard now sends a **Redux.Action.SetSearchQuery** action to the Redux store. All of the store's middlewares now can intercept this **IReduxAction** to do their funny businesses. One such middleware is the **Saga** middleware, which is the recommended approach to tackling asynchronous work in a Redux system:

```java
fun performSearch(api: ISearchAPI) {
  // Catch all SetSearchQuery actions with a takeLatest (same idea as RxJava.switchMap),
  // then perform async logic.
  return takeLatest(Redux.Action.SetSearchQuery::class, { it.query }) { query ->
    just(query)                                                     // Same as Flowable.just
      .thenMightAsWell(putInStore(Redux.Action.SetLoading(true)))   // Put a true loading flag.
      .mapAsync { q -> this.async { api.search(q) } }               // Perform a search.
      .putInStore { Redux.Action.SetSearchResults(it) }             // Put results back in store.
      .thenNoMatterWhat(putInStore(Redux.Action.SetLoading(false))) // Disable loading no matter what.
  }
}
```

What this logic does is:

- Put a true loading flag into the store so that whichever views responsible for showing progress can catch this flag and set progress bar visibility to VISIBLE;
- Perform the search using **CoroutineScope.async**;
- Put the results of the search back into the store so that it can be propagated to views that handle the display of result items;
- Put a false loading flag into the store to disable progress bar(s);

The beauty of this approach is that the logic lives completely outside of views, which eliminates the need for ViewModel/Presenter/Interactor etc and with them, MVC/MVP/MVVM-related architectures.

If you have used **RxJava** before, you will find yourself right at home: most of **SagaEffects** are just wrappers for **Flowable**. If you have not, I believe this API is much more accessible than **RxJava**'s, because you don't
need to worry about subscriptions, thread handling etc - everything happens on background threads that we don't need to care about. The injection of properties will **always** happen on the main thread, so no more **subscribeOn** and **observeOn**.

Apply the relevant middlewares like so:

```java
val store = applyMiddlewares<GlobalState>(
  createAsyncMiddleware(),
  createRouterMiddleware(Router(this)),
  createSagaMiddleware(arrayListOf(Saga.performSearch(dependency))),
  createThunkMiddleware(dependency)
)(FinalStore(initialState, storeReducer))
```

### Customized OutProp for dependency injection

**OutProp** is **Unit** for **Fragment1**, but it might very well contain non-Redux dependencies for other views, such as a Picasso provider to inject a **Picasso** instance into **Fragment1** for image loading. For example, we can do:

```java
// Fragment2
interface IPicassoProvider {
  val picasso: Picasso
}

data class State(val imageURL: String? = null)
class Action(override val picasso: Picasso) : IPicassoProvider

class Fragment2 : Fragment(), IPropContainer<GlobalState, IPicassoProvider, State, Action> {
  companion object : IPropMapper<GlobalState, IPicassoProvider, State, Action> {
    ...
    override fun mapAction(dispatch: IActionDispatcher, outProp: IPicassoProvider): Action {
      return Action(outProp.picasso)
    }
  }

  override val reduxProps by ObservableReduxProps<State, Action> { _, next ->
    next.state?.imageURL?.also { url ->
      next.action?.picasso?.also { picasso ->
        picasso.load(url).into(this.image_view)
      }
    }
  }
}

// We now initialize a global dependency object that implements IPicassoProvider and pass
// it to Fragment2 as OutProp. The dependency object is local only to MainApplication.onCreate
// and will remain so as long as we use only interfaces in views.
class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    ...
    val dependency = object : IPicassoProvider {
      override val picasso get() = Picasso.get()
    }

    injector.injectActivitySerializable(this) { lifecycleOwner ->
      when (lifecycleOwner) {
        ...
        is Fragment2 -> this.injectLifecycle(dependency, it, Fragment2)
      }
    }
  }
}
```

This kind of dependency injection is easy to understand and do, and remains the only manual injection you will need to do in a Redux system. You won't see the need for complicated DI frameworks anymore.

## Extent of Android support

In the **android** packages, I also provide the **AndroidPropInjector** to inject properties on the main thread and handle lifecycles for **Fragment/Activity** and **RecyclerView.Adapter/ListAdapter** (so no worries about memory leaks). These will suffice for many use cases, and if the need arises I can always add more customized support for other components.

## Demo

There are a bunch of other things that I haven't touched on, but rest assured that the library aims to provide all facets of Android development. In the mean time, check out these demos for a better idea on how other architectures can be replaced by **Redux**:

- [android-sunflower](https://github.com/protoman92/KotlinRedux/tree/master/sample-android/sample-sunflower): This is a rewrite of [sunflower](https://github.com/googlesamples/android-sunflower) to use Redux entirely. There is very little boilerplate and no codegen aside from **Room** DB usage;

- [ReduxForAndroid](https://github.com/protoman92/ReduxForAndroid): Simple music search engine sample that accesses the iTunes store for tracks matching specified queries.
