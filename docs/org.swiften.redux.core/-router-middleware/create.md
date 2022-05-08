[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [RouterMiddleware](index.md) / [create](./create.md)

# create

`inline fun <reified Screen : `[`IRouterScreen`](../-i-router-screen.md)`> create(router: `[`IRouter`](../-i-router/index.md)`<`[`Screen`](create.md#Screen)`>): `[`IMiddleware`](../-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-core\src\main\kotlin/org/swiften/redux/core/RouterMiddleware.kt#L28)

Create a [RouterMiddleware](index.md) with [router](create.md#org.swiften.redux.core.RouterMiddleware.Companion$create(org.swiften.redux.core.IRouter((org.swiften.redux.core.RouterMiddleware.Companion.create.Screen)))/router).

### Parameters

`Screen` - The app [IRouterScreen](../-i-router-screen.md) type.

`router` - See [RouterMiddleware.router](router.md).

**Return**
A [RouterMiddleware](index.md) instance.

