[docs](../index.md) / [org.swiften.redux.saga.rx](index.md) / [select](./select.md)

# select

`inline fun <reified State, R, R2, R3> `[`SagaEffect`](../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R`](select.md#R)`>.select(noinline selector: (`[`State`](select.md#State)`) -> `[`R2`](select.md#R2)`, noinline combiner: (`[`R`](select.md#R)`, `[`R2`](select.md#R2)`) -> `[`R3`](select.md#R3)`): `[`SagaEffect`](../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R3`](select.md#R3)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/RxExtension.kt#L19)

Invoke a [SelectEffect](-select-effect/index.md) on [this](select/-this-.md) and combine the emitted values with [combiner](select.md#org.swiften.redux.saga.rx$select(org.swiften.redux.saga.common.SagaEffect((org.swiften.redux.saga.rx.select.R)), kotlin.Function1((org.swiften.redux.saga.rx.select.State, org.swiften.redux.saga.rx.select.R2)), kotlin.Function2((org.swiften.redux.saga.rx.select.R, org.swiften.redux.saga.rx.select.R2, org.swiften.redux.saga.rx.select.R3)))/combiner)

`inline fun <reified State, R2> `[`SagaEffect`](../org.swiften.redux.saga.common/-saga-effect/index.md)`<*>.select(noinline selector: (`[`State`](select.md#State)`) -> `[`R2`](select.md#R2)`): `[`SagaEffect`](../org.swiften.redux.saga.common/-saga-effect/index.md)`<`[`R2`](select.md#R2)`>` [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-rx-saga/src/main/kotlin/org/swiften/redux/saga/rx/RxExtension.kt#L27)

Invoke a [SelectEffect](-select-effect/index.md) but ignore emissions from [this](select/-this-.md)

