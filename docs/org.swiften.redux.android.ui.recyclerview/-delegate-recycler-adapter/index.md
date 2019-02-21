[docs](../../index.md) / [org.swiften.redux.android.ui.recyclerview](../index.md) / [DelegateRecyclerAdapter](./index.md)

# DelegateRecyclerAdapter

`abstract class DelegateRecyclerAdapter<GState : `[`LState`](index.md#LState)`, LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp, VH, VHState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, VHAction : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> where VH : `[`IPropContainer`](../../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`VHState`](index.md#VHState)`, `[`VHAction`](index.md#VHAction)`>, VH : `[`IPropLifecycleOwner`](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`LState`](index.md#LState)`, `[`PositionProp`](../-position-prop/index.md)`<`[`OutProp`](index.md#OutProp)`>>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/RecyclerAdapter.kt#L55)

[RecyclerView.Adapter](#) that delegates method calls to another [RecyclerView.Adapter](#).

### Parameters

`GState` - The global state type.

`LState` - The local state type that [GState](index.md#GState) must extend from.

`OutProp` - Property as defined by [adapter](adapter.md)'s parent.

`VH` - The [RecyclerView.ViewHolder](#) instance.

`VHState` - The [VH](index.md#VH) state type. See [ReduxProp.state](../../org.swiften.redux.ui/-redux-prop/state.md).

`VHAction` - The [VH](index.md#VH) action type. See [ReduxProp.action](../../org.swiften.redux.ui/-redux-prop/action.md).

`adapter` - The base [RecyclerView.Adapter](#) instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `DelegateRecyclerAdapter(adapter: <ERROR CLASS><`[`VH`](index.md#VH)`>)`<br>[RecyclerView.Adapter](#) that delegates method calls to another [RecyclerView.Adapter](#). |

### Properties

| Name | Summary |
|---|---|
| [adapter](adapter.md) | `val adapter: <ERROR CLASS><`[`VH`](index.md#VH)`>`<br>The base [RecyclerView.Adapter](#) instance. |
| [viewHolderIDs](view-holder-i-ds.md) | `internal val viewHolderIDs: <ERROR CLASS>`<br>Keep track of all injected [VH](index.md#VH) subscriber IDs here for sweeping unsubscription. Unlike [ReduxListAdapter.composite](../-redux-list-adapter/composite.md), we need to keep track of these IDs because otherwise there is no way to perform [IPropInjector.unsubscribe](../../org.swiften.redux.core/-i-redux-unsubscriber-provider/unsubscribe.md) for each [VH](index.md#VH) instances. |

### Functions

| Name | Summary |
|---|---|
| [cleanUpSubscriptions](clean-up-subscriptions.md) | `internal fun cleanUpSubscriptions(injector: `[`IPropInjector`](../../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](index.md#GState)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Clean up [VH](index.md#VH) subscriptions with [viewHolderIDs](view-holder-i-ds.md). |
| [getItemId](get-item-id.md) | `open fun getItemId(position: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): <ERROR CLASS>` |
| [getItemViewType](get-item-view-type.md) | `open fun getItemViewType(position: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): <ERROR CLASS>` |
| [onAttachedToRecyclerView](on-attached-to-recycler-view.md) | `open fun onAttachedToRecyclerView(recyclerView: <ERROR CLASS>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onCreateViewHolder](on-create-view-holder.md) | `open fun onCreateViewHolder(parent: <ERROR CLASS>, viewType: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`VH`](index.md#VH) |
| [onDetachedFromRecyclerView](on-detached-from-recycler-view.md) | `open fun onDetachedFromRecyclerView(recyclerView: <ERROR CLASS>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onFailedToRecycleView](on-failed-to-recycle-view.md) | `open fun onFailedToRecycleView(holder: `[`VH`](index.md#VH)`): <ERROR CLASS>` |
| [onViewAttachedToWindow](on-view-attached-to-window.md) | `open fun onViewAttachedToWindow(holder: `[`VH`](index.md#VH)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onViewDetachedFromWindow](on-view-detached-from-window.md) | `open fun onViewDetachedFromWindow(holder: `[`VH`](index.md#VH)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onViewRecycled](on-view-recycled.md) | `open fun onViewRecycled(holder: `[`VH`](index.md#VH)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [registerAdapterDataObserver](register-adapter-data-observer.md) | `open fun registerAdapterDataObserver(observer: <ERROR CLASS>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setHasStableIds](set-has-stable-ids.md) | `open fun setHasStableIds(hasStableIds: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [unregisterAdapterDataObserver](unregister-adapter-data-observer.md) | `open fun unregisterAdapterDataObserver(observer: <ERROR CLASS>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
