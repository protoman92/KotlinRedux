/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger

/** Created by viethai.pham on 2019/02/21 */
class DependencyLevel1(val sd1: DependencyLevel2x1, val sd2: DependencyLevel2x2)
class DependencyLevel2x1(val sd1: DependencyLevel3x1, val sd2: DependencyLevel3x2)
class DependencyLevel3x1
class DependencyLevel3x2
class DependencyLevel2x2(val sd1: DependencyLevel3x3, val sd2: DependencyLevel3x4)
class DependencyLevel3x3
class DependencyLevel3x4
