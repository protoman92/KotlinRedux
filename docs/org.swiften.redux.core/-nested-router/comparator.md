[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [NestedRouter](index.md) / [comparator](./comparator.md)

# comparator

`private val comparator: <ERROR CLASS><`[`IVetoableRouter`](../-i-vetoable-router/index.md)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/NestedRouter.kt#L28)

A [Comparator](#) that will be used to sort [subRouters](sub-routers.md). This will be done to
determine which [IVetoableRouter](../-i-vetoable-router/index.md) should be invoked first, and should be used in conjunction
with [DefaultUniqueIDProvider](../-default-unique-i-d-provider/index.md) thanks to its auto-incrementing of provided IDs (so that we know
the order of object creation).

