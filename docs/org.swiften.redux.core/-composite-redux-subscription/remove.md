[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [CompositeReduxSubscription](index.md) / [remove](./remove.md)

# remove

`fun remove(subscribeId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`IReduxSubscription`](../-i-redux-subscription/index.md)`?` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Subscription.kt#L123)

Overrides [ICompositeReduxSubscription.remove](../-i-composite-redux-subscription/remove.md)

Remove an [IReduxSubscription](../-i-redux-subscription/index.md) instance whose [IReduxSubscription.uniqueID](../-i-unique-i-d-provider/unique-i-d.md) equals [uniqueID](#).

### Parameters

`subscribeId` - A [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) value.

**Return**
The remove [IReduxSubscription](../-i-redux-subscription/index.md), if any.

