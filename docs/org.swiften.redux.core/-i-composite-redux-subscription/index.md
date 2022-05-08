[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [ICompositeReduxSubscription](./index.md)

# ICompositeReduxSubscription

`interface ICompositeReduxSubscription : `[`IReduxSubscription`](../-i-redux-subscription/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-core\src\main\kotlin/org/swiften/redux/core/Subscription.kt#L28)

### Functions

| Name | Summary |
|---|---|
| [add](add.md) | `abstract fun add(subscription: `[`IReduxSubscription`](../-i-redux-subscription/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Add an [IReduxSubscription](../-i-redux-subscription/index.md). |
| [remove](remove.md) | `abstract fun remove(subscribeId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`IReduxSubscription`](../-i-redux-subscription/index.md)`?`<br>Remove an [IReduxSubscription](../-i-redux-subscription/index.md) instance whose [IReduxSubscription.uniqueID](../-i-unique-i-d-provider/unique-i-d.md) equals [uniqueID](#). |

### Inherited Functions

| Name | Summary |
|---|---|
| [isUnsubscribed](../-i-redux-subscription/is-unsubscribed.md) | `abstract fun isUnsubscribed(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Check if this [IReduxSubscription](../-i-redux-subscription/index.md) is unsubscribed. |
| [unsubscribe](../-i-redux-subscription/unsubscribe.md) | `abstract fun unsubscribe(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Unsubscribe from this [IReduxSubscription](../-i-redux-subscription/index.md). |

### Extension Functions

| Name | Summary |
|---|---|
| [remove](../remove.md) | `fun `[`ICompositeReduxSubscription`](./index.md)`.remove(subscription: `[`IReduxSubscription`](../-i-redux-subscription/index.md)`): `[`IReduxSubscription`](../-i-redux-subscription/index.md)`?`<br>Remove an [IReduxSubscription](../-i-redux-subscription/index.md). |

### Inheritors

| Name | Summary |
|---|---|
| [CompositeReduxSubscription](../-composite-redux-subscription/index.md) | `class CompositeReduxSubscription : `[`ICompositeReduxSubscription`](./index.md)<br>Composite [IReduxSubscription](../-i-redux-subscription/index.md) that may contain other [IReduxSubscription](../-i-redux-subscription/index.md), and when [IReduxSubscription.unsubscribe](../-i-redux-subscription/unsubscribe.md) is called, all the children [IReduxSubscription](../-i-redux-subscription/index.md) will also be unsubscribed from. |
