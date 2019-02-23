[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IStateMapper](./index.md)

# IStateMapper

`interface IStateMapper<in LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, in OutProp, out State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Mapper.kt#L17)

Maps [LState](index.md#LState) to [State](index.md#State) for a [IPropContainer](../-i-prop-container/index.md).

### Parameters

`LState` - The local state type that the global state must extend from.

`OutProp` - Property as defined by a view's parent.

`State` - The container state.

### Functions

| Name | Summary |
|---|---|
| [mapState](map-state.md) | `abstract fun mapState(state: `[`LState`](index.md#LState)`, outProp: `[`OutProp`](index.md#OutProp)`): `[`State`](index.md#State)<br>Map [LState](index.md#LState) to [State](index.md#State) using [OutProp](index.md#OutProp) |

### Inheritors

| Name | Summary |
|---|---|
| [IPropMapper](../-i-prop-mapper.md) | `interface IPropMapper<in LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, in OutProp, out State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, out Action : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IStateMapper`](./index.md)`<`[`LState`](../-i-prop-mapper.md#LState)`, `[`OutProp`](../-i-prop-mapper.md#OutProp)`, `[`State`](../-i-prop-mapper.md#State)`>, `[`IActionMapper`](../-i-action-mapper/index.md)`<`[`OutProp`](../-i-prop-mapper.md#OutProp)`, `[`Action`](../-i-prop-mapper.md#Action)`>`<br>Maps [LState](../-i-prop-mapper.md#LState) to [State](../-i-prop-mapper.md#State) and [Action](../-i-prop-mapper.md#Action) for a [IPropContainer](../-i-prop-container/index.md). [OutProp](../-i-prop-mapper.md#OutProp) is the view's immutable property as dictated by its parent. |
