/*
 * Copyright (c) haipham 2018. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.swiften.redux.core.IReduxAction;
import org.swiften.redux.ui.IReduxPropContainer;
import org.swiften.redux.ui.IReduxPropMapper;
import org.swiften.redux.ui.StaticProps;
import org.swiften.redux.ui.VariableProps;

import static org.swiften.redux.android.ui.core.AndroidReduxKt.endFragmentInjection;
import static org.swiften.redux.android.ui.core.AndroidReduxKt.injectLifecycleProps;
import static org.swiften.redux.android.ui.core.AndroidReduxKt.startFragmentInjection;

/** Created by haipham on 2018/12/19 */
class MainActivity extends AppCompatActivity implements
  IReduxPropContainer<State, Unit, MainActivity.A>,
  IReduxPropMapper<State, Unit, Unit, MainActivity.A> {
  static class A {
    @NonNull Function0<Unit> goToMainScreen;

    A(@NonNull Function0<Unit> goToMainScreen) {
      this.goToMainScreen = goToMainScreen;
    }
  }

  enum Mapper implements IReduxPropMapper<State, Unit, Unit, A> {
    INSTANCE;

    public Unit mapState(State state, Unit outProps) {
      return Unit.INSTANCE;
    }

    public A mapAction(
      @NonNull final Function1<? super IReduxAction, Unit> dispatch,
      State state,
      Unit outProps
    ) {
      return new A(new Function0<Unit>() {
        @Override
        public Unit invoke() {
          return dispatch.invoke(MainRedux.Screen.MainScreen.INSTANCE);
        }
      });
    }
  }

  private StaticProps<State> staticProps;
  private FragmentManager.FragmentLifecycleCallbacks fragmentCallback;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
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

  @Nullable
  @Override
  public VariableProps<Unit, A> getVariableProps() {
    return null;
  }

  @Override
  public void setVariableProps(@Nullable VariableProps<Unit, A> variableProps) {
    if (variableProps != null) {
      variableProps.getActions().goToMainScreen.invoke();
    }
  }

  @Override
  public void beforePropInjectionStarts() {
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
  public void afterPropInjectionEnds() {
    endFragmentInjection(this, this.fragmentCallback);
  }

  @Override
  public Unit mapState(State state, Unit unit) {
    return Mapper.INSTANCE.mapState(state, unit);
  }

  @Override
  public A mapAction(
    @NotNull Function1<? super IReduxAction, Unit> dispatch,
    State state,
    Unit unit
  ) {
    return Mapper.INSTANCE.mapAction(dispatch, state, unit);
  }
}
