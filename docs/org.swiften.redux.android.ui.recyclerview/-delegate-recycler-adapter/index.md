[docs](../../index.md) / [org.swiften.redux.android.ui.recyclerview](../index.md) / [DelegateRecyclerAdapter](./index.md)

# DelegateRecyclerAdapter

`abstract class DelegateRecyclerAdapter<GlobalState, VH : `[`IPropContainer`](../../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`GlobalState`](index.md#GlobalState)`, `[`VHState`](index.md#VHState)`, `[`VHAction`](index.md#VHAction)`>, VHState, VHAction>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/RecyclerAdapter.kt#L33)

[RecyclerView.Adapter](#) that delegates method calls to another [RecyclerView.Adapter](#)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `DelegateRecyclerAdapter(adapter: <ERROR CLASS><`[`VH`](index.md#VH)`>)`<br>[RecyclerView.Adapter](#) that delegates method calls to another [RecyclerView.Adapter](#) |

### Properties

| Name | Summary |
|---|---|
| [adapter](adapter.md) | `val adapter: <ERROR CLASS><`[`VH`](index.md#VH)`>` |
| [composite](composite.md) | `val composite: `[`CompositeReduxSubscription`](../../org.swiften.redux.core/-composite-redux-subscription/index.md) |

### Functions

| Name | Summary |
|---|---|
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
| [unsubscribeSafely](unsubscribe-safely.md) | `internal fun unsubscribeSafely(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Since we will be performing [IPropInjector.inject](../../org.swiften.redux.ui/-i-prop-injector/inject.md) for [VH](index.md#VH) instances, we will be be using [CompositeReduxSubscription.add](../../org.swiften.redux.core/-composite-redux-subscription/add.md) a lot every time [RecyclerView.Adapter.onBindViewHolder](#) is called. As a result, calling this method will ensure proper deinitialization. |