[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [ReduxSubscription](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`ReduxSubscription(uniqueSubscriberID: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, _unsubscribe: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`)`

Use this class to perform some [unsubscribe](unsubscribe.md) logic. For e.g.: terminate a [IReduxSubscription](../-i-redux-subscription/index.md)
from [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md).

### Parameters

`uniqueSubscriberID` - See [IReduxSubscription.uniqueSubscriberID](../-i-subscriber-i-d-provider/unique-subscriber-i-d.md).

`_unsubscribe` - Function that contains unsubscription logic.