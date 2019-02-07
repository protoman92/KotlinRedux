[docs](../index.md) / [org.swiften.redux.android.ui.recyclerview](index.md) / [injectDiffedAdapter](./inject-diffed-adapter.md)

# injectDiffedAdapter

`fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, VH, VHS, VHA> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-diffed-adapter.md#GState)`, `[`GExt`](inject-diffed-adapter.md#GExt)`>.injectDiffedAdapter(adapter: <ERROR CLASS><`[`VH`](inject-diffed-adapter.md#VH)`>, adapterMapper: `[`IPropMapper`](../org.swiften.redux.ui/-i-prop-mapper.md)`<`[`GState`](inject-diffed-adapter.md#GState)`, `[`GExt`](inject-diffed-adapter.md#GExt)`, `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`, `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`VHS`](inject-diffed-adapter.md#VHS)`>, `[`VHA`](inject-diffed-adapter.md#VHA)`>, diffCallback: <ERROR CLASS><`[`VHS`](inject-diffed-adapter.md#VHS)`>): `[`ReduxListAdapter`](-redux-list-adapter/index.md)`<`[`GState`](inject-diffed-adapter.md#GState)`, `[`GExt`](inject-diffed-adapter.md#GExt)`, `[`VH`](inject-diffed-adapter.md#VH)`, `[`VHS`](inject-diffed-adapter.md#VHS)`, `[`VHA`](inject-diffed-adapter.md#VHA)`> where VH : `[`IPropContainer`](../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`VHS`](inject-diffed-adapter.md#VHS)`, `[`VHA`](inject-diffed-adapter.md#VHA)`>, VH : `[`IPropLifecycleOwner`](../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`GState`](inject-diffed-adapter.md#GState)`, `[`GExt`](inject-diffed-adapter.md#GExt)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/DiffedAdapter.kt#L148)

Inject props for [adapter](inject-diffed-adapter.md#org.swiften.redux.android.ui.recyclerview$injectDiffedAdapter(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GState, org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GExt)), ((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VH)), org.swiften.redux.ui.IPropMapper((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GState, org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GExt, kotlin.Unit, kotlin.collections.List((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHS)), org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHA)), ((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHS)))/adapter) with a compatible [VH](inject-diffed-adapter.md#VH) by wrapping it in a [ListAdapter](#). Note that
[adapter](inject-diffed-adapter.md#org.swiften.redux.android.ui.recyclerview$injectDiffedAdapter(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GState, org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GExt)), ((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VH)), org.swiften.redux.ui.IPropMapper((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GState, org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GExt, kotlin.Unit, kotlin.collections.List((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHS)), org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHA)), ((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHS)))/adapter) does not have to be a [ListAdapter](#) - it can be any [RecyclerView.Adapter](#) as long as
it implements [RecyclerView.Adapter.onCreateViewHolder](#).

Since we do not call [IPropInjector.inject](../org.swiften.redux.ui/-i-prop-injector/inject.md) directly into [VH](inject-diffed-adapter.md#VH), we cannot use
[IPropMapper.mapAction](../org.swiften.redux.ui/-i-action-mapper/map-action.md) on [VH](inject-diffed-adapter.md#VH) itself. As a result, we must pass down
[ReduxProps.action](../org.swiften.redux.ui/-redux-props/action.md) from [ReduxListAdapter.reduxProps](-redux-list-adapter/redux-props.md) into each [VH](inject-diffed-adapter.md#VH) instance. The [VHA](inject-diffed-adapter.md#VHA) should
contain actions that take at least one [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) parameter, (e.g. (Int) -&gt; Unit), so that we can use
[RecyclerView.ViewHolder.getLayoutPosition](#) to call them.

Note that this does not support lifecycle handling, so we will need to manually set null via
[RecyclerView.setAdapter](#) to invoke [RecyclerView.Adapter.onDetachedFromRecyclerView](#).

### Parameters

`GState` - The global state type.

`GExt` - See [IPropInjector.external](../org.swiften.redux.ui/-i-action-dependency/external.md).

`VH` - The [RecyclerView.ViewHolder](#) instance.

`VHS` - The [VH](inject-diffed-adapter.md#VH) state type. See [ReduxProps.state](../org.swiften.redux.ui/-redux-props/state.md).

`VHA` - The [VH](inject-diffed-adapter.md#VH) action type. See [ReduxProps.action](../org.swiften.redux.ui/-redux-props/action.md).

`adapter` - The base [RecyclerView.Adapter](#) instance.

`adapterMapper` - An [IPropMapper](../org.swiften.redux.ui/-i-prop-mapper.md) instance for [ReduxListAdapter](-redux-list-adapter/index.md).

`diffCallback` - A [DiffUtil.ItemCallback](#) instance.`fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, VH, VHState, VHAction> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-diffed-adapter.md#GState)`, `[`GExt`](inject-diffed-adapter.md#GExt)`>.injectDiffedAdapter(lifecycleOwner: <ERROR CLASS>, adapter: <ERROR CLASS><`[`VH`](inject-diffed-adapter.md#VH)`>, adapterMapper: `[`IPropMapper`](../org.swiften.redux.ui/-i-prop-mapper.md)`<`[`GState`](inject-diffed-adapter.md#GState)`, `[`GExt`](inject-diffed-adapter.md#GExt)`, `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`, `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`VHState`](inject-diffed-adapter.md#VHState)`>, `[`VHAction`](inject-diffed-adapter.md#VHAction)`>, diffCallback: <ERROR CLASS><`[`VHState`](inject-diffed-adapter.md#VHState)`>): <ERROR CLASS><`[`VHState`](inject-diffed-adapter.md#VHState)`, `[`VH`](inject-diffed-adapter.md#VH)`> where VH : `[`IPropContainer`](../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`VHState`](inject-diffed-adapter.md#VHState)`, `[`VHAction`](inject-diffed-adapter.md#VHAction)`>, VH : `[`IPropLifecycleOwner`](../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`GState`](inject-diffed-adapter.md#GState)`, `[`GExt`](inject-diffed-adapter.md#GExt)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/LifecycleAdapter.kt#L102)

Perform [injectDiffedAdapter](./inject-diffed-adapter.md), but also handle lifecycle with [lifecycleOwner](inject-diffed-adapter.md#org.swiften.redux.android.ui.recyclerview$injectDiffedAdapter(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GState, org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GExt)), , ((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VH)), org.swiften.redux.ui.IPropMapper((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GState, org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GExt, kotlin.Unit, kotlin.collections.List((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHState)), org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHAction)), ((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VHState)))/lifecycleOwner).

### Parameters

`GState` - The global state type.

`GExt` - See [IPropInjector.external](../org.swiften.redux.ui/-i-action-dependency/external.md).

`VH` - The [RecyclerView.ViewHolder](#) instance.

`VHState` - The [VH](inject-diffed-adapter.md#VH) state type. See [ReduxProps.state](../org.swiften.redux.ui/-redux-props/state.md).

`VHAction` - The [VH](inject-diffed-adapter.md#VH) action type. See [ReduxProps.action](../org.swiften.redux.ui/-redux-props/action.md).

`lifecycleOwner` - A [LifecycleOwner](#) instance.

`adapter` - The base [RecyclerView.Adapter](#) instance.

`adapterMapper` - An [IPropMapper](../org.swiften.redux.ui/-i-prop-mapper.md) instance for [ReduxListAdapter](-redux-list-adapter/index.md).

`diffCallback` - A [DiffUtil.ItemCallback](#) instance.

**Receiver**
An [IPropInjector](../org.swiften.redux.ui/-i-prop-injector/index.md) instance.

**Return**
A [ListAdapter](#) instance.

`fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, VH, VHState, VHAction> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-diffed-adapter.md#GState)`, `[`GExt`](inject-diffed-adapter.md#GExt)`>.injectDiffedAdapter(lifecycleOwner: <ERROR CLASS>, adapter: <ERROR CLASS><`[`VH`](inject-diffed-adapter.md#VH)`>, adapterMapper: `[`IPropMapper`](../org.swiften.redux.ui/-i-prop-mapper.md)`<`[`GState`](inject-diffed-adapter.md#GState)`, `[`GExt`](inject-diffed-adapter.md#GExt)`, `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`, `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`VHState`](inject-diffed-adapter.md#VHState)`>, `[`VHAction`](inject-diffed-adapter.md#VHAction)`>, diffCallback: `[`IDiffItemCallback`](-i-diff-item-callback/index.md)`<`[`VHState`](inject-diffed-adapter.md#VHState)`>): <ERROR CLASS><`[`VHState`](inject-diffed-adapter.md#VHState)`, `[`VH`](inject-diffed-adapter.md#VH)`> where VH : `[`IPropContainer`](../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`VHState`](inject-diffed-adapter.md#VHState)`, `[`VHAction`](inject-diffed-adapter.md#VHAction)`>, VH : `[`IPropLifecycleOwner`](../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`GState`](inject-diffed-adapter.md#GState)`, `[`GExt`](inject-diffed-adapter.md#GExt)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/LifecycleAdapter.kt#L141)

Instead of [DiffUtil.ItemCallback](#), use [IDiffItemCallback](-i-diff-item-callback/index.md) to avoid abstract class.

### Parameters

`GState` - The global state type.

`GExt` - See [IPropInjector.external](../org.swiften.redux.ui/-i-action-dependency/external.md).

`VH` - The [RecyclerView.ViewHolder](#) instance.

`VHState` - The [VH](inject-diffed-adapter.md#VH) state type. See [ReduxProps.state](../org.swiften.redux.ui/-redux-props/state.md).

`VHAction` - The [VH](inject-diffed-adapter.md#VH) action type. See [ReduxProps.action](../org.swiften.redux.ui/-redux-props/action.md).

`lifecycleOwner` - A [LifecycleOwner](#) instance.

`adapter` - The base [RecyclerView.Adapter](#) instance.

`adapterMapper` - An [IPropMapper](../org.swiften.redux.ui/-i-prop-mapper.md) instance for [ReduxListAdapter](-redux-list-adapter/index.md).

`diffCallback` - A [IDiffItemCallback](-i-diff-item-callback/index.md) instance.

**Receiver**
An [IPropInjector](../org.swiften.redux.ui/-i-prop-injector/index.md) instance.

**Return**
A [ListAdapter](#) instance.

`fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, Mapper, VH, VHState, VHAction> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-diffed-adapter.md#GState)`, `[`GExt`](inject-diffed-adapter.md#GExt)`>.injectDiffedAdapter(lifecycleOwner: <ERROR CLASS>, adapter: <ERROR CLASS><`[`VH`](inject-diffed-adapter.md#VH)`>, mapper: `[`Mapper`](inject-diffed-adapter.md#Mapper)`): <ERROR CLASS><`[`VHState`](inject-diffed-adapter.md#VHState)`, `[`VH`](inject-diffed-adapter.md#VH)`> where Mapper : `[`IPropMapper`](../org.swiften.redux.ui/-i-prop-mapper.md)`<`[`GState`](inject-diffed-adapter.md#GState)`, `[`GExt`](inject-diffed-adapter.md#GExt)`, `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`, `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`VHState`](inject-diffed-adapter.md#VHState)`>, `[`VHAction`](inject-diffed-adapter.md#VHAction)`>, Mapper : `[`IDiffItemCallback`](-i-diff-item-callback/index.md)`<`[`VHState`](inject-diffed-adapter.md#VHState)`>, VH : `[`IPropContainer`](../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`VHState`](inject-diffed-adapter.md#VHState)`, `[`VHAction`](inject-diffed-adapter.md#VHAction)`>, VH : `[`IPropLifecycleOwner`](../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`GState`](inject-diffed-adapter.md#GState)`, `[`GExt`](inject-diffed-adapter.md#GExt)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/LifecycleAdapter.kt#L179)

Convenience [injectDiffedAdapter](./inject-diffed-adapter.md) for when [mapper](inject-diffed-adapter.md#org.swiften.redux.android.ui.recyclerview$injectDiffedAdapter(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GState, org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GExt)), , ((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.VH)), org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.Mapper)/mapper) implements both [IPropMapper](../org.swiften.redux.ui/-i-prop-mapper.md) and
[DiffUtil.ItemCallback](#).

### Parameters

`GState` - The global state type.

`GExt` - See [IPropInjector.external](../org.swiften.redux.ui/-i-action-dependency/external.md).

`VH` - The [RecyclerView.ViewHolder](#) instance.

`VHState` - The [VH](inject-diffed-adapter.md#VH) state type. See [ReduxProps.state](../org.swiften.redux.ui/-redux-props/state.md).

`VHAction` - The [VH](inject-diffed-adapter.md#VH) action type. See [ReduxProps.action](../org.swiften.redux.ui/-redux-props/action.md).

`lifecycleOwner` - A [LifecycleOwner](#) instance.

`adapter` - The base [RecyclerView.Adapter](#) instance.

`mapper` - An [IPropMapper](../org.swiften.redux.ui/-i-prop-mapper.md) instance for [ReduxListAdapter](-redux-list-adapter/index.md) that also implements
[IDiffItemCallback](-i-diff-item-callback/index.md).

**Receiver**
An [IPropInjector](../org.swiften.redux.ui/-i-prop-injector/index.md) instance.

**Return**
A [ListAdapter](#) instance.

`fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, Adapter, VH, VHState, VHAction> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-diffed-adapter.md#GState)`, `[`GExt`](inject-diffed-adapter.md#GExt)`>.injectDiffedAdapter(lifecycleOwner: <ERROR CLASS>, adapter: `[`Adapter`](inject-diffed-adapter.md#Adapter)`): <ERROR CLASS><`[`VHState`](inject-diffed-adapter.md#VHState)`, `[`VH`](inject-diffed-adapter.md#VH)`> where Adapter : `[`IPropMapper`](../org.swiften.redux.ui/-i-prop-mapper.md)`<`[`GState`](inject-diffed-adapter.md#GState)`, `[`GExt`](inject-diffed-adapter.md#GExt)`, `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`, `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`VHState`](inject-diffed-adapter.md#VHState)`>, `[`VHAction`](inject-diffed-adapter.md#VHAction)`>, Adapter : `[`IDiffItemCallback`](-i-diff-item-callback/index.md)`<`[`VHState`](inject-diffed-adapter.md#VHState)`>, VH : `[`IPropContainer`](../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`VHState`](inject-diffed-adapter.md#VHState)`, `[`VHAction`](inject-diffed-adapter.md#VHAction)`>, VH : `[`IPropLifecycleOwner`](../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`GState`](inject-diffed-adapter.md#GState)`, `[`GExt`](inject-diffed-adapter.md#GExt)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/LifecycleAdapter.kt#L209)

Convenience [injectDiffedAdapter](./inject-diffed-adapter.md) for when [adapter](inject-diffed-adapter.md#org.swiften.redux.android.ui.recyclerview$injectDiffedAdapter(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GState, org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.GExt)), , org.swiften.redux.android.ui.recyclerview.injectDiffedAdapter.Adapter)/adapter) implements both [RecyclerView.Adapter](#),
[IPropMapper](../org.swiften.redux.ui/-i-prop-mapper.md) and [DiffUtil.ItemCallback](#).

### Parameters

`GState` - The global state type.

`GExt` - See [IPropInjector.external](../org.swiften.redux.ui/-i-action-dependency/external.md).

`Adapter` - The base [RecyclerView.Adapter](#) type.

`VH` - The [RecyclerView.ViewHolder](#) instance.

`VHState` - The [VH](inject-diffed-adapter.md#VH) state type. See [ReduxProps.state](../org.swiften.redux.ui/-redux-props/state.md).

`VHAction` - The [VH](inject-diffed-adapter.md#VH) action type. See [ReduxProps.action](../org.swiften.redux.ui/-redux-props/action.md).

`lifecycleOwner` - A [LifecycleOwner](#) instance.

`adapter` - The base [Adapter](inject-diffed-adapter.md#Adapter) instance that also implements [IPropMapper](../org.swiften.redux.ui/-i-prop-mapper.md) and
[IDiffItemCallback](-i-diff-item-callback/index.md).

**Receiver**
An [IPropInjector](../org.swiften.redux.ui/-i-prop-injector/index.md) instance.

**Return**
A [ListAdapter](#) instance.

