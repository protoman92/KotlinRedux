[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [DefaultReduxAction](./index.md)

# DefaultReduxAction

`sealed class DefaultReduxAction : `[`IReduxAction`](../-i-redux-action.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Preset.kt#L10)

Default [IReduxAction](../-i-redux-action.md) implementation

### Types

| Name | Summary |
|---|---|
| [Deinitialize](-deinitialize.md) | `object Deinitialize : `[`DefaultReduxAction`](./index.md) |
| [Dummy](-dummy.md) | `object Dummy : `[`DefaultReduxAction`](./index.md) |
| [MapState](-map-state/index.md) | `class MapState<GState> : `[`DefaultReduxAction`](./index.md)<br>Replace the current [GState](-map-state/index.md#GState) with [fn](-map-state/fn.md). |
| [ReplaceState](-replace-state/index.md) | `data class ReplaceState<out State> : `[`DefaultReduxAction`](./index.md)<br>Replace the current [State](-replace-state/index.md#State) with [state](-replace-state/state.md). |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `DefaultReduxAction()`<br>Default [IReduxAction](../-i-redux-action.md) implementation |

### Inheritors

| Name | Summary |
|---|---|
| [Deinitialize](-deinitialize.md) | `object Deinitialize : `[`DefaultReduxAction`](./index.md) |
| [Dummy](-dummy.md) | `object Dummy : `[`DefaultReduxAction`](./index.md) |
| [MapState](-map-state/index.md) | `class MapState<GState> : `[`DefaultReduxAction`](./index.md)<br>Replace the current [GState](-map-state/index.md#GState) with [fn](-map-state/fn.md). |
| [ReplaceState](-replace-state/index.md) | `data class ReplaceState<out State> : `[`DefaultReduxAction`](./index.md)<br>Replace the current [State](-replace-state/index.md#State) with [state](-replace-state/state.md). |
