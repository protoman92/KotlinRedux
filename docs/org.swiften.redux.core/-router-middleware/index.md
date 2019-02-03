[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [RouterMiddleware](./index.md)

# RouterMiddleware

`@PublishedApi internal class RouterMiddleware<Screen : `[`IRouterScreen`](../-i-router-screen.md)`> : `[`IMiddleware`](../-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/RouterMiddleware.kt#L28)

[IMiddleware](../-i-middleware.md) implementation for [IRouter](../-i-router/index.md) middleware

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `RouterMiddleware(cls: `[`KClass`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)`<`[`Screen`](index.md#Screen)`>, router: `[`IRouter`](../-i-router/index.md)`<`[`Screen`](index.md#Screen)`>)``RouterMiddleware(cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`Screen`](index.md#Screen)`>, router: `[`IRouter`](../-i-router/index.md)`<`[`Screen`](index.md#Screen)`>)`<br>[IMiddleware](../-i-middleware.md) implementation for [IRouter](../-i-router/index.md) middleware |

### Properties

| Name | Summary |
|---|---|
| [cls](cls.md) | `val cls: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`Screen`](index.md#Screen)`>` |
| [router](router.md) | `val router: `[`IRouter`](../-i-router/index.md)`<`[`Screen`](index.md#Screen)`>` |

### Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `fun invoke(p1: `[`MiddlewareInput`](../-middleware-input/index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>): `[`DispatchMapper`](../-dispatch-mapper.md) |
