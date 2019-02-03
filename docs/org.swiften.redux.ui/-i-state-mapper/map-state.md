[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IStateMapper](index.md) / [mapState](./map-state.md)

# mapState

`abstract fun mapState(state: `[`GlobalState`](index.md#GlobalState)`, outProps: `[`OutProps`](index.md#OutProps)`): `[`State`](index.md#State) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L75)

Map [GlobalState](index.md#GlobalState) to [State](index.md#State) using [OutProps](index.md#OutProps)

### Parameters

`state` - The latest [GlobalState](index.md#GlobalState) instance.

`outProps` - The [OutProps](index.md#OutProps) instance.

**Return**
A [State](index.md#State) instance.

