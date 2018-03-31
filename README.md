# KotlinRedux

[![Build Status](https://travis-ci.org/protoman92/KotlinRedux.svg?branch=master)](https://travis-ci.org/protoman92/KotlinRedux)

Reactive Redux implementation for Kotlin. Set up a **TreeState**-based store with:

```kotlin
val store = RxTreeStore.create<Any>(reducer, TreeState.empty())
```

And subscribe to state values as follows:

```kotlin
val store = RxTreeStore.create<Any>(reducer, TreeState.empty())
store.valueStream("a.b.c").subscribe()
```

We can either dispatch actions with **store.dispatch(actions)** or bind an action stream to **store.actionReceiver**. Since the **actionReceiver** ignores error/complete events, we do not have to worry about the stream dying.
