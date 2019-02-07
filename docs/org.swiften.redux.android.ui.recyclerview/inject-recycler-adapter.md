[docs](../index.md) / [org.swiften.redux.android.ui.recyclerview](index.md) / [injectRecyclerAdapter](./inject-recycler-adapter.md)

# injectRecyclerAdapter

`fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, VH, VHState, VHAction> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-recycler-adapter.md#GState)`, `[`GExt`](inject-recycler-adapter.md#GExt)`>.injectRecyclerAdapter(lifecycleOwner: <ERROR CLASS>, adapter: <ERROR CLASS><`[`VH`](inject-recycler-adapter.md#VH)`>, adapterMapper: `[`IStateMapper`](../org.swiften.redux.ui/-i-state-mapper/index.md)`<`[`GState`](inject-recycler-adapter.md#GState)`, `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>, vhMapper: `[`IPropMapper`](../org.swiften.redux.ui/-i-prop-mapper.md)`<`[`GState`](inject-recycler-adapter.md#GState)`, `[`GExt`](inject-recycler-adapter.md#GExt)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, `[`VHState`](inject-recycler-adapter.md#VHState)`, `[`VHAction`](inject-recycler-adapter.md#VHAction)`>): <ERROR CLASS><`[`VH`](inject-recycler-adapter.md#VH)`> where VH : `[`IPropContainer`](../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`VHState`](inject-recycler-adapter.md#VHState)`, `[`VHAction`](inject-recycler-adapter.md#VHAction)`>, VH : `[`IPropLifecycleOwner`](../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`GState`](inject-recycler-adapter.md#GState)`, `[`GExt`](inject-recycler-adapter.md#GExt)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/LifecycleAdapter.kt#L38)

Perform [injectRecyclerAdapter](./inject-recycler-adapter.md), but also handle lifecycle with [lifecycleOwner](inject-recycler-adapter.md#org.swiften.redux.android.ui.recyclerview$injectRecyclerAdapter(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.GState, org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.GExt)), , ((org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.VH)), org.swiften.redux.ui.IStateMapper((org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.GState, kotlin.Unit, kotlin.Int)), org.swiften.redux.ui.IPropMapper((org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.GState, org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.GExt, kotlin.Int, org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.VHState, org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.VHAction)))/lifecycleOwner).

### Parameters

`GState` - The global state type.

`GExt` - See [IPropInjector.external](../org.swiften.redux.ui/-i-action-dependency/external.md).

`VH` - The [RecyclerView.ViewHolder](#) instance.

`VHState` - The [VH](inject-recycler-adapter.md#VH) state type. See [ReduxProps.state](../org.swiften.redux.ui/-redux-props/state.md).

`VHAction` - The [VH](inject-recycler-adapter.md#VH) action type. See [ReduxProps.action](../org.swiften.redux.ui/-redux-props/action.md).

`lifecycleOwner` - A [LifecycleOwner](#) instance.

`adapter` - The base [RecyclerView.Adapter](#) instance.

`adapterMapper` - An [IStateMapper](../org.swiften.redux.ui/-i-state-mapper/index.md) instance that calculates item count for
[RecyclerView.Adapter.getItemCount](#).

`vhMapper` - An [IPropMapper](../org.swiften.redux.ui/-i-prop-mapper.md) instance for [VH](inject-recycler-adapter.md#VH).

**Receiver**
An [IPropInjector](../org.swiften.redux.ui/-i-prop-injector/index.md) instance.

**Return**
A [RecyclerView.Adapter](#) instance.

`fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, Adapter : `[`IStateMapper`](../org.swiften.redux.ui/-i-state-mapper/index.md)`<`[`GState`](inject-recycler-adapter.md#GState)`, `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>, VH, VHState, VHAction> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-recycler-adapter.md#GState)`, `[`GExt`](inject-recycler-adapter.md#GExt)`>.injectRecyclerAdapter(lifecycleOwner: <ERROR CLASS>, adapter: `[`Adapter`](inject-recycler-adapter.md#Adapter)`, vhMapper: `[`IPropMapper`](../org.swiften.redux.ui/-i-prop-mapper.md)`<`[`GState`](inject-recycler-adapter.md#GState)`, `[`GExt`](inject-recycler-adapter.md#GExt)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, `[`VHState`](inject-recycler-adapter.md#VHState)`, `[`VHAction`](inject-recycler-adapter.md#VHAction)`>): <ERROR CLASS><`[`VH`](inject-recycler-adapter.md#VH)`> where VH : `[`IPropContainer`](../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`VHState`](inject-recycler-adapter.md#VHState)`, `[`VHAction`](inject-recycler-adapter.md#VHAction)`>, VH : `[`IPropLifecycleOwner`](../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`GState`](inject-recycler-adapter.md#GState)`, `[`GExt`](inject-recycler-adapter.md#GExt)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/LifecycleAdapter.kt#L73)

Same as [IPropInjector.injectRecyclerAdapter](./inject-recycler-adapter.md) but [Adapter](inject-recycler-adapter.md#Adapter) also implements [IStateMapper](../org.swiften.redux.ui/-i-state-mapper/index.md).

### Parameters

`GState` - The global state type.

`GExt` - See [IPropInjector.external](../org.swiften.redux.ui/-i-action-dependency/external.md).

`Adapter` - The [RecyclerView.Adapter](#) type.

`VH` - The [RecyclerView.ViewHolder](#) instance.

`VHState` - The [VH](inject-recycler-adapter.md#VH) state type. See [ReduxProps.state](../org.swiften.redux.ui/-redux-props/state.md).

`VHAction` - The [VH](inject-recycler-adapter.md#VH) action type. See [ReduxProps.action](../org.swiften.redux.ui/-redux-props/action.md).

`lifecycleOwner` - A [LifecycleOwner](#) instance.

`adapter` - The base [Adapter](inject-recycler-adapter.md#Adapter) instance.

`vhMapper` - An [IPropMapper](../org.swiften.redux.ui/-i-prop-mapper.md) instance for [VH](inject-recycler-adapter.md#VH).

**Receiver**
An [IPropInjector](../org.swiften.redux.ui/-i-prop-injector/index.md) instance.

**Return**
A [RecyclerView.Adapter](#) instance.

`fun <GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, VH, VHState, VHAction> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-recycler-adapter.md#GState)`, `[`GExt`](inject-recycler-adapter.md#GExt)`>.injectRecyclerAdapter(adapter: <ERROR CLASS><`[`VH`](inject-recycler-adapter.md#VH)`>, adapterMapper: `[`IStateMapper`](../org.swiften.redux.ui/-i-state-mapper/index.md)`<`[`GState`](inject-recycler-adapter.md#GState)`, `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>, vhMapper: `[`IPropMapper`](../org.swiften.redux.ui/-i-prop-mapper.md)`<`[`GState`](inject-recycler-adapter.md#GState)`, `[`GExt`](inject-recycler-adapter.md#GExt)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, `[`VHState`](inject-recycler-adapter.md#VHState)`, `[`VHAction`](inject-recycler-adapter.md#VHAction)`>): `[`DelegateRecyclerAdapter`](-delegate-recycler-adapter/index.md)`<`[`GState`](inject-recycler-adapter.md#GState)`, `[`GExt`](inject-recycler-adapter.md#GExt)`, `[`VH`](inject-recycler-adapter.md#VH)`, `[`VHState`](inject-recycler-adapter.md#VHState)`, `[`VHAction`](inject-recycler-adapter.md#VHAction)`> where VH : `[`IPropContainer`](../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`VHState`](inject-recycler-adapter.md#VHState)`, `[`VHAction`](inject-recycler-adapter.md#VHAction)`>, VH : `[`IPropLifecycleOwner`](../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`GState`](inject-recycler-adapter.md#GState)`, `[`GExt`](inject-recycler-adapter.md#GExt)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/RecyclerAdapter.kt#L124)

Inject props for a [RecyclerView.Adapter](#) with a compatible [VH](inject-recycler-adapter.md#VH). Note that this does not
support lifecycle handling, so we will need to manually set null via [RecyclerView.setAdapter](#)
in order to invoke [RecyclerView.Adapter.onViewRecycled](#), e.g. on orientation change.

### Parameters

`GState` - The global state type.

`GExt` - See [IPropInjector.external](../org.swiften.redux.ui/-i-action-dependency/external.md).

`VH` - The [RecyclerView.ViewHolder](#) instance.

`VHState` - The [VH](inject-recycler-adapter.md#VH) state type. See [ReduxProps.state](../org.swiften.redux.ui/-redux-props/state.md).

`VHAction` - The [VH](inject-recycler-adapter.md#VH) action type. See [ReduxProps.action](../org.swiften.redux.ui/-redux-props/action.md).

`adapter` - The base [RecyclerView.Adapter](#) instance.

`adapterMapper` - An [IStateMapper](../org.swiften.redux.ui/-i-state-mapper/index.md) instance that calculates item count for
[RecyclerView.Adapter.getItemCount](#).

`vhMapper` - An [IPropMapper](../org.swiften.redux.ui/-i-prop-mapper.md) instance for [VH](inject-recycler-adapter.md#VH).

**Return**
An [IPropInjector](../org.swiften.redux.ui/-i-prop-injector/index.md) instance.

**Return**
A [DelegateRecyclerAdapter](-delegate-recycler-adapter/index.md) instance.

