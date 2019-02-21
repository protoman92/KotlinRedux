/*
 * Copyright (c) haipham 2019. All rights reserved.
 * Any attempt to reproduce this source code in any form shall be met with legal actions.
 */

package org.swiften.redux.android.dagger.business1

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.R
import androidx.appcompat.widget.AppCompatEditText

/** Created by viethai.pham on 2019/02/22 */
class SearchView1 @JvmOverloads constructor (
  context: Context,
  attrs: AttributeSet? = null,
  style: Int = R.attr.editTextStyle
) : AppCompatEditText(context, attrs, style)
