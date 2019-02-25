[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [ICompositeReduxSubscription](index.md) / [remove](./remove.md)

# remove

`abstract fun remove(subscribeId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`IReduxSubscription`](../-i-redux-subscription/index.md)`?` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Subscription.kt#L40)

Remove an [IReduxSubscription](../-i-redux-subscription/index.md) instance whose [IReduxSubscription.uniqueID](../-i-unique-i-d-provider/unique-i-d.md) equals [uniqueID](#).

### Parameters

`subscribeId` - A [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) value.

**Return**
The remove [IReduxSubscription](../-i-redux-subscription/index.md), if any.

