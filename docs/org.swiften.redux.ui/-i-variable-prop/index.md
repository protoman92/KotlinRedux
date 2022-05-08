[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IVariableProp](./index.md)

# IVariableProp

`interface IVariableProp<out State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, out Action : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-ui\src\main\kotlin/org/swiften/redux/ui/Props.kt#L14)

Container for an [IPropContainer](../-i-prop-container/index.md) mutable properties.

### Parameters

`State` - State type that contains view information.

`Action` - Action type that handles view interactions.

### Properties

| Name | Summary |
|---|---|
| [action](action.md) | `abstract val action: `[`Action`](index.md#Action) |
| [firstTime](first-time.md) | `abstract val firstTime: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [state](state.md) | `abstract val state: `[`State`](index.md#State) |

### Inheritors

| Name | Summary |
|---|---|
| [ReduxProp](../-redux-prop/index.md) | `data class ReduxProp<State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, Action : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IVariableProp`](./index.md)`<`[`State`](../-redux-prop/index.md#State)`, `[`Action`](../-redux-prop/index.md#Action)`>`<br>Container for [state](../-redux-prop/state.md) and [action](../-redux-prop/action.md). |
