[docs](../../index.md) / [org.swiften.redux.android.ui.recyclerview](../index.md) / [ReduxRecyclerViewAdapter](./index.md)

# ReduxRecyclerViewAdapter

`abstract class ReduxRecyclerViewAdapter<VH>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/RecyclerAdapter.kt#L29)

Convenience [RecyclerView.Adapter](#) that implements some default methods to make working with
[IPropInjector](../../org.swiften.redux.ui/-i-prop-injector/index.md) easier. Basically, [RecyclerView.Adapter.getItemCount](#) always returns 0
(because it will be delegated to a different calculation.

This class is not required because custom [RecyclerView.Adapter](#) only needs to do the same as
which has been done here to ensure proper integration.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ReduxRecyclerViewAdapter()`<br>Convenience [RecyclerView.Adapter](#) that implements some default methods to make working with [IPropInjector](../../org.swiften.redux.ui/-i-prop-injector/index.md) easier. Basically, [RecyclerView.Adapter.getItemCount](#) always returns 0 (because it will be delegated to a different calculation. |

### Functions

| Name | Summary |
|---|---|
| [getItemCount](get-item-count.md) | `fun getItemCount(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [onBindViewHolder](on-bind-view-holder.md) | `open fun onBindViewHolder(holder: `[`VH`](index.md#VH)`, position: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
