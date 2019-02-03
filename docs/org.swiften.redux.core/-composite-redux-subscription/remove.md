[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [CompositeReduxSubscription](index.md) / [remove](./remove.md)

# remove

`fun remove(subscribeId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Subscription.kt#L92)

Remove an [IReduxSubscription](../-i-redux-subscription/index.md) instance whose [IReduxSubscription.id](../-i-redux-subscription/id.md) equals [id](id.md).

### Parameters

`subscribeId` - A [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) value.`fun remove(subscription: `[`IReduxSubscription`](../-i-redux-subscription/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Subscription.kt#L100)

Remove an [IReduxSubscription](../-i-redux-subscription/index.md) from [subscriptions](subscriptions.md).

### Parameters

`subscription` - An [IReduxSubscription](../-i-redux-subscription/index.md) instance.