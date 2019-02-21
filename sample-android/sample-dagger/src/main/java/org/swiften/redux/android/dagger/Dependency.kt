/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger

/** Created by viethai.pham on 2019/02/21 */
class DependencyLevel1(val tag: String, val sd: DependencyLevel2) {
  fun finalize() {
    println("Redux: Finalized dependency for ${this.tag}")
  }
}

class DependencyLevel2(val sd: DependencyLevel3)
class DependencyLevel3
