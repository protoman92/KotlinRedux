[docs](../index.md) / [org.swiften.redux.core](index.md) / [createRouterMiddleware](./create-router-middleware.md)

# createRouterMiddleware

`inline fun <reified Screen : `[`IRouterScreen`](-i-router-screen.md)`> createRouterMiddleware(router: `[`IRouter`](-i-router/index.md)`<`[`Screen`](create-router-middleware.md#Screen)`>): `[`IMiddleware`](-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/RouterMiddleware.kt#L72)

Create a [RouterMiddleware](-router-middleware/index.md) with [router](create-router-middleware.md#org.swiften.redux.core$createRouterMiddleware(org.swiften.redux.core.IRouter((org.swiften.redux.core.createRouterMiddleware.Screen)))/router).

### Parameters

`Screen` - The app [IRouterScreen](-i-router-screen.md) type.

`router` - See [RouterMiddleware.router](-router-middleware/router.md).

**Return**
A [RouterMiddleware](-router-middleware/index.md) instance.

