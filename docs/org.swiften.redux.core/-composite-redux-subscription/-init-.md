[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [CompositeReduxSubscription](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`CompositeReduxSubscription(uniqueSubscriberID: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`)`

Composite [IReduxSubscription](../-i-redux-subscription/index.md) that may contain other [IReduxSubscription](../-i-redux-subscription/index.md), and when
[IReduxSubscription.unsubscribe](../-i-redux-subscription/unsubscribe.md) is called, all the children [IReduxSubscription](../-i-redux-subscription/index.md) will also
be unsubscribed from.

### Parameters

`uniqueSubscriberID` - See [IReduxSubscription.uniqueSubscriberID](../-i-subscriber-i-d-provider/unique-subscriber-i-d.md).