/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.jk.hscardfactory.base

import android.support.annotation.StringRes

interface BaseView {

    val isNetworkConnected: Boolean
    fun onError(@StringRes resId: Int)

    fun showLoading()

    fun hideLoading()

    fun onError(message: String?)

    fun openActivityOnTokenExpire()

    fun showMessage(message: String)

    fun showMessage(@StringRes resId: Int)

    fun hideKeyboard()
}