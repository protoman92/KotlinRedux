[docs](../index.md) / [org.swiften.redux.core](index.md) / [IReduxSubscriber](./-i-redux-subscriber.md)

# IReduxSubscriber

`typealias IReduxSubscriber<GlobalState> = (`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, (`[`GlobalState`](-i-redux-subscriber.md#GlobalState)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`) -> `[`IReduxSubscription`](-i-redux-subscription/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Core.kt#L25)

Subscribe to state updates with a callback. The subscriber id should be a unique id that
identifies that subscriber. The resulting [IReduxSubscription](-i-redux-subscription/index.md) can be used to unsubscribe.

