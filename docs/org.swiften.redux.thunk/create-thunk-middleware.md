[docs](../index.md) / [org.swiften.redux.thunk](index.md) / [createThunkMiddleware](./create-thunk-middleware.md)

# createThunkMiddleware

`internal fun <GExt> createThunkMiddleware(external: `[`GExt`](create-thunk-middleware.md#GExt)`, context: <ERROR CLASS> = SupervisorJob()): `[`IMiddleware`](../org.swiften.redux.core/-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-thunk/src/main/kotlin/org/swiften/redux/thunk/ThunkMiddleware.kt#L93)

Create a [ThunkMiddleware](-thunk-middleware/index.md) with [context](create-thunk-middleware.md#org.swiften.redux.thunk$createThunkMiddleware(org.swiften.redux.thunk.createThunkMiddleware.GExt, )/context).

### Parameters

`GExt` - The external argument type.

`external` - The external [GExt](create-thunk-middleware.md#GExt) argument.

`context` - See [ThunkMiddleware.context](-thunk-middleware/context.md).

**Return**
A [ThunkMiddleware](-thunk-middleware/index.md) instance.

`fun <GExt> createThunkMiddleware(external: `[`GExt`](create-thunk-middleware.md#GExt)`): `[`IMiddleware`](../org.swiften.redux.core/-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-thunk/src/main/kotlin/org/swiften/redux/thunk/ThunkMiddleware.kt#L107)

Create a [ThunkMiddleware](-thunk-middleware/index.md) with a default [CoroutineContext](#). This is made public so that users
of this [ThunkMiddleware](-thunk-middleware/index.md) cannot share its [CoroutineContext](#) with other users.

### Parameters

`GExt` - The external argument type.

`external` - The external [GExt](create-thunk-middleware.md#GExt) argument.

**Return**
A [ThunkMiddleware](-thunk-middleware/index.md) instance.

