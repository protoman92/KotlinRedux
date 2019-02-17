[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [FinalStore](./index.md)

# FinalStore

`class FinalStore<GState : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> : `[`IReduxStore`](../-i-redux-store.md)`<`[`GState`](index.md#GState)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/FinalStore.kt#L15)

[FinalStore](./index.md) is a [IReduxStore](../-i-redux-store.md) that combines all crucial [IReduxStore](../-i-redux-store.md) implementations to
provide a full suite of functionalities.

### Parameters

`GState` - The global state type.

`store` - An [IReduxStore](../-i-redux-store.md) instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `FinalStore(state: `[`GState`](index.md#GState)`, reducer: `[`IReducer`](../-i-reducer.md)`<`[`GState`](index.md#GState)`>)``FinalStore(store: `[`IReduxStore`](../-i-redux-store.md)`<`[`GState`](index.md#GState)`>)`<br>[FinalStore](./index.md) is a [IReduxStore](../-i-redux-store.md) that combines all crucial [IReduxStore](../-i-redux-store.md) implementations to provide a full suite of functionalities. |
