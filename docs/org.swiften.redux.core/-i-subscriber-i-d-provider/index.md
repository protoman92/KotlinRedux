[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [ISubscriberIDProvider](./index.md)

# ISubscriberIDProvider

`interface ISubscriberIDProvider` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/SubscriberID.kt#L12)

Provide a unique subscriber ID for [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md).

### Properties

| Name | Summary |
|---|---|
| [uniqueSubscriberID](unique-subscriber-i-d.md) | `abstract val uniqueSubscriberID: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The unique ID to pass to [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md) and [IReduxStore.unsubscribe](../-i-redux-unsubscriber-provider/unsubscribe.md). |

### Inheritors

| Name | Summary |
|---|---|
| [DefaultSubscriberIDProvider](../-default-subscriber-i-d-provider/index.md) | `class DefaultSubscriberIDProvider : `[`ISubscriberIDProvider`](./index.md)<br>Default implementation of [ISubscriberIDProvider](./index.md) that simply uses [UUID.randomUUID](http://docs.oracle.com/javase/6/docs/api/java/util/UUID.html#randomUUID()). |
| [IReduxSubscription](../-i-redux-subscription/index.md) | `interface IReduxSubscription : `[`ISubscriberIDProvider`](./index.md)<br>Represents a subscription object that can be unsubscribed from. Each [IReduxSubscription](../-i-redux-subscription/index.md) must also have an [uniqueSubscriberID](unique-subscriber-i-d.md) that can be used to perform selective unsubscription. |
| [ReduxListAdapter](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md) | `abstract class ReduxListAdapter<GState : `[`LState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#LState)`, LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp, VH, VHState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, VHAction : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`ISubscriberIDProvider`](./index.md)`, `[`IPropLifecycleOwner`](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`LState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#LState)`, `[`OutProp`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#OutProp)`>, `[`IPropContainer`](../../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`VHState`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#VHState)`>, `[`VHAction`](../../org.swiften.redux.android.ui.recyclerview/-redux-list-adapter/index.md#VHAction)`>`<br>Custom Redux-compatible [ListAdapter](#) implementation. This [ListAdapter](#) can receive [ReduxProp](../../org.swiften.redux.ui/-redux-prop/index.md) in order to call [ListAdapter.submitList](#). |
