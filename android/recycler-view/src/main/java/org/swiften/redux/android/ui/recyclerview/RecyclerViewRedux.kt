/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.recyclerview

import androidx.recyclerview.widget.RecyclerView
import org.swiften.redux.ui.ReduxUI

/** Created by haipham on 2019/01/08 */
/** Injector props for a [RecyclerView.Adapter] */
fun <State, VH> ReduxUI.IPropInjector<State>.injectProps(
  adapter: RecyclerView.Adapter<VH>
) : RecyclerView.Adapter<VH> where VH : RecyclerView.ViewHolder = adapter
