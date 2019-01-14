/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample;

import org.swiften.redux.ui.IReduxPropInjector;

/** Created by haipham on 2018/12/19 */
public final class MainDependency {
  IReduxPropInjector<State> injector;

  MainDependency(IReduxPropInjector<State> injector) {
    this.injector = injector;
  }
}
