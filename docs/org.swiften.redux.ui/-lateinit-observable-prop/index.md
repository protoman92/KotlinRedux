[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [LateinitObservableProp](./index.md)

# LateinitObservableProp

`open class LateinitObservableProp<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/ObservableProp.kt#L20)

Note that [notifier](notifier.md) passes along both the previous and upcoming [T](index.md#T) values

### Parameters

`T` - The property type to be observed.

`notifier` - Broadcast the latest [T](index.md#T) instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `LateinitObservableProp(notifier: (`[`T`](index.md#T)`?, `[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`)`<br>Note that [notifier](notifier.md) passes along both the previous and upcoming [T](index.md#T) values |

### Properties

| Name | Summary |
|---|---|
| [lock](lock.md) | `val lock: <ERROR CLASS>` |
| [notifier](notifier.md) | `val notifier: (`[`T`](index.md#T)`?, `[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Broadcast the latest [T](index.md#T) instance. |
| [value](value.md) | `lateinit var value: `[`T`](index.md#T) |

### Functions

| Name | Summary |
|---|---|
| [getValue](get-value.md) | `open fun getValue(thisRef: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?, property: `[`KProperty`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-property/index.html)`<*>): <ERROR CLASS>` |
| [setValue](set-value.md) | `open fun setValue(thisRef: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?, property: `[`KProperty`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-property/index.html)`<*>, value: `[`T`](index.md#T)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
