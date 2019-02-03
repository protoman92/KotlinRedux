[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IActionMapper](index.md) / [mapAction](./map-action.md)

# mapAction

`abstract fun mapAction(dispatch: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md)`, state: `[`GlobalState`](index.md#GlobalState)`, outProps: `[`OutProps`](index.md#OutProps)`): `[`Action`](index.md#Action) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L92)

Map [IActionDispatcher](../../org.swiften.redux.core/-i-action-dispatcher.md) to [Action](index.md#Action) using [GlobalState](index.md#GlobalState) and [OutProps](index.md#OutProps)

### Parameters

`dispatch` - See [IReduxStore.dispatch](../../org.swiften.redux.core/-i-dispatcher-provider/dispatch.md).

`state` - The latest [GlobalState](index.md#GlobalState) instance.

`outProps` - The [OutProps](index.md#OutProps) instance.

**Return**
An [Action](index.md#Action) instance.

