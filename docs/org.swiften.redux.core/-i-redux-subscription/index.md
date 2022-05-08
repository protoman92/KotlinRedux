[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IReduxSubscription](./index.md)

# IReduxSubscription

`interface IReduxSubscription : `[`IUniqueIDProvider`](../-i-unique-i-d-provider/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-core\src\main\kotlin/org/swiften/redux/core/Subscription.kt#L17)

Represents a subscription object that can be unsubscribed from. Each [IReduxSubscription](./index.md)
must also have an [uniqueID](../-i-unique-i-d-provider/unique-i-d.md) that can be used to perform selective unsubscription.

### Inherited Properties

| Name | Summary |
|---|---|
| [uniqueID](../-i-unique-i-d-provider/unique-i-d.md) | `abstract val uniqueID: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>The unique ID to pass to [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md) and [IReduxStore.unsubscribe](../-i-redux-unsubscriber-provider/unsubscribe.md). |

### Functions

| Name | Summary |
|---|---|
| [isUnsubscribed](is-unsubscribed.md) | `abstract fun isUnsubscribed(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Check if this [IReduxSubscription](./index.md) is unsubscribed. |
| [unsubscribe](unsubscribe.md) | `abstract fun unsubscribe(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Unsubscribe from this [IReduxSubscription](./index.md). |

### Inheritors

| Name | Summary |
|---|---|
| [ICompositeReduxSubscription](../-i-composite-redux-subscription/index.md) | `interface ICompositeReduxSubscription : `[`IReduxSubscription`](./index.md) |
| [ReduxSubscription](../-redux-subscription/index.md) | `class ReduxSubscription : `[`IReduxSubscription`](./index.md)<br>Use this class to perform some [unsubscribe](../-redux-subscription/unsubscribe.md) logic. For e.g.: terminate a [IReduxSubscription](./index.md) from [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md). |
