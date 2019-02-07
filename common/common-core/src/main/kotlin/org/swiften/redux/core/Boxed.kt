/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.core

/** Created by viethai.pham on 2019/02/07 */
/** Use this to wrap [T] to handle nullables wherever [T] is required to be [Any] */
data class Boxed<T>(val value: T)
