[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [IPropLifecycleOwner](index.md) / [beforePropInjectionStarts](./before-prop-injection-starts.md)

# beforePropInjectionStarts

`abstract fun beforePropInjectionStarts(sp: `[`StaticProp`](../-static-prop/index.md)`<`[`LState`](index.md#LState)`, `[`OutProp`](index.md#OutProp)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/Injector.kt#L31)

This is called before [IPropInjector.inject](../-i-prop-injector/inject.md) is called.

### Parameters

`sp` - A [StaticProp](../-static-prop/index.md) instance.