[docs](../../index.md) / [org.swiften.redux.android.ui.recyclerview](../index.md) / [IDiffItemCallback](./index.md)

# IDiffItemCallback

`interface IDiffItemCallback<T>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/android/android-recyclerview/src/main/java/org/swiften/redux/android/ui/recyclerview/DiffedAdapter.kt#L28)

Callback for [DiffUtil.ItemCallback](#) since [DiffUtil.ItemCallback](#) is an abstract class.

### Functions

| Name | Summary |
|---|---|
| [areContentsTheSame](are-contents-the-same.md) | `abstract fun areContentsTheSame(oldItem: `[`T`](index.md#T)`, newItem: `[`T`](index.md#T)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [areItemsTheSame](are-items-the-same.md) | `abstract fun areItemsTheSame(oldItem: `[`T`](index.md#T)`, newItem: `[`T`](index.md#T)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
