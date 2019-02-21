[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IStateMapper](index.md) / [mapState](./map-state.md)

# mapState

`abstract fun mapState(state: `[`LState`](index.md#LState)`, outProp: `[`OutProp`](index.md#OutProp)`): `[`State`](index.md#State) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L75)

Map [LState](index.md#LState) to [State](index.md#State) using [OutProp](index.md#OutProp)

### Parameters

`state` - The latest [LState](index.md#LState) instance.

`outProp` - The [OutProp](index.md#OutProp) instance.

**Return**
A [State](index.md#State) instance.

