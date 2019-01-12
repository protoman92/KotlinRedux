/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_music_detail.trackName
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.IReduxStatePropMapper
import org.swiften.redux.ui.StaticProps
import org.swiften.redux.ui.VariableProps
import kotlin.properties.Delegates

/** Created by haipham on 2019/01/12 */
class MusicDetailFragment : Fragment(),
  IReduxPropContainer<State, MusicDetailFragment.S, Unit>,
  IReduxStatePropMapper<State, Unit, MusicDetailFragment.S> by MusicDetailFragment {
  class S(val track: MusicTrack?)

  companion object : IReduxStatePropMapper<State, Unit, S> {
    override fun mapState(state: State, outProps: Unit) = S(state.currentSelectedTrack())
  }

  override lateinit var staticProps: StaticProps<State>

  override var variableProps
    by Delegates.observable<VariableProps<S, Unit>?>(null) { _, _, p ->
      p?.next?.track?.also { this.trackName.text = it.trackName }
    }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_music_detail, container, false)

  override fun beforePropInjectionStarts() {}

  override fun afterPropInjectionEnds() {}
}
