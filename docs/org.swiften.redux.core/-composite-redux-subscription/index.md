[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [CompositeReduxSubscription](./index.md)

# CompositeReduxSubscription

`class CompositeReduxSubscription : `[`IReduxSubscription`](../-i-redux-subscription/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Subscription.kt#L60)

Composite [IReduxSubscription](../-i-redux-subscription/index.md) that may contain other [IReduxSubscription](../-i-redux-subscription/index.md), and when
[IReduxSubscription.unsubscribe](../-i-redux-subscription/unsubscribe.md) is called, all the children [IReduxSubscription](../-i-redux-subscription/index.md) will also
be unsubscribed from.

### Parameters

`uniqueID` - See [IReduxSubscription.uniqueID](../-i-unique-i-d-provider/unique-i-d.md).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CompositeReduxSubscription(uniqueID: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`)`<br>Composite [IReduxSubscription](../-i-redux-subscription/index.md) that may contain other [IReduxSubscription](../-i-redux-subscription/index.md), and when [IReduxSubscription.unsubscribe](../-i-redux-subscription/unsubscribe.md) is called, all the children [IReduxSubscription](../-i-redux-subscription/index.md) will also be unsubscribed from. |

### Properties

| Name | Summary |
|---|---|
| [_isUnsubscribed](_is-unsubscribed.md) | `var _isUnsubscribed: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [lock](lock.md) | `val lock: `[`ReentrantReadWriteLock`](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/locks/ReentrantReadWriteLock.html) |
| [subscriptions](subscriptions.md) | `val subscriptions: <ERROR CLASS>` |
| [uniqueID](unique-i-d.md) | `val uniqueID: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>See [IReduxSubscription.uniqueID](../-i-unique-i-d-provider/unique-i-d.md). |

### Functions

| Name | Summary |
|---|---|
| [add](add.md) | `fun add(subscription: `[`IReduxSubscription`](../-i-redux-subscription/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Add an [IReduxSubscription](../-i-redux-subscription/index.md) to [subscriptions](subscriptions.md). |
| [isUnsubscribed](is-unsubscribed.md) | `fun isUnsubscribed(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Check if this [IReduxSubscription](../-i-redux-subscription/index.md) is unsubscribed. |
| [remove](remove.md) | `fun remove(subscribeId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`IReduxSubscription`](../-i-redux-subscription/index.md)`?`<br>Remove an [IReduxSubscription](../-i-redux-subscription/index.md) instance whose [IReduxSubscription.uniqueID](../-i-unique-i-d-provider/unique-i-d.md) equals [uniqueID](unique-i-d.md).`fun remove(subscription: `[`IReduxSubscription`](../-i-redux-subscription/index.md)`): `[`IReduxSubscription`](../-i-redux-subscription/index.md)`?`<br>Remove an [IReduxSubscription](../-i-redux-subscription/index.md) from [subscriptions](subscriptions.md). |
| [unsubscribe](unsubscribe.md) | `fun unsubscribe(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Unsubscribe from this [IReduxSubscription](../-i-redux-subscription/index.md). |
