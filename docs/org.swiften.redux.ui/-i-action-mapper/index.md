[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IActionMapper](./index.md)

# IActionMapper

`interface IActionMapper<in OutProp, out Action : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L88)

Maps [IActionDispatcher](../../org.swiften.redux.core/-i-action-dispatcher.md) to [Action](index.md#Action) for a [IPropContainer](../-i-prop-container/index.md). Note that [Action](index.md#Action) can include
external, non-Redux related dependencies provided by [OutProp](index.md#OutProp).

For example, if the app wants to load an image into a view, it's probably not a good idea to
download that image and store in the global state to be mapped into [ReduxProp.state](../-redux-prop/state.md). It is
better to inject an image downloader in [Action](index.md#Action) using [OutProp](index.md#OutProp).

### Parameters

`OutProp` - Property as defined by a view's parent.

`Action` - See [ReduxProp.action](../-redux-prop/action.md).

### Functions

| Name | Summary |
|---|---|
| [mapAction](map-action.md) | `abstract fun mapAction(dispatch: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md)`, outProp: `[`OutProp`](index.md#OutProp)`): `[`Action`](index.md#Action)<br>Map [IActionDispatcher](../../org.swiften.redux.core/-i-action-dispatcher.md) to [Action](index.md#Action) using [OutProp](index.md#OutProp). |

### Inheritors

| Name | Summary |
|---|---|
| [IPropMapper](../-i-prop-mapper.md) | `interface IPropMapper<in LState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, in OutProp, out State : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`, out Action : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IStateMapper`](../-i-state-mapper/index.md)`<`[`LState`](../-i-prop-mapper.md#LState)`, `[`OutProp`](../-i-prop-mapper.md#OutProp)`, `[`State`](../-i-prop-mapper.md#State)`>, `[`IActionMapper`](./index.md)`<`[`OutProp`](../-i-prop-mapper.md#OutProp)`, `[`Action`](../-i-prop-mapper.md#Action)`>`<br>Maps [LState](../-i-prop-mapper.md#LState) to [State](../-i-prop-mapper.md#State) and [Action](../-i-prop-mapper.md#Action) for a [IPropContainer](../-i-prop-container/index.md). [OutProp](../-i-prop-mapper.md#OutProp) is the view's immutable property as dictated by its parent. |
