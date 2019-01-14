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
import org.swiften.redux.core.IReduxDispatcher
import org.swiften.redux.ui.IReduxPropContainer
import org.swiften.redux.ui.IReduxPropMapper
import org.swiften.redux.ui.StaticProps
import org.swiften.redux.ui.VariableProps
import kotlin.properties.Delegates

/** Created by haipham on 2019/01/12 */
class MusicDetailFragment : Fragment(),
  IReduxPropContainer<State, MusicDetailFragment.S, MusicDetailFragment.A>,
  IReduxPropMapper<State, Unit, MusicDetailFragment.S, MusicDetailFragment.A> by MusicDetailFragment {
  class S(val track: MusicTrack?)
  class A(val goToTrackInformation: () -> Unit)

  companion object : IReduxPropMapper<State, Unit, S, A> {
    override fun mapAction(dispatch: IReduxDispatcher, state: State, outProps: Unit) = A {
      this.mapState(state, outProps).track?.also {
        dispatch(MainRedux.Screen.WebView(it.previewUrl))
      }
    }

    override fun mapState(state: State, outProps: Unit) = S(state.currentSelectedTrack())
  }

  override lateinit var staticProps: StaticProps<State>

  override var variableProps
    by Delegates.observable<VariableProps<S, A>?>(null) { _, _, p ->
      p?.next?.track?.also {
        this.trackName.text = it.trackName
        this.artistName.text = it.artistName
      }
    }

  private val trackOpenListener: View.OnClickListener by lazy {
    View.OnClickListener { this.variableProps?.actions?.also { a -> a.goToTrackInformation() } }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_music_detail, container, false)

  override fun beforePropInjectionStarts() {
    this.trackOpen.setOnClickListener(this.trackOpenListener)
  }

  override fun afterPropInjectionEnds() {
    this.trackOpen.setOnClickListener(null)
  }
}
