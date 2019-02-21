[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [NoopPropLifecycleOwner](index.md) / [beforePropInjectionStarts](./before-prop-injection-starts.md)

# beforePropInjectionStarts

`fun beforePropInjectionStarts(sp: `[`StaticProp`](../-static-prop/index.md)`<`[`LState`](index.md#LState)`, `[`OutProp`](index.md#OutProp)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L49)

Overrides [IPropLifecycleOwner.beforePropInjectionStarts](../-i-prop-lifecycle-owner/before-prop-injection-starts.md)

This is called before [IFullPropInjector.inject](../-i-prop-injector/inject.md) is called.

### Parameters

`sp` - A [StaticProp](../-static-prop/index.md) instance.