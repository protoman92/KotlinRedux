[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [ReduxSubscription](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`ReduxSubscription(uniqueID: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, _unsubscribe: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`)`

Use this class to perform some [unsubscribe](unsubscribe.md) logic. For e.g.: terminate a [IReduxSubscription](../-i-redux-subscription/index.md)
from [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md).

### Parameters

`uniqueID` - See [IReduxSubscription.uniqueID](../-i-unique-i-d-provider/unique-i-d.md).

`_unsubscribe` - Function that contains unsubscription logic.