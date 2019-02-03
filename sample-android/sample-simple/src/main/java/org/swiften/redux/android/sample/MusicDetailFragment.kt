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
import kotlinx.android.synthetic.main.fragment_music_detail.artistName
import kotlinx.android.synthetic.main.fragment_music_detail.trackName
import kotlinx.android.synthetic.main.fragment_music_detail.trackOpen
import org.swiften.redux.core.IActionDispatcher
import org.swiften.redux.ui.EmptyPropLifecycleOwner
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropLifecycleOwner
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.ObservableReduxProps
import org.swiften.redux.ui.StaticProps

/** Created by haipham on 2019/01/12 */
class MusicDetailFragment : Fragment(),
  IPropContainer<State, Unit, MusicDetailFragment.S, MusicDetailFragment.A>,
  IPropLifecycleOwner<State, Unit> by EmptyPropLifecycleOwner(),
  IPropMapper<State, Unit, Unit, MusicDetailFragment.S, MusicDetailFragment.A> by MusicDetailFragment {
  class S(val track: MusicTrack?)
  class A(val goToTrackInformation: () -> Unit)

  companion object : IPropMapper<State, Unit, Unit, S, A> {
    override fun mapAction(dispatch: IActionDispatcher, state: State, ext: Unit, outProps: Unit): A {
      return A {
        this.mapState(state, outProps).track?.also {
          dispatch(MainRedux.Screen.WebView(it.previewUrl))
        }
      }
    }

    override fun mapState(state: State, outProps: Unit) = S(state.currentSelectedTrack())
  }

  override var reduxProps by ObservableReduxProps<State, Unit, S, A> { _, next ->
    next?.state?.track?.also {
      this.trackName.text = it.trackName
      this.artistName.text = it.artistName
    }
  }

  private val trackOpenListener = View.OnClickListener {
    this.reduxProps.v?.action?.also { a -> a.goToTrackInformation() }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_music_detail, container, false)

  override fun beforePropInjectionStarts(sp: StaticProps<State, Unit>) {
    this.trackOpen.setOnClickListener(this.trackOpenListener)
  }
}
