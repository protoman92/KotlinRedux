[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [RouterMiddleware](./index.md)

# RouterMiddleware

`class RouterMiddleware<out Screen : `[`IRouterScreen`](../-i-router-screen.md)`> : `[`IMiddleware`](../-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/RouterMiddleware.kt#L17)

[IMiddleware](../-i-middleware.md) implementation for [IRouter](../-i-router/index.md) middleware.

### Parameters

`Screen` - The app [IRouterScreen](../-i-router-screen.md) type.

`cls` - The [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html) of the [Screen](index.md#Screen) type to check type for [IReduxAction](../-i-redux-action.md).

`router` - An [IRouter](../-i-router/index.md) instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `RouterMiddleware(cls: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<`[`Screen`](index.md#Screen)`>, router: `[`IRouter`](../-i-router/index.md)`<`[`Screen`](index.md#Screen)`>)``RouterMiddleware(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`Screen`](index.md#Screen)`>, router: `[`IRouter`](../-i-router/index.md)`<`[`Screen`](index.md#Screen)`>)`<br>[IMiddleware](../-i-middleware.md) implementation for [IRouter](../-i-router/index.md) middleware. |

### Properties

| Name | Summary |
|---|---|
| [cls](cls.md) | `private/*private to this*/ val cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`Screen`](index.md#Screen)`>`<br>The [Class](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html) of the [Screen](index.md#Screen) type to check type for [IReduxAction](../-i-redux-action.md). |
| [router](router.md) | `private/*private to this*/ val router: `[`IRouter`](../-i-router/index.md)`<`[`Screen`](index.md#Screen)`>`<br>An [IRouter](../-i-router/index.md) instance. |

### Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `fun invoke(p1: `[`MiddlewareInput`](../-middleware-input/index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>): `[`DispatchMapper`](../-dispatch-mapper.md) |

### Companion Object Functions

| Name | Summary |
|---|---|
| [create](create.md) | `fun <Screen : `[`IRouterScreen`](../-i-router-screen.md)`> create(router: `[`IRouter`](../-i-router/index.md)`<`[`Screen`](create.md#Screen)`>): `[`IMiddleware`](../-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>`<br>Create a [RouterMiddleware](./index.md) with [router](create.md#org.swiften.redux.core.RouterMiddleware.Companion$create(org.swiften.redux.core.IRouter((org.swiften.redux.core.RouterMiddleware.Companion.create.Screen)))/router). |
