[docs](../index.md) / [org.swiften.redux.android.ui.recyclerview](index.md) / [injectRecyclerAdapter](./inject-recycler-adapter.md)

# injectRecyclerAdapter

`fun <GState : `[`LState`](inject-recycler-adapter.md#LState)`, LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp, VH, VHState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, VHAction : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-recycler-adapter.md#GState)`>.injectRecyclerAdapter(outProp: `[`OutProp`](inject-recycler-adapter.md#OutProp)`, adapter: <ERROR CLASS><`[`VH`](inject-recycler-adapter.md#VH)`>, adapterMapper: `[`IStateMapper`](../org.swiften.redux.ui/-i-state-mapper/index.md)`<`[`LState`](inject-recycler-adapter.md#LState)`, `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>, vhMapper: `[`IPropMapper`](../org.swiften.redux.ui/-i-prop-mapper.md)`<`[`LState`](inject-recycler-adapter.md#LState)`, `[`PositionProp`](-position-prop/index.md)`<`[`OutProp`](inject-recycler-adapter.md#OutProp)`>, `[`VHState`](inject-recycler-adapter.md#VHState)`, `[`VHAction`](inject-recycler-adapter.md#VHAction)`>): `[`DelegateRecyclerAdapter`](-delegate-recycler-adapter/index.md)`<`[`GState`](inject-recycler-adapter.md#GState)`, `[`LState`](inject-recycler-adapter.md#LState)`, `[`OutProp`](inject-recycler-adapter.md#OutProp)`, `[`VH`](inject-recycler-adapter.md#VH)`, `[`VHState`](inject-recycler-adapter.md#VHState)`, `[`VHAction`](inject-recycler-adapter.md#VHAction)`> where VH : `[`IUniqueIDProvider`](../org.swiften.redux.core/-i-unique-i-d-provider/index.md)`, VH : `[`IPropContainer`](../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`VHState`](inject-recycler-adapter.md#VHState)`, `[`VHAction`](inject-recycler-adapter.md#VHAction)`>, VH : `[`IPropLifecycleOwner`](../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`LState`](inject-recycler-adapter.md#LState)`, `[`PositionProp`](-position-prop/index.md)`<`[`OutProp`](inject-recycler-adapter.md#OutProp)`>>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android\android-recyclerview\src\main\java/org/swiften/redux/android/ui/recyclerview/RecyclerAdapter.kt#L149)

Inject prop for a [RecyclerView.Adapter](#) with a compatible [VH](inject-recycler-adapter.md#VH). Note that this does not support
lifecycle handling, so we will need to manually set null via [RecyclerView.setAdapter](#) in order
to invoke [RecyclerView.Adapter.onViewRecycled](#), e.g. on orientation change.

Note that [VH](inject-recycler-adapter.md#VH)'s outer prop store both [OutProp](inject-recycler-adapter.md#OutProp) and [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) corresponding to its layout position.

### Parameters

`GState` - The global state type.

`LState` - The local state type that [GState](inject-recycler-adapter.md#GState) must extend from.

`OutProp` - Property as defined by [adapter](inject-recycler-adapter.md#org.swiften.redux.android.ui.recyclerview$injectRecyclerAdapter(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.GState)), org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.OutProp, ((org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.VH)), org.swiften.redux.ui.IStateMapper((org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.LState, kotlin.Unit, kotlin.Int)), org.swiften.redux.ui.IPropMapper((org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.LState, org.swiften.redux.android.ui.recyclerview.PositionProp((org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.OutProp)), org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.VHState, org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.VHAction)))/adapter)'s parent.

`VH` - The [RecyclerView.ViewHolder](#) instance.

`VHState` - The [VH](inject-recycler-adapter.md#VH) state type. See [ReduxProp.state](../org.swiften.redux.ui/-redux-prop/state.md).

`VHAction` - The [VH](inject-recycler-adapter.md#VH) action type. See [ReduxProp.action](../org.swiften.redux.ui/-redux-prop/action.md).

`outProp` - An [OutProp](inject-recycler-adapter.md#OutProp) instance.

`adapter` - The base [RecyclerView.Adapter](#) instance.

`adapterMapper` - An [IStateMapper](../org.swiften.redux.ui/-i-state-mapper/index.md) instance that calculates item count for
[RecyclerView.Adapter.getItemCount](#).

`vhMapper` - An [IPropMapper](../org.swiften.redux.ui/-i-prop-mapper.md) instance for [VH](inject-recycler-adapter.md#VH).

**Return**
An [IPropInjector](../org.swiften.redux.ui/-i-prop-injector/index.md) instance.

**Return**
A [DelegateRecyclerAdapter](-delegate-recycler-adapter/index.md) instance.

`fun <GState : `[`LState`](inject-recycler-adapter.md#LState)`, LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, OutProp, VH, VHState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, VHAction : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`IPropInjector`](../org.swiften.redux.ui/-i-prop-injector/index.md)`<`[`GState`](inject-recycler-adapter.md#GState)`>.injectRecyclerAdapter(outProp: `[`OutProp`](inject-recycler-adapter.md#OutProp)`, lifecycleOwner: <ERROR CLASS>, adapter: <ERROR CLASS><`[`VH`](inject-recycler-adapter.md#VH)`>, adapterMapper: `[`IStateMapper`](../org.swiften.redux.ui/-i-state-mapper/index.md)`<`[`LState`](inject-recycler-adapter.md#LState)`, `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>, vhMapper: `[`IPropMapper`](../org.swiften.redux.ui/-i-prop-mapper.md)`<`[`LState`](inject-recycler-adapter.md#LState)`, `[`PositionProp`](-position-prop/index.md)`<`[`OutProp`](inject-recycler-adapter.md#OutProp)`>, `[`VHState`](inject-recycler-adapter.md#VHState)`, `[`VHAction`](inject-recycler-adapter.md#VHAction)`>): <ERROR CLASS><`[`VH`](inject-recycler-adapter.md#VH)`> where VH : `[`IUniqueIDProvider`](../org.swiften.redux.core/-i-unique-i-d-provider/index.md)`, VH : `[`IPropContainer`](../org.swiften.redux.ui/-i-prop-container/index.md)`<`[`VHState`](inject-recycler-adapter.md#VHState)`, `[`VHAction`](inject-recycler-adapter.md#VHAction)`>, VH : `[`IPropLifecycleOwner`](../org.swiften.redux.ui/-i-prop-lifecycle-owner/index.md)`<`[`LState`](inject-recycler-adapter.md#LState)`, `[`PositionProp`](-position-prop/index.md)`<`[`OutProp`](inject-recycler-adapter.md#OutProp)`>>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android\android-recyclerview\src\main\java/org/swiften/redux/android/ui/recyclerview/RecyclerAdapter.kt#L204)

Perform [injectRecyclerAdapter](./inject-recycler-adapter.md), but also handle lifecycle with [lifecycleOwner](inject-recycler-adapter.md#org.swiften.redux.android.ui.recyclerview$injectRecyclerAdapter(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.GState)), org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.OutProp, , ((org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.VH)), org.swiften.redux.ui.IStateMapper((org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.LState, kotlin.Unit, kotlin.Int)), org.swiften.redux.ui.IPropMapper((org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.LState, org.swiften.redux.android.ui.recyclerview.PositionProp((org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.OutProp)), org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.VHState, org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.VHAction)))/lifecycleOwner).

### Parameters

`GState` - The global state type.

`LState` - The local state type that [GState](inject-recycler-adapter.md#GState) must extend from.

`OutProp` - Property as defined by [lifecycleOwner](inject-recycler-adapter.md#org.swiften.redux.android.ui.recyclerview$injectRecyclerAdapter(org.swiften.redux.ui.IPropInjector((org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.GState)), org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.OutProp, , ((org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.VH)), org.swiften.redux.ui.IStateMapper((org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.LState, kotlin.Unit, kotlin.Int)), org.swiften.redux.ui.IPropMapper((org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.LState, org.swiften.redux.android.ui.recyclerview.PositionProp((org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.OutProp)), org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.VHState, org.swiften.redux.android.ui.recyclerview.injectRecyclerAdapter.VHAction)))/lifecycleOwner)'s parent.

`VH` - The [RecyclerView.ViewHolder](#) instance.

`VHState` - The [VH](inject-recycler-adapter.md#VH) state type. See [ReduxProp.state](../org.swiften.redux.ui/-redux-prop/state.md).

`VHAction` - The [VH](inject-recycler-adapter.md#VH) action type. See [ReduxProp.action](../org.swiften.redux.ui/-redux-prop/action.md).

`lifecycleOwner` - A [LifecycleOwner](#) instance.

`adapter` - The base [RecyclerView.Adapter](#) instance.

`outProp` - An [OutProp](inject-recycler-adapter.md#OutProp) instance.

`adapterMapper` - An [IStateMapper](../org.swiften.redux.ui/-i-state-mapper/index.md) instance that calculates item count for
[RecyclerView.Adapter.getItemCount](#).

`vhMapper` - An [IPropMapper](../org.swiften.redux.ui/-i-prop-mapper.md) instance for [VH](inject-recycler-adapter.md#VH).

**Receiver**
An [IPropInjector](../org.swiften.redux.ui/-i-prop-injector/index.md) instance.

**Return**
A [RecyclerView.Adapter](#) instance.

