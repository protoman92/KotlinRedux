[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [CompositeReduxSubscription](index.md) / [remove](./remove.md)

# remove

`fun remove(subscribeId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`IReduxSubscription`](../-i-redux-subscription/index.md)`?` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Subscription.kt#L91)

Remove an [IReduxSubscription](../-i-redux-subscription/index.md) instance whose [IReduxSubscription.uniqueSubscriberID](../-i-subscriber-i-d-provider/unique-subscriber-i-d.md) equals
[uniqueSubscriberID](unique-subscriber-i-d.md).

### Parameters

`subscribeId` - A [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) value.

**Return**
The remove [IReduxSubscription](../-i-redux-subscription/index.md), if any.

`fun remove(subscription: `[`IReduxSubscription`](../-i-redux-subscription/index.md)`): `[`IReduxSubscription`](../-i-redux-subscription/index.md)`?` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Subscription.kt#L100)

Remove an [IReduxSubscription](../-i-redux-subscription/index.md) from [subscriptions](subscriptions.md).

### Parameters

`subscription` - An [IReduxSubscription](../-i-redux-subscription/index.md) instance.

**Return**
The remove [IReduxSubscription](../-i-redux-subscription/index.md), if any.

