package org.swiften.redux
import io.reactivex.processors.BehaviorProcessor
import org.testng.Assert
import org.testng.annotations.Test
import org.swiften.redux.rxstore.ReduxProcessor

/**
 * Created by haipham on 1/4/18.
 */

class ReduxProcessorTest {
  @Test
  fun test_reduxProcessor_shouldNotAcceptErrorAndCompletedEvents() {
    /// Setup
    val processor = BehaviorProcessor.createDefault(0)
    val reduxProcessor = ReduxProcessor(processor)

    /// When
    reduxProcessor.onNext(1)
    reduxProcessor.onNext(2)
    reduxProcessor.onNext(3)
    reduxProcessor.onError(Exception(""))
    reduxProcessor.onComplete()
    reduxProcessor.onNext(4)

    /// Then
    Assert.assertEquals(processor.value, 4)
  }
}