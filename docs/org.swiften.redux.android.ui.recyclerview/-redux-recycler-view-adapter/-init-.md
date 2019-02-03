[docs](../../index.md) / [org.swiften.redux.android.ui.recyclerview](../index.md) / [ReduxRecyclerViewAdapter](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`ReduxRecyclerViewAdapter()`

Convenience [RecyclerView.Adapter](#) that implements some default methods to make working with
[IPropInjector](../../org.swiften.redux.ui/-i-prop-injector/index.md) easier. Basically, [RecyclerView.Adapter.getItemCount](#) always returns 0
(because it will be delegated to a different calculation.

This class is not required because custom [RecyclerView.Adapter](#) only needs to do the same as
which has been done here to ensure proper integration.

