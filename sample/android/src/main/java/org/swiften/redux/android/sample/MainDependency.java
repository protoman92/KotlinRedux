/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample;

import androidx.annotation.NonNull;
import org.swiften.redux.ui.IReduxPropInjector;

/** Created by haipham on 2018/12/19 */
public final class MainDependency {
  @NonNull final IReduxPropInjector<State> injector;

  MainDependency(@NonNull IReduxPropInjector<State> injector) {
    this.injector = injector;
  }
}
