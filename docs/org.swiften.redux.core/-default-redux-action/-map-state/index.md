[docs](../../../index.md) / [org.swiften.redux.core](../../index.md) / [DefaultReduxAction](../index.md) / [MapState](./index.md)

# MapState

`class MapState<GState> : `[`DefaultReduxAction`](../index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Preset.kt#L18)

Replace the current [GState](index.md#GState) with [fn](fn.md)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `MapState(fn: (`[`GState`](index.md#GState)`) -> `[`GState`](index.md#GState)`)`<br>Replace the current [GState](index.md#GState) with [fn](fn.md) |

### Properties

| Name | Summary |
|---|---|
| [fn](fn.md) | `val fn: (`[`GState`](index.md#GState)`) -> `[`GState`](index.md#GState) |
