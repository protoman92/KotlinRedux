[docs](../../index.md) / [org.swiften.redux.android.ui.recyclerview](../index.md) / [ReduxListAdapter](index.md) / [beforePropInjectionStarts](./before-prop-injection-starts.md)

# beforePropInjectionStarts

`open fun beforePropInjectionStarts(sp: `[`StaticProp`](../../org.swiften.redux.ui/-static-prop/index.md)`<`[`LState`](index.md#LState)`, `[`OutProp`](index.md#OutProp)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/DiffedAdapter.kt#L87)

Overrides [IPropLifecycleOwner.beforePropInjectionStarts](../../org.swiften.redux.ui/-i-prop-lifecycle-owner/before-prop-injection-starts.md)

This is called before [IFullPropInjector.inject](../../org.swiften.redux.ui/-i-prop-injector/inject.md) is called.

### Parameters

`sp` - A [StaticProp](../../org.swiften.redux.ui/-static-prop/index.md) instance.