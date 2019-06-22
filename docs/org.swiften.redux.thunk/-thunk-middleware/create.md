[docs](../../index.md) / [org.swiften.redux.thunk](../index.md) / [ThunkMiddleware](index.md) / [create](./create.md)

# create

`internal fun <GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> create(external: `[`GExt`](create.md#GExt)`, context: <ERROR CLASS>): `[`IMiddleware`](../../org.swiften.redux.core/-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-thunk/src/main/kotlin/org/swiften/redux/thunk/ThunkMiddleware.kt#L73)

Create a [ThunkMiddleware](index.md) with [context](create.md#org.swiften.redux.thunk.ThunkMiddleware.Companion$create(org.swiften.redux.thunk.ThunkMiddleware.Companion.create.GExt, )/context).

### Parameters

`GExt` - The external argument type.

`external` - The external [GExt](create.md#GExt) argument.

`context` - See [ThunkMiddleware.context](context.md).

**Return**
A [ThunkMiddleware](index.md) instance.

`fun <GExt : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> create(external: `[`GExt`](create.md#GExt)`): `[`IMiddleware`](../../org.swiften.redux.core/-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-thunk/src/main/kotlin/org/swiften/redux/thunk/ThunkMiddleware.kt#L84)

Create a [ThunkMiddleware](index.md) with a default [CoroutineContext](#). This is made public so that users
of this [ThunkMiddleware](index.md) cannot share its [CoroutineContext](#) with other users.

### Parameters

`GExt` - The external argument type.

`external` - The external [GExt](create.md#GExt) argument.

**Return**
A [ThunkMiddleware](index.md) instance.

