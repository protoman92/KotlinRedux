/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.ui.core

import android.text.Editable
import android.text.TextWatcher

/** Created by haipham on 2019/01/12 */
/**
 * This [TextWatcher] implementation calls on another [listener] methods only when the underlying
 * values change. We do not need to worry about thread-safe access because these methods will be
 * called on the main thread anyway.
 */
class DistinctTextWatcher(private val listener: TextWatcher) : TextWatcher {
  internal class BeforeTextChanged(
    private var s: CharSequence?,
    private var start: Int,
    private var count: Int,
    private var after: Int
  ) {
    fun isDifferent(s: CharSequence?, start: Int, count: Int, after: Int): Boolean {
      return !(this.s == s && this.start == start && this.count == count && this.after == after)
    }

    fun update(s: CharSequence?, start: Int, count: Int, after: Int) {
      this.s = s; this.start = start; this.count = count; this.after = after
    }
  }

  internal class OnTextChanged(
    private var s: CharSequence?,
    private var start: Int,
    private var before: Int,
    private var count: Int
  ) {
    fun isDifferent(s: CharSequence?, start: Int, before: Int, count: Int): Boolean {
      return !(this.s == s && this.start == start && this.before == before && this.count == count)
    }

    fun update(s: CharSequence?, start: Int, before: Int, count: Int) {
      this.s = s; this.start = start; this.before = before; this.count = count
    }
  }

  private var beforeTextChanged = BeforeTextChanged("", 0, 0, 0)
  private var onTextChanged = OnTextChanged("", 0, 0, 0)
  private var afterTextChanged: String? = null

  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    if (this.beforeTextChanged.isDifferent(s, start, count, after)) {
      this.beforeTextChanged.update(s, start, count, after)
      this.listener.beforeTextChanged(s, start, count, after)
    }
  }

  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    if (this.onTextChanged.isDifferent(s, start, before, count)) {
      this.onTextChanged.update(s, start, before, count)
      this.listener.onTextChanged(s, start, before, count)
    }
  }

  override fun afterTextChanged(s: Editable?) {
    if (s?.toString() != this.afterTextChanged) {
      this.afterTextChanged = s?.toString()
      this.listener.afterTextChanged(s)
    }
  }
}
