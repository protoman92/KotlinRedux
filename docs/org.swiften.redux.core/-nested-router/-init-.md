[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [NestedRouter](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`private NestedRouter(navigator: (`[`IRouterScreen`](../-i-router-screen.md)`) -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`)`

[IRouter](../-i-router/index.md) implementation that holds on to a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of [IVetoableRouter](../-i-vetoable-router/index.md), each of which will
call [IVetoableRouter.navigate](../-i-vetoable-router/navigate.md) to check if it can perform a successful navigation. If not, we
move on to the next [IVetoableRouter](../-i-vetoable-router/index.md) until the end.

For this [IRouter](../-i-router/index.md) implementation, we are not overly worried about performance because there
won't be a situation whereby the user has gone through thousands of [IVetoableRouter](../-i-vetoable-router/index.md)-enabled
screens, thus significantly increasing the size of [subRouters](sub-routers.md).

### Parameters

`navigator` - The navigation function that will be called before we touch [subRouters](sub-routers.md).