[docs](../../index.md) / [org.swiften.redux.ui](../index.md) / [VetoableObservableProp](./index.md)

# VetoableObservableProp

`open class VetoableObservableProp<T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-ui/src/main/kotlin/org/swiften/redux/ui/ObservableProp.kt#L16)

Note that [notifier](notifier.md) passes along both the previous and upcoming [T](index.md#T) values

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `VetoableObservableProp(equalChecker: (`[`T`](index.md#T)`?, `[`T`](index.md#T)`) -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = { a, b -> a == b }, notifier: (`[`T`](index.md#T)`?, `[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`)`<br>Note that [notifier](notifier.md) passes along both the previous and upcoming [T](index.md#T) values |

### Properties

| Name | Summary |
|---|---|
| [equalChecker](equal-checker.md) | `val equalChecker: (`[`T`](index.md#T)`?, `[`T`](index.md#T)`) -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [lock](lock.md) | `val lock: <ERROR CLASS>` |
| [notifier](notifier.md) | `val notifier: (`[`T`](index.md#T)`?, `[`T`](index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [value](value.md) | `lateinit var value: `[`T`](index.md#T) |

### Functions

| Name | Summary |
|---|---|
| [getValue](get-value.md) | `open fun getValue(thisRef: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?, property: `[`KProperty`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-property/index.html)`<*>): <ERROR CLASS>` |
| [setValue](set-value.md) | `open fun setValue(thisRef: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?, property: `[`KProperty`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-property/index.html)`<*>, value: `[`T`](index.md#T)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
