[docs](../index.md) / [org.swiften.redux.core](index.md) / [IReduxSubscriber](./-i-redux-subscriber.md)

# IReduxSubscriber

`typealias IReduxSubscriber<GState> = (`[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, (`[`GState`](-i-redux-subscriber.md#GState)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`) -> `[`IReduxSubscription`](-i-redux-subscription/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-core\src\main\kotlin/org/swiften/redux/core/Core.kt#L31)

Subscribe to state updates with a callback. The subscriber id should be a unique id that
identifies that subscriber. The resulting [IReduxSubscription](-i-redux-subscription/index.md) can be used to unsubscribe.

### Parameters

`GState` - The global state type.