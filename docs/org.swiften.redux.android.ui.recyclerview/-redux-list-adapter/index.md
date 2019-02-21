[docs](../../index.md) / [org.swiften.redux.android.ui.recyclerview](../index.md) / [ReduxListAdapter](./index.md)

# ReduxListAdapter

`abstract class ReduxListAdapter<GState : `[`LState`](index.md#LState)`, LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp, VH, VHState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, VHAction : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`ISubscriberIDProvider`](../../org.swiften.redux.core/-i-subscriber-i-d-provider/index.md)`, `[`IPropLifecycleOwner`](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`LState`](index.md#LState)`, `[`OutProp`](index.md#OutProp)`>, `[`IPropContainer`](../../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`VHState`](index.md#VHState)`>, `[`VHAction`](index.md#VHAction)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/DiffedAdapter.kt#L52)

Custom Redux-compatible [ListAdapter](#) implementation. This [ListAdapter](#) can receive [ReduxProp](../../org.swiften.redux.ui/-redux-prop/index.md)
in order to call [ListAdapter.submitList](#).

### Parameters

`GState` - The global state type.

`LState` - The local state type that [GState](index.md#GState) must extend from.

`OutProp` - Property as defined by [adapter](adapter.md)'s parent.

`VH` - The [RecyclerView.ViewHolder](#) instance.

`VHState` - The [VH](index.md#VH) state type. See [ReduxProp.state](../../org.swiften.redux.ui/-redux-prop/state.md).

`VHAction` - The [VH](index.md#VH) action type. See [ReduxProp.action](../../org.swiften.redux.ui/-redux-prop/action.md).

`adapter` - The base [RecyclerView.Adapter](#) instance.

`diffCallback` - A [DiffUtil.ItemCallback](#) instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ReduxListAdapter(adapter: <ERROR CLASS><`[`VH`](index.md#VH)`>, diffCallback: <ERROR CLASS><`[`VHState`](index.md#VHState)`>)`<br>Custom Redux-compatible [ListAdapter](#) implementation. This [ListAdapter](#) can receive [ReduxProp](../../org.swiften.redux.ui/-redux-prop/index.md) in order to call [ListAdapter.submitList](#). |

### Properties

| Name | Summary |
|---|---|
| [adapter](adapter.md) | `val adapter: <ERROR CLASS><`[`VH`](index.md#VH)`>`<br>The base [RecyclerView.Adapter](#) instance. |
| [composite](composite.md) | `internal val composite: `[`CompositeReduxSubscription`](../../org.swiften.redux.core/-composite-redux-subscription/index.md)<br>Since we will be manually injecting prop into [VH](index.md#VH) instances, we will need to collect their [ReduxSubscription](../../org.swiften.redux.core/-redux-subscription/index.md) here. We need a [CompositeReduxSubscription](../../org.swiften.redux.core/-composite-redux-subscription/index.md) here, unlike [DelegateRecyclerAdapter.viewHolderIDs](../-delegate-recycler-adapter/view-holder-i-ds.md), because we are not using [IPropInjector.inject](../../org.swiften.redux.ui/-i-prop-injector/inject.md) directly on [VH](index.md#VH) instances. There is thus no need to keep track of [VH](index.md#VH) subscriber IDs to perform [IPropInjector.unsubscribe](../../org.swiften.redux.core/-i-redux-unsubscriber-provider/unsubscribe.md), but we do need a [CompositeReduxSubscription](../../org.swiften.redux.core/-composite-redux-subscription/index.md) to ensure [IPropLifecycleOwner.afterPropInjectionEnds](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/after-prop-injection-ends.md) is properly called when we do a sweeping unsubscription. |
| [reduxProp](redux-prop.md) | `open var reduxProp: <ERROR CLASS>`<br>Since we are only calling [ListAdapter.submitList](#) when [reduxProp](redux-prop.md) arrives, the [ReduxProp.action](../../org.swiften.redux.ui/-redux-prop/action.md) instance must be non-null upon [onBindViewHolder](#). As a result, we can safely access [ReduxProp.action](../../org.swiften.redux.ui/-redux-prop/action.md) in [onBindViewHolder](#). |
| [staticProp](static-prop.md) | `internal lateinit var staticProp: `[`StaticProp`](../../org.swiften.redux.ui/-static-prop/index.md)`<`[`LState`](index.md#LState)`, `[`OutProp`](index.md#OutProp)`>` |

### Functions

| Name | Summary |
|---|---|
| [beforePropInjectionStarts](before-prop-injection-starts.md) | `open fun beforePropInjectionStarts(sp: `[`StaticProp`](../../org.swiften.redux.ui/-static-prop/index.md)`<`[`LState`](index.md#LState)`, `[`OutProp`](index.md#OutProp)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This is called before [IFullPropInjector.inject](../../org.swiften.redux.ui/-i-prop-injector/inject.md) is called. |
| [cleanUpSubscriptions](clean-up-subscriptions.md) | `internal fun cleanUpSubscriptions(injector: `[`IPropInjector`](../../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](index.md#GState)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Clean up all subscriptions with [composite](composite.md). |
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
| [toString](to-string.md) | `open fun toString(): <ERROR CLASS>` |
| [unregisterAdapterDataObserver](unregister-adapter-data-observer.md) | `open fun unregisterAdapterDataObserver(observer: <ERROR CLASS>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
