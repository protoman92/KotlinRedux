[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [EmptyPropLifecycleOwner](./index.md)

# EmptyPropLifecycleOwner

`class EmptyPropLifecycleOwner<GlobalState> : `[`IPropLifecycleOwner`](../-i-prop-lifecycle-owner/index.md)`<`[`GlobalState`](index.md#GlobalState)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L31)

Treat this as a delegate for [IPropLifecycleOwner](../-i-prop-lifecycle-owner/index.md) that does not hold any logic

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `EmptyPropLifecycleOwner()`<br>Treat this as a delegate for [IPropLifecycleOwner](../-i-prop-lifecycle-owner/index.md) that does not hold any logic |

### Functions

| Name | Summary |
|---|---|
| [afterPropInjectionEnds](after-prop-injection-ends.md) | `fun afterPropInjectionEnds(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This is called after [IReduxSubscription.unsubscribe](../../org.swiften.redux.core/-i-redux-subscription/unsubscribe.md) is called |
| [beforePropInjectionStarts](before-prop-injection-starts.md) | `fun beforePropInjectionStarts(sp: `[`StaticProps`](../-static-props/index.md)`<`[`GlobalState`](index.md#GlobalState)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This is called before [IPropInjector.inject](../-i-prop-injector/inject.md) is called |
