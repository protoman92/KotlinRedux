[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [RouterMiddleware](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`RouterMiddleware(cls: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<`[`Screen`](index.md#Screen)`>, router: `[`IRouter`](../-i-router/index.md)`<`[`Screen`](index.md#Screen)`>)``private RouterMiddleware(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`Screen`](index.md#Screen)`>, router: `[`IRouter`](../-i-router/index.md)`<`[`Screen`](index.md#Screen)`>)`

[IMiddleware](../-i-middleware.md) implementation for [IRouter](../-i-router/index.md) middleware.

### Parameters

`Screen` - The app [IRouterScreen](../-i-router-screen.md) type.

`cls` - The [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html) of the [Screen](index.md#Screen) type to check type for [IReduxAction](../-i-redux-action.md).

`router` - An [IRouter](../-i-router/index.md) instance.