[docs](../../index.md) / [org.swiften.redux.core](../index.md) / [DefaultUniqueIDProvider](./index.md)

# DefaultUniqueIDProvider

`class DefaultUniqueIDProvider : `[`IUniqueIDProvider`](../-i-unique-i-d-provider/index.md) [(source)](https://github.com/protoman92/KotlinRedux/tree/master/common/common-core/src/main/kotlin/org/swiften/redux/core/SubscriberID.kt#L18)

Default implementation of [IUniqueIDProvider](../-i-unique-i-d-provider/index.md) that simply uses [UUID.randomUUID](http://docs.oracle.com/javase/6/docs/api/java/util/UUID.html#randomUUID()).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `DefaultUniqueIDProvider()`<br>Default implementation of [IUniqueIDProvider](../-i-unique-i-d-provider/index.md) that simply uses [UUID.randomUUID](http://docs.oracle.com/javase/6/docs/api/java/util/UUID.html#randomUUID()). |

### Properties

| Name | Summary |
|---|---|
| [uniqueID](unique-i-d.md) | `val uniqueID: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The unique ID to pass to [IReduxStore.subscribe](../-i-redux-subscriber-provider/subscribe.md) and [IReduxStore.unsubscribe](../-i-redux-unsubscriber-provider/unsubscribe.md). |
