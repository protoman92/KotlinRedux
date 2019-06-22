[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [NestedRouter](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`private NestedRouter(navigator: (`[`IRouterScreen`](../-i-router-screen.md)`) -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, comparator: <ERROR CLASS><`[`IVetoableSubRouter`](../-i-vetoable-sub-router/index.md)`> = object : Comparator<IVetoableSubRouter> {
    override fun compare(p0: IVetoableSubRouter?, p1: IVetoableSubRouter?): Int {
      return if (p0 != null && p1 != null) (p1.subRouterPriority - p0.subRouterPriority).toInt() else 0
    }
  })`

[IRouter](../-i-router/index.md) implementation that holds on to a [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html) of [IVetoableSubRouter](../-i-vetoable-sub-router/index.md), each of which will
call [IVetoableSubRouter.navigate](../-i-vetoable-sub-router/navigate.md) to check if it can perform a successful navigation. If not, we
move on to the next [IVetoableSubRouter](../-i-vetoable-sub-router/index.md) until the end.

For this [IRouter](../-i-router/index.md) implementation, we are not overly worried about performance because there
won't be a situation whereby the user has gone through thousands of [IVetoableSubRouter](../-i-vetoable-sub-router/index.md)-enabled
screens, thus significantly increasing the size of [subRouters](sub-routers.md).

### Parameters

`navigator` - The navigation function that will be called before we touch [subRouters](sub-routers.md).

`comparator` - A [Comparator](#) that will be used to sort [subRouters](sub-routers.md). This will be done to
determine which [IVetoableSubRouter](../-i-vetoable-sub-router/index.md) should be invoked first, and should be used in conjunction
with [DefaultUniqueIDProvider](../-default-unique-i-d-provider/index.md) thanks to its auto-incrementing of provided IDs (so that we know
the order of object creation).