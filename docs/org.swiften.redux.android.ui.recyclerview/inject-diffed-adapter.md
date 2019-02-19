[docs](../index.md) / [org.swiften.redux.android.ui.recyclerview](index.md) / [injectDiffedAdapter](./inject-diffed-adapter.md)

# injectDiffedAdapter

`fun <GState : `[`LState`](inject-diffed-adapter.md#LState)`, LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp, VH, VHState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, VHAction : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-diffed-adapter.md#GState)`>.injectDiffedAdapter(outProp: `[`OutProp`](inject-diffed-adapter.md#OutProp)`, adapter: <ERROR CLASS><`[`VH`](inject-diffed-adapter.md#VH)`>, adapterMapper: `[`IPropMapper`](../org.swiften.redux.ui/-i-prop-mapper.md)`<`[`LState`](inject-diffed-adapter.md#LState)`, `[`OutProp`](inject-diffed-adapter.md#OutProp)`, `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`VHState`](inject-diffed-adapter.md#VHState)`>, `[`VHAction`](inject-diffed-adapter.md#VHAction)`>, diffCallback: <ERROR CLASS><`[`VHState`](inject-diffed-adapter.md#VHState)`>): `[`ReduxListAdapter`](-redux-list-adapter/index.md)`<`[`GState`](inject-diffed-adapter.md#GState)`, `[`LState`](inject-diffed-adapter.md#LState)`, `[`OutProp`](inject-diffed-adapter.md#OutProp)`, `[`VH`](inject-diffed-adapter.md#VH)`, `[`VHState`](inject-diffed-adapter.md#VHState)`, `[`VHAction`](inject-diffed-adapter.md#VHAction)`> where VH : `[`IPropContainer`](../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`VHState`](inject-diffed-adapter.md#VHState)`, `[`VHAction`](inject-diffed-adapter.md#VHAction)`>, VH : `[`IPropLifecycleOwner`](../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`LState`](inject-diffed-adapter.md#LState)`, `[`OutProp`](inject-diffed-adapter.md#OutProp)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/DiffedAdapter.kt#L157)

Inject prop for [adapter](inject-diffed-adapter.md#org.swiften.redux.android.ui.recyclerview$injectDiffedAdapter(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GState)), org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.OutProp, ((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VH)), org.swiften.redux.ui.IPropMapper((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.LState, org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.OutProp, kotlin.collections.List((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHState)), org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHAction)), ((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHState)))/adapter) with a compatible [VH](inject-diffed-adapter.md#VH) by wrapping it in a [ListAdapter](#). Note that
[adapter](inject-diffed-adapter.md#org.swiften.redux.android.ui.recyclerview$injectDiffedAdapter(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GState)), org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.OutProp, ((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VH)), org.swiften.redux.ui.IPropMapper((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.LState, org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.OutProp, kotlin.collections.List((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHState)), org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHAction)), ((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHState)))/adapter) does not have to be a [ListAdapter](#) - it can be any [RecyclerView.Adapter](#) as long as
it implements [RecyclerView.Adapter.onCreateViewHolder](#).

Since we do not call [IFullPropInjector.inject](../org.swiften.redux.ui/-i-prop-injector/inject.md) directly into [VH](inject-diffed-adapter.md#VH), we cannot use
[IPropMapper.mapAction](../org.swiften.redux.ui/-i-action-mapper/map-action.md) on [VH](inject-diffed-adapter.md#VH) itself. As a result, we must pass down
[ReduxProp.action](../org.swiften.redux.ui/-redux-prop/action.md) from [ReduxListAdapter.reduxProp](-redux-list-adapter/redux-prop.md) into each [VH](inject-diffed-adapter.md#VH) instance. The [VHAction](inject-diffed-adapter.md#VHAction)
should contain actions that take at least one [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) parameter, (e.g. (Int) -&gt; Unit), so that we
can use [RecyclerView.ViewHolder.getLayoutPosition](#) to call them.

Note that this does not support lifecycle handling, so we will need to manually set null via
[RecyclerView.setAdapter](#) to invoke [RecyclerView.Adapter.onDetachedFromRecyclerView](#).

### Parameters

`GState` - The global state type.

`OutProp` - Property as defined by [adapter](inject-diffed-adapter.md#org.swiften.redux.android.ui.recyclerview$injectDiffedAdapter(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GState)), org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.OutProp, ((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VH)), org.swiften.redux.ui.IPropMapper((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.LState, org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.OutProp, kotlin.collections.List((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHState)), org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHAction)), ((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHState)))/adapter)'s parent.

`VH` - The [RecyclerView.ViewHolder](#) instance.

`VHState` - The [VH](inject-diffed-adapter.md#VH) state type. See [ReduxProp.state](../org.swiften.redux.ui/-redux-prop/state.md).

`VHAction` - The [VH](inject-diffed-adapter.md#VH) action type. See [ReduxProp.action](../org.swiften.redux.ui/-redux-prop/action.md).

`outProp` - An [OutProp](inject-diffed-adapter.md#OutProp) instance.

`adapter` - The base [RecyclerView.Adapter](#) instance.

`adapterMapper` - An [IPropMapper](../org.swiften.redux.ui/-i-prop-mapper.md) instance for [ReduxListAdapter](-redux-list-adapter/index.md).

`diffCallback` - A [DiffUtil.ItemCallback](#) instance.

**Receiver**
An [IPropInjector](../org.swiften.redux.ui/-i-prop-injector/index.md) instance.

`fun <GState : `[`LState`](inject-diffed-adapter.md#LState)`, LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp, VH, VHState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, VHAction : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-diffed-adapter.md#GState)`>.injectDiffedAdapter(outProp: `[`OutProp`](inject-diffed-adapter.md#OutProp)`, lifecycleOwner: <ERROR CLASS>, adapter: <ERROR CLASS><`[`VH`](inject-diffed-adapter.md#VH)`>, adapterMapper: `[`IPropMapper`](../org.swiften.redux.ui/-i-prop-mapper.md)`<`[`LState`](inject-diffed-adapter.md#LState)`, `[`OutProp`](inject-diffed-adapter.md#OutProp)`, `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`VHState`](inject-diffed-adapter.md#VHState)`>, `[`VHAction`](inject-diffed-adapter.md#VHAction)`>, diffCallback: <ERROR CLASS><`[`VHState`](inject-diffed-adapter.md#VHState)`>): <ERROR CLASS><`[`VHState`](inject-diffed-adapter.md#VHState)`, `[`VH`](inject-diffed-adapter.md#VH)`> where VH : `[`IPropContainer`](../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`VHState`](inject-diffed-adapter.md#VHState)`, `[`VHAction`](inject-diffed-adapter.md#VHAction)`>, VH : `[`IPropLifecycleOwner`](../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`LState`](inject-diffed-adapter.md#LState)`, `[`OutProp`](inject-diffed-adapter.md#OutProp)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/LifecycleAdapter.kt#L80)

Perform [injectDiffedAdapter](./inject-diffed-adapter.md), but also handle lifecycle with [lifecycleOwner](inject-diffed-adapter.md#org.swiften.redux.android.ui.recyclerview$injectDiffedAdapter(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GState)), org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.OutProp, , ((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VH)), org.swiften.redux.ui.IPropMapper((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.LState, org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.OutProp, kotlin.collections.List((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHState)), org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHAction)), ((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHState)))/lifecycleOwner).

### Parameters

`GState` - The global state type.

`LState` - The local state type that [GState](inject-diffed-adapter.md#GState) must extend from.

`OutProp` - Property as defined by [lifecycleOwner](inject-diffed-adapter.md#org.swiften.redux.android.ui.recyclerview$injectDiffedAdapter(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GState)), org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.OutProp, , ((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VH)), org.swiften.redux.ui.IPropMapper((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.LState, org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.OutProp, kotlin.collections.List((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHState)), org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHAction)), ((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHState)))/lifecycleOwner)'s parent.

`VH` - The [RecyclerView.ViewHolder](#) instance.

`VHState` - The [VH](inject-diffed-adapter.md#VH) state type. See [ReduxProp.state](../org.swiften.redux.ui/-redux-prop/state.md).

`VHAction` - The [VH](inject-diffed-adapter.md#VH) action type. See [ReduxProp.action](../org.swiften.redux.ui/-redux-prop/action.md).

`outProp` - An [OutProp](inject-diffed-adapter.md#OutProp) instance.

`lifecycleOwner` - A [LifecycleOwner](#) instance.

`adapter` - The base [RecyclerView.Adapter](#) instance.

`adapterMapper` - An [IPropMapper](../org.swiften.redux.ui/-i-prop-mapper.md) instance for [ReduxListAdapter](-redux-list-adapter/index.md).

`diffCallback` - A [DiffUtil.ItemCallback](#) instance.

**Receiver**
An [IPropInjector](../org.swiften.redux.ui/-i-prop-injector/index.md) instance.

**Return**
A [ListAdapter](#) instance.

`fun <GState : `[`LState`](inject-diffed-adapter.md#LState)`, LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp, VH, VHState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, VHAction : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-diffed-adapter.md#GState)`>.injectDiffedAdapter(outProp: `[`OutProp`](inject-diffed-adapter.md#OutProp)`, lifecycleOwner: <ERROR CLASS>, adapter: <ERROR CLASS><`[`VH`](inject-diffed-adapter.md#VH)`>, adapterMapper: `[`IPropMapper`](../org.swiften.redux.ui/-i-prop-mapper.md)`<`[`LState`](inject-diffed-adapter.md#LState)`, `[`OutProp`](inject-diffed-adapter.md#OutProp)`, `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`VHState`](inject-diffed-adapter.md#VHState)`>, `[`VHAction`](inject-diffed-adapter.md#VHAction)`>, diffCallback: `[`IDiffItemCallback`](-i-diff-item-callback/index.md)`<`[`VHState`](inject-diffed-adapter.md#VHState)`>): <ERROR CLASS><`[`VHState`](inject-diffed-adapter.md#VHState)`, `[`VH`](inject-diffed-adapter.md#VH)`> where VH : `[`IPropContainer`](../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`VHState`](inject-diffed-adapter.md#VHState)`, `[`VHAction`](inject-diffed-adapter.md#VHAction)`>, VH : `[`IPropLifecycleOwner`](../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`LState`](inject-diffed-adapter.md#LState)`, `[`OutProp`](inject-diffed-adapter.md#OutProp)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/LifecycleAdapter.kt#L124)

Instead of [DiffUtil.ItemCallback](#), use [IDiffItemCallback](-i-diff-item-callback/index.md) to avoid abstract class.

### Parameters

`GState` - The global state type.

`LState` - The local state type that [GState](inject-diffed-adapter.md#GState) must extend from.

`OutProp` - Property as defined by [lifecycleOwner](inject-diffed-adapter.md#org.swiften.redux.android.ui.recyclerview$injectDiffedAdapter(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GState)), org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.OutProp, , ((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VH)), org.swiften.redux.ui.IPropMapper((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.LState, org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.OutProp, kotlin.collections.List((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHState)), org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHAction)), org.swiften.redux.android.ui.recyclerview.IDiffItemCallback((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHState)))/lifecycleOwner)'s parent.

`VH` - The [RecyclerView.ViewHolder](#) instance.

`VHState` - The [VH](inject-diffed-adapter.md#VH) state type. See [ReduxProp.state](../org.swiften.redux.ui/-redux-prop/state.md).

`VHAction` - The [VH](inject-diffed-adapter.md#VH) action type. See [ReduxProp.action](../org.swiften.redux.ui/-redux-prop/action.md).

`outProp` - An [OutProp](inject-diffed-adapter.md#OutProp) instance.

`lifecycleOwner` - A [LifecycleOwner](#) instance.

`adapter` - The base [RecyclerView.Adapter](#) instance.

`adapterMapper` - An [IPropMapper](../org.swiften.redux.ui/-i-prop-mapper.md) instance for [ReduxListAdapter](-redux-list-adapter/index.md).

`diffCallback` - A [IDiffItemCallback](-i-diff-item-callback/index.md) instance.

**Receiver**
An [IPropInjector](../org.swiften.redux.ui/-i-prop-injector/index.md) instance.

**Return**
A [ListAdapter](#) instance.

