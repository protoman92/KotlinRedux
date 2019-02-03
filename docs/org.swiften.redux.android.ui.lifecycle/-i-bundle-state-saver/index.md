[docs](../../index.md) / [org.swiften.redux.android.ui.lifecycle](../index.md) / [IBundleStateSaver](./index.md)

# IBundleStateSaver

`interface IBundleStateSaver<GState>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-lifecycle/src/main/java/org/swiften/redux/android/ui/lifecycle/AndroidActivity.kt#L24)

Handle saving/restoring [GState](index.md#GState) instances.

### Functions

| Name | Summary |
|---|---|
| [restoreState](restore-state.md) | `abstract fun restoreState(bundle: <ERROR CLASS>): `[`GState`](index.md#GState)`?` |
| [saveState](save-state.md) | `abstract fun saveState(bundle: <ERROR CLASS>, state: `[`GState`](index.md#GState)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
