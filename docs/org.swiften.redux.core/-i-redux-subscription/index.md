[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IReduxSubscription](./index.md)

# IReduxSubscription

`interface IReduxSubscription` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Subscription.kt#L18)

Represents a subscription object that can be unsubscribed from. Each [IReduxSubscription](./index.md)
must also have an [id](id.md) that can be used to perform selective unsubscription.

### Properties

| Name | Summary |
|---|---|
| [id](id.md) | `abstract val id: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Functions

| Name | Summary |
|---|---|
| [isUnsubscribed](is-unsubscribed.md) | `abstract fun isUnsubscribed(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [unsubscribe](unsubscribe.md) | `abstract fun unsubscribe(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Inheritors

| Name | Summary |
|---|---|
| [CompositeReduxSubscription](../-composite-redux-subscription/index.md) | `class CompositeReduxSubscription : `[`IReduxSubscription`](./index.md)<br>Composite [IReduxSubscription](./index.md) that may contain other [IReduxSubscription](./index.md), and when [IReduxSubscription.unsubscribe](unsubscribe.md) is called, all the children [IReduxSubscription](./index.md) will also be unsubscribed from. |
| [ReduxSubscription](../-redux-subscription/index.md) | `class ReduxSubscription : `[`IReduxSubscription`](./index.md)<br>Use this class to perform some [unsubscribe](../-redux-subscription/unsubscribe.md) logic. For e.g.: terminate a [IReduxSubscription](./index.md) from [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md). |
