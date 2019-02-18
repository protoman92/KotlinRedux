[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IActionMapper](index.md) / [mapAction](./map-action.md)

# mapAction

`abstract fun mapAction(dispatch: `[`IActionDispatcher`](../../org.swiften.redux.core/-i-action-dispatcher.md)`, outProp: `[`OutProp`](index.md#OutProp)`): `[`Action`](index.md#Action) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L92)

Map [IActionDispatcher](../../org.swiften.redux.core/-i-action-dispatcher.md) to [Action](index.md#Action) using [OutProp](index.md#OutProp).

### Parameters

`dispatch` - An [IActionDispatcher](../../org.swiften.redux.core/-i-action-dispatcher.md) instance.

`outProp` - The [OutProp](index.md#OutProp) instance.

**Return**
An [Action](index.md#Action) instance.

