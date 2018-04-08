package org.swiften.redux
import io.reactivex.processors.PublishProcessor
import io.reactivex.processors.ReplayProcessor
import org.swiften.redux.rxstore.MappedSubscriber
import org.swiften.redux.rxstore.ReduxProcessor
import org.testng.Assert
import org.testng.annotations.Test

/**
 * Created by haipham on 6/4/18.
 */

class MappableSubscriberTest {
  @Test
  fun test_mappableSubscriberMappingArg_shouldWork() {
    /// Setup
    val receiver = ReplayProcessor.create<String>()
    val processor = ReduxProcessor(receiver)
    val mapped = processor.mapArg<Int> { it.toString() }
    val values = 0 until 1000
    val stringValues = values.map { it.toString() }

    /// When
    mapped.onError(Exception(""))
    mapped.onComplete()
    values.forEach { mapped.onNext(it) }

    /// Then
    Assert.assertEquals(receiver.values.map { it as String }, stringValues)
  }

  @Test
  fun test_mapperWithError_shouldWork() {
    /// Setup
    val receiver = ReplayProcessor.create<String>()
    val mapped = MappedSubscriber<Int, String>(receiver) { throw Exception("") }
    val mapped2 = mapped.mapArg<Int> { it }
    val source = PublishProcessor.create<Int>()
    val values = 0 until 1000
    source.subscribe(mapped2)

    /// When
    values.forEach { mapped2.onNext(it) }
    values.forEach { source.onNext(it) }

    /// Then
    Assert.assertTrue(receiver.values.isEmpty())
  }
}