[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [AsyncMiddleware](index.md) / [create](./create.md)

# create

`internal fun create(context: <ERROR CLASS>): `[`IMiddleware`](../-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/AsyncMiddleware.kt#L26)

Create a [AsyncMiddleware](index.md) with [context](create.md#org.swiften.redux.core.AsyncMiddleware.Companion$create()/context).

### Parameters

`context` - See [AsyncMiddleware.context](context.md).

**Return**
An [AsyncMiddleware](index.md) instance.

`fun create(): `[`IMiddleware`](../-i-middleware.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/AsyncMiddleware.kt#L35)

Create a [AsyncMiddleware](index.md) with a default [CoroutineContext](#). This is made public so that
users of this [AsyncMiddleware](index.md) cannot share its [CoroutineContext](#) with other users.

**Return**
An [AsyncMiddleware](index.md) instance.

