[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IActionMapper](index.md) / [mapAction](./map-action.md)

# mapAction

`abstract fun mapAction(static: `[`IActionDependency`](../-i-action-dependency/index.md)`<`[`GExt`](index.md#GExt)`>, outProps: `[`OutProps`](index.md#OutProps)`): `[`Action`](index.md#Action) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L103)

Map [IActionDispatcher](../../org.swiften.redux.core/-i-action-dispatcher.md) to [Action](index.md#Action) using [GExt](index.md#GExt) and [OutProps](index.md#OutProps)

### Parameters

`static` - An [IActionDependency](../-i-action-dependency/index.md) instance.

`outProps` - The [OutProps](index.md#OutProps) instance.

**Return**
An [Action](index.md#Action) instance.

