[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [CompositeReduxSubscription](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`private CompositeReduxSubscription(uniqueID: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`)`

Composite [IReduxSubscription](../-i-redux-subscription/index.md) that may contain other [IReduxSubscription](../-i-redux-subscription/index.md), and when
[IReduxSubscription.unsubscribe](../-i-redux-subscription/unsubscribe.md) is called, all the children [IReduxSubscription](../-i-redux-subscription/index.md) will also
be unsubscribed from.

### Parameters

`uniqueID` - See [IReduxSubscription.uniqueID](../-i-unique-i-d-provider/unique-i-d.md).