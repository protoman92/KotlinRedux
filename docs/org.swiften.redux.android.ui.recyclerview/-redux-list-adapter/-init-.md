[docs](../../index.md) / [org.swiften.redux.android.ui.recyclerview](../index.md) / [ReduxListAdapter](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`ReduxListAdapter(adapter: <ERROR CLASS><`[`VH`](index.md#VH)`>, diffCallback: <ERROR CLASS><`[`VHState`](index.md#VHState)`>)`

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