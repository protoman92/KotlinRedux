[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [NestedRouter](index.md) / [create](./create.md)

# create

`fun create(navigator: (`[`IRouterScreen`](../-i-router-screen.md)`) -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`IRouter`](../-i-router/index.md)`<`[`IRouterScreen`](../-i-router-screen.md)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/NestedRouter.kt#L25)

Create an [IRouter](../-i-router/index.md) instance that provides locking mechanisms for an internal [NestedRouter](index.md).

### Parameters

`navigator` - See [NestedRouter.navigator](navigator.md).

**Return**
An [IRouter](../-i-router/index.md) instance.

