/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business3

import dagger.Subcomponent
import org.swiften.redux.android.dagger.DependencyLevel1

/** Created by viethai.pham on 2019/02/21 */
@Business3Scope
@Subcomponent(modules = [Business3HostModule::class, Business3Module::class])
interface Business3Component {
  fun hostDependency(): DependencyLevel1
}
