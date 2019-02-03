[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [MiddlewareInput](./index.md)

# MiddlewareInput

`class MiddlewareInput<out GlobalState>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/Middleware.kt#L19)

Input for middlewares that includes some functionalities from [IReduxStore](../-i-redux-store.md)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `MiddlewareInput(stateGetter: `[`IStateGetter`](../-i-state-getter.md)`<`[`GlobalState`](index.md#GlobalState)`>, subscriber: `[`IReduxSubscriber`](../-i-redux-subscriber.md)`<`[`GlobalState`](index.md#GlobalState)`>)`<br>Input for middlewares that includes some functionalities from [IReduxStore](../-i-redux-store.md) |

### Properties

| Name | Summary |
|---|---|
| [stateGetter](state-getter.md) | `val stateGetter: `[`IStateGetter`](../-i-state-getter.md)`<`[`GlobalState`](index.md#GlobalState)`>` |
| [subscriber](subscriber.md) | `val subscriber: `[`IReduxSubscriber`](../-i-redux-subscriber.md)`<`[`GlobalState`](index.md#GlobalState)`>` |
