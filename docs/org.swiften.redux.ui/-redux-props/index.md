[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [ReduxProps](./index.md)

# ReduxProps

`data class ReduxProps<State, Action> : `[`IVariableProps`](../-i-variable-props/index.md)`<`[`State`](index.md#State)`, `[`Action`](index.md#Action)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Props.kt#L39)

Container for [s](s.md), [state](state.md) and [action](action.md).

### Parameters

`State` - See [IVariableProps.state](../-i-variable-props/state.md).

`Action` - See [IVariableProps.action](../-i-variable-props/action.md).

`s` - An [IReduxSubscription](../../org.swiften.redux.core/-i-redux-subscription/index.md) instance.

`state` - A [State](index.md#State) instance.

`action` - An [Action](index.md#Action) instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ReduxProps(s: `[`IReduxSubscription`](../../org.swiften.redux.core/-i-redux-subscription/index.md)`, state: `[`State`](index.md#State)`?, action: `[`Action`](index.md#Action)`?)`<br>Container for [s](s.md), [state](state.md) and [action](action.md). |

### Properties

| Name | Summary |
|---|---|
| [action](action.md) | `val action: `[`Action`](index.md#Action)`?`<br>An [Action](index.md#Action) instance. |
| [s](s.md) | `val s: `[`IReduxSubscription`](../../org.swiften.redux.core/-i-redux-subscription/index.md)<br>An [IReduxSubscription](../../org.swiften.redux.core/-i-redux-subscription/index.md) instance. |
| [state](state.md) | `val state: `[`State`](index.md#State)`?`<br>A [State](index.md#State) instance. |
