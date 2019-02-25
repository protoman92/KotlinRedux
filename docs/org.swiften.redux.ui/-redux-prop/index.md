[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [ReduxProp](./index.md)

# ReduxProp

`data class ReduxProp<State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, Action : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IVariableProp`](../-i-variable-prop/index.md)`<`[`State`](index.md#State)`, `[`Action`](index.md#Action)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Props.kt#L38)

Container for [state](state.md) and [action](action.md).

### Parameters

`State` - See [IVariableProp.state](../-i-variable-prop/state.md).

`Action` - See [IVariableProp.action](../-i-variable-prop/action.md).

`firstTime` - Whether this [ReduxProp](./index.md) is the first one to be injected.

`state` - A [State](index.md#State) instance.

`action` - An [Action](index.md#Action) instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ReduxProp(firstTime: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, state: `[`State`](index.md#State)`, action: `[`Action`](index.md#Action)`)`<br>Container for [state](state.md) and [action](action.md). |

### Properties

| Name | Summary |
|---|---|
| [action](action.md) | `val action: `[`Action`](index.md#Action)<br>An [Action](index.md#Action) instance. |
| [firstTime](first-time.md) | `val firstTime: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Whether this [ReduxProp](./index.md) is the first one to be injected. |
| [state](state.md) | `val state: `[`State`](index.md#State)<br>A [State](index.md#State) instance. |
