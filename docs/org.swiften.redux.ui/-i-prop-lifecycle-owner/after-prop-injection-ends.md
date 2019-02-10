[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IPropLifecycleOwner](index.md) / [afterPropInjectionEnds](./after-prop-injection-ends.md)

# afterPropInjectionEnds

`open fun afterPropInjectionEnds(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L38)

This is called after [IReduxSubscription.unsubscribe](../../org.swiften.redux.core/-i-redux-subscription/unsubscribe.md) is called. Override the default
implementation to catch this event.

