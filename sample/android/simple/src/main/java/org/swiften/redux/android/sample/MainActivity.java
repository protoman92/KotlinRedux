/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.swiften.redux.ui.IStaticReduxPropContainer;
import org.swiften.redux.ui.StaticProps;

import static org.swiften.redux.android.ui.core.AndroidReduxKt.endFragmentInjection;
import static org.swiften.redux.android.ui.core.AndroidReduxKt.injectLifecycleProps;
import static org.swiften.redux.android.ui.core.AndroidReduxKt.startFragmentInjection;

/** Created by haipham on 2018/12/19 */
public final class MainActivity extends AppCompatActivity implements
  IStaticReduxPropContainer<State> {
  private StaticProps<State> staticProps;
  private FragmentManager.FragmentLifecycleCallbacks fragmentCallback;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (savedInstanceState == null) {
      this.getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.fragment, new MainFragment())
        .commit();
    }

    this.fragmentCallback = startFragmentInjection(this,
      new Function2<StaticProps<State>, Fragment, Unit>() {
        @Override
        public Unit invoke(StaticProps<State> staticProps, Fragment fragment) {
          if (fragment instanceof SearchFragment) {
            SearchFragment f = (SearchFragment)fragment;
            injectLifecycleProps(staticProps.getInjector(), f, Unit.INSTANCE, f);
          } else if (fragment instanceof MusicDetailFragment) {
            MusicDetailFragment f = (MusicDetailFragment)fragment;
            injectLifecycleProps(staticProps.getInjector(), f, Unit.INSTANCE, f);
          }

          return Unit.INSTANCE;
        }
      });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    endFragmentInjection(this, this.fragmentCallback);
  }

  @NotNull
  @Override
  public StaticProps<State> getStaticProps() {
    return this.staticProps;
  }

  @Override
  public void setStaticProps(@NotNull StaticProps<State> staticProps) {
    this.staticProps = staticProps;
  }
}