[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [NoopPropLifecycleOwner](index.md) / [afterPropInjectionEnds](./after-prop-injection-ends.md)

# afterPropInjectionEnds

`fun afterPropInjectionEnds(sp: `[`StaticProp`](../-static-prop/index.md)`<`[`LState`](index.md#LState)`, `[`OutProp`](index.md#OutProp)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common\common-ui\src\main\kotlin/org/swiften/redux/ui/Container.kt#L38)

Overrides [IPropLifecycleOwner.afterPropInjectionEnds](../-i-prop-lifecycle-owner/after-prop-injection-ends.md)

This is called after [IReduxSubscription.unsubscribe](../../org.swiften.redux.core/-i-redux-subscription/unsubscribe.md) is called.

### Parameters

`sp` - A [StaticProp](../-static-prop/index.md) instance.