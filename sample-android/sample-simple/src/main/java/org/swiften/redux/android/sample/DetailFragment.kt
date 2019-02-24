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
import kotlinx.android.synthetic.main.detail_fragment.artistName
import kotlinx.android.synthetic.main.detail_fragment.trackName
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.core.IUniqueIDProvider
import org.swiften.redux.core.DefaultUniqueIDProvider
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.NoopPropLifecycleOwner
import org.swiften.redux.ui.ObservableReduxProp
import java.io.Serializable

/** Created by haipham on 27/1/19 */
class DetailFragment : Fragment(),
  IUniqueIDProvider by DefaultUniqueIDProvider(),
  IPropContainer<DetailFragment.S, Unit>,
  IPropLifecycleOwner<DetailFragment.ILocalState, Unit> by NoopPropLifecycleOwner() {
  companion object : IPropMapper<ILocalState, Unit, DetailFragment.S, Unit> {
    override fun mapState(state: ILocalState, outProp: Unit): S {
      return S(state.selectedTrack?.let { i -> state.musicResult?.results?.elementAtOrNull(i) })
    }

    override fun mapAction(dispatch: IActionDispatcher, outProp: Unit) = Unit
  }

  interface ILocalState : IMusicResultProvider, ISelectedTrackProvider

  data class S(val track: MusicTrack?) : Serializable

  override var reduxProp by ObservableReduxProp<S, Unit> { _, next ->
    next.state.track?.also {
      this.trackName.text = it.trackName
      this.artistName.text = it.artistName
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.detail_fragment, container, false)
}
