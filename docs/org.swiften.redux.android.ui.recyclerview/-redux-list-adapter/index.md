[docs](../../index.md) / [org.swiften.redux.android.ui.recyclerview](../index.md) / [ReduxListAdapter](./index.md)

# ReduxListAdapter

`abstract class ReduxListAdapter<GState, GExt, VH, S, A> : `[`IPropLifecycleOwner`](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`GState`](index.md#GState)`, `[`GExt`](index.md#GExt)`>, `[`IPropContainer`](../../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`S`](index.md#S)`>, `[`A`](index.md#A)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/DiffedAdapter.kt#L37)

Custom Redux-compatible [ListAdapter](#) implementation. This [ListAdapter](#) can receive [ReduxProps](../../org.swiften.redux.ui/-redux-props/index.md)
in order to call [ListAdapter.submitList](#).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ReduxListAdapter(adapter: <ERROR CLASS><`[`VH`](index.md#VH)`>, diffCallback: <ERROR CLASS><`[`S`](index.md#S)`>)`<br>Custom Redux-compatible [ListAdapter](#) implementation. This [ListAdapter](#) can receive [ReduxProps](../../org.swiften.redux.ui/-redux-props/index.md) in order to call [ListAdapter.submitList](#). |

### Properties

| Name | Summary |
|---|---|
| [adapter](adapter.md) | `val adapter: <ERROR CLASS><`[`VH`](index.md#VH)`>` |
| [reduxProps](redux-props.md) | `open var reduxProps: <ERROR CLASS>`<br>Since we are only calling [ListAdapter.submitList](#) when [reduxProps](redux-props.md) arrives, the [VariableProps.action](../../org.swiften.redux.ui/-variable-props/action.md) instance must be non-null upon [onBindViewHolder](#). As a result, we can safely access [VariableProps.action](../../org.swiften.redux.ui/-variable-props/action.md) in [onBindViewHolder](#). |

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
| [toString](to-string.md) | `open fun toString(): <ERROR CLASS>` |
| [unregisterAdapterDataObserver](unregister-adapter-data-observer.md) | `open fun unregisterAdapterDataObserver(observer: <ERROR CLASS>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Extension Functions

| Name | Summary |
|---|---|
| [unsubscribeSafely](../../org.swiften.redux.ui/unsubscribe-safely.md) | `fun `[`IPropContainer`](../../org.swiften.redux.ui/-i-prop-container/index.md)`<*, *>.unsubscribeSafely(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>Unsubscribe from [IPropContainer.reduxProps](../../org.swiften.redux.ui/-i-prop-container/redux-props.md) safely, i.e. catch [UninitializedPropertyAccessException](#) because this is most probably declared as lateinit in Kotlin code, and catch [NullPointerException](http://docs.oracle.com/javase/6/docs/api/java/lang/NullPointerException.html) to satisfy Java code. Also return the [ReduxSubscription.id](../../org.swiften.redux.core/-redux-subscription/id.md) that can be used to track and remove the relevant [ReduxSubscription](../../org.swiften.redux.core/-redux-subscription/index.md) from other containers. |
