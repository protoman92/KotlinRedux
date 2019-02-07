[docs](../../index.md) / [org.swiften.redux.android.ui.recyclerview](../index.md) / [ReduxListAdapter](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`ReduxListAdapter(adapter: <ERROR CLASS><`[`VH`](index.md#VH)`>, diffCallback: <ERROR CLASS><`[`VHS`](index.md#VHS)`>)`

Custom Redux-compatible [ListAdapter](#) implementation. This [ListAdapter](#) can receive [ReduxProps](../../org.swiften.redux.ui/-redux-props/index.md)
in order to call [ListAdapter.submitList](#).

### Parameters

`GState` - The global state type.

`GExt` - See [IPropInjector.external](../../org.swiften.redux.ui/-i-action-dependency/external.md).

`VH` - The [RecyclerView.ViewHolder](#) instance.

`VHS` - The [VH](index.md#VH) state type. See [ReduxProps.state](../../org.swiften.redux.ui/-redux-props/state.md).

`VHA` - The [VH](index.md#VH) action type. See [ReduxProps.action](../../org.swiften.redux.ui/-redux-props/action.md).

`adapter` - The base [RecyclerView.Adapter](#) instance.

`diffCallback` - A [DiffUtil.ItemCallback](#) instance.