[docs](../../index.md) / [org.swiften.redux.android.ui.recyclerview](../index.md) / [DelegateRecyclerAdapter](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`DelegateRecyclerAdapter(adapter: <ERROR CLASS><`[`VH`](index.md#VH)`>)`

[RecyclerView.Adapter](#) that delegates method calls to another [RecyclerView.Adapter](#).

### Parameters

`GState` - The global state type.

`LState` - The local state type that [GState](index.md#GState) must extend from.

`OutProp` - Property as defined by [adapter](adapter.md)'s parent.

`VH` - The [RecyclerView.ViewHolder](#) instance.

`VHState` - The [VH](index.md#VH) state type. See [ReduxProp.state](../../org.swiften.redux.ui/-redux-prop/state.md).

`VHAction` - The [VH](index.md#VH) action type. See [ReduxProp.action](../../org.swiften.redux.ui/-redux-prop/action.md).

`adapter` - The base [RecyclerView.Adapter](#) instance.