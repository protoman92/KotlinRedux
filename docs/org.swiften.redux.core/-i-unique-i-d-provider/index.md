[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [IUniqueIDProvider](./index.md)

# IUniqueIDProvider

`interface IUniqueIDProvider` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/SubscriberID.kt#L12)

Provide a unique subscriber ID for [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md).

### Properties

| Name | Summary |
|---|---|
| [uniqueID](unique-i-d.md) | `abstract val uniqueID: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The unique ID to pass to [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md) and [IReduxStore.unsubscribe](../-i-redux-unsubscriber-provider/unsubscribe.md). |

### Inheritors

| Name | Summary |
|---|---|
| [DefaultUniqueIDProvider](../-default-unique-i-d-provider/index.md) | `class DefaultUniqueIDProvider : `[`IUniqueIDProvider`](./index.md)<br>Default implementation of [IUniqueIDProvider](./index.md) that simply uses [UUID.randomUUID](http://docs.oracle.com/javase/6/docs/api/java/util/UUID.html#randomUUID()). |
| [IReduxSubscription](../-i-redux-subscription/index.md) | `interface IReduxSubscription : `[`IUniqueIDProvider`](./index.md)<br>Represents a subscription object that can be unsubscribed from. Each [IReduxSubscription](../-i-redux-subscription/index.md) must also have an [uniqueID](unique-i-d.md) that can be used to perform selective unsubscription. |
| [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md) | `interface ISagaOutput<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IAsyncJob`](../-i-async-job/index.md)`<`[`T`](../../org.swiften.redux.saga.common/-i-saga-output/index.md#T)`>, `[`IUniqueIDProvider`](./index.md)<br>Stream values for a [ISagaEffect](../../org.swiften.redux.saga.common/-i-saga-effect.md). This stream has functional operators that can transform emitted values. |
| [ReduxListAdapter](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md) | `abstract class ReduxListAdapter<GState : `[`LState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#LState)`, LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp, VH, VHState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, VHAction : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IUniqueIDProvider`](./index.md)`, `[`IPropLifecycleOwner`](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`LState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#LState)`, `[`OutProp`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#OutProp)`>, `[`IPropContainer`](../../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`VHState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#VHState)`>, `[`VHAction`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#VHAction)`>`<br>Custom Redux-compatible [ListAdapter](#) implementation. This [ListAdapter](#) can receive [ReduxProp](../../org.swiften.redux.ui/-redux-prop/index.md) in order to call [ListAdapter.submitList](#). |
| [SagaOutput](../../org.swiften.redux.saga.rx/-saga-output/index.md) | `class SagaOutput<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`ISagaOutput`](../../org.swiften.redux.saga.common/-i-saga-output/index.md)`<`[`T`](../../org.swiften.redux.saga.rx/-saga-output/index.md#T)`>, `[`IUniqueIDProvider`](./index.md)<br>This is the default implementation of [ISagaOutput](../../org.swiften.redux.saga.common/-i-saga-output/index.md). Every time a new [SagaOutput](../../org.swiften.redux.saga.rx/-saga-output/index.md) is created, [monitor](../../org.swiften.redux.saga.rx/-saga-output/monitor.md) will keep track of its [onAction](../../org.swiften.redux.saga.rx/-saga-output/on-action.md) to call on [ISagaMonitor.dispatch](../-i-dispatcher-provider/dispatch.md), and when said [SagaOutput](../../org.swiften.redux.saga.rx/-saga-output/index.md) is disposed of, [monitor](../../org.swiften.redux.saga.rx/-saga-output/monitor.md) will remove the reference. |
