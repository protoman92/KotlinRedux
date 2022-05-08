[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [ReduxSubscription](./index.md)

# ReduxSubscription

`class ReduxSubscription : `[`IReduxSubscription`](../-i-redux-subscription/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-core\src\main\kotlin/org/swiften/redux/core/Subscription.kt#L49)

Use this class to perform some [unsubscribe](unsubscribe.md) logic. For e.g.: terminate a [IReduxSubscription](../-i-redux-subscription/index.md)
from [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md).

### Parameters

`uniqueID` - See [IReduxSubscription.uniqueID](../-i-unique-i-d-provider/unique-i-d.md).

`_unsubscribe` - Function that contains unsubscription logic.

### Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | `companion object Companion : `[`IUniqueIDProvider`](../-i-unique-i-d-provider/index.md) |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ReduxSubscription(uniqueID: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, _unsubscribe: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`)`<br>Use this class to perform some [unsubscribe](unsubscribe.md) logic. For e.g.: terminate a [IReduxSubscription](../-i-redux-subscription/index.md) from [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md). |

### Properties

| Name | Summary |
|---|---|
| [_isUnsubscribed](_is-unsubscribed.md) | `val _isUnsubscribed: `[`AtomicBoolean`](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/atomic/AtomicBoolean.html) |
| [_unsubscribe](_unsubscribe.md) | `val _unsubscribe: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Function that contains unsubscription logic. |
| [uniqueID](unique-i-d.md) | `val uniqueID: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>See [IReduxSubscription.uniqueID](../-i-unique-i-d-provider/unique-i-d.md). |

### Functions

| Name | Summary |
|---|---|
| [isUnsubscribed](is-unsubscribed.md) | `fun isUnsubscribed(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Check if this [IReduxSubscription](../-i-redux-subscription/index.md) is unsubscribed. |
| [unsubscribe](unsubscribe.md) | `fun unsubscribe(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Unsubscribe from this [IReduxSubscription](../-i-redux-subscription/index.md). |

### Companion Object Properties

| Name | Summary |
|---|---|
| [EMPTY](-e-m-p-t-y.md) | `val EMPTY: `[`ReduxSubscription`](./index.md)<br>Mock [ReduxSubscription](./index.md) that is used every time there is no meaningful subscription logic to be unsubscribed on. |
