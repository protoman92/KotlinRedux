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
import org.swiften.redux.ui.IPropContainer
import org.swiften.redux.ui.IPropMapper
import org.swiften.redux.ui.ObservableReduxProp
import org.swiften.redux.ui.StaticProp

/** Created by haipham on 2019/01/12 */
class MusicDetailFragment : Fragment(),
  IPropContainer<MainRedux.State, Unit, MusicDetailFragment.S, MusicDetailFragment.A> {
  class S(val track: MusicTrack?)
  class A(val previewTrack: () -> Unit)

  companion object : IPropMapper<MainRedux.State, Unit, S, A> {
    override fun mapAction(dispatch: IActionDispatcher, outProp: Unit): A {
      return A { dispatch(MainRedux.ThunkAction.PreviewTrack) }
    }

    override fun mapState(state: MainRedux.State, outProp: Unit) = S(state.currentSelectedTrack())
  }

  override var reduxProp by ObservableReduxProp<S, A> { _, next ->
    next.state?.track?.also {
      this.trackName.text = it.trackName
      this.artistName.text = it.artistName
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_music_detail, container, false)

  override fun beforePropInjectionStarts(sp: StaticProp<MainRedux.State, Unit>) {
    this.trackOpen.setOnClickListener {
      this.reduxProp.action?.also { a -> a.previewTrack() }
    }
  }
}
