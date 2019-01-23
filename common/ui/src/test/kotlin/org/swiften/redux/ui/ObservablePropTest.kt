/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.ui

import org.junit.Assert
import org.junit.Test

/** Created by haipham on 2019/01/16 */
class ObservablePropTest {
  @Test
  @Suppress("UNUSED_VALUE")
  fun `Observable prop should work correctly`() {
    // Setup
    var setCount = 0
    var prop by VetoableObservableProp<Int> { _, _ -> setCount += 1 }

    // When
    prop = 1; prop = 2; prop = 3

    // Then
    Assert.assertEquals(setCount, 3)
    Assert.assertEquals(prop, 3)
  }

  @Test
  @Suppress("UNUSED_VALUE")
  fun `Observable variable prop should work correctly`() {
    // Setup
    data class S(val query: String)
    data class A(val action: () -> Unit)
    var setCount = 0
    var prop by ObservableVariableProps<S, A> { _, _ -> setCount += 1 }

    // When
    prop = VariableProps(S("1"), A {})
    prop = VariableProps(S("1"), A {})
    prop = VariableProps(S("2"), A {})
    prop = VariableProps(S("3"), A {})
    
    // Then
    Assert.assertEquals(setCount, 3)
    Assert.assertEquals(prop!!.state, S("3"))
  }
}
