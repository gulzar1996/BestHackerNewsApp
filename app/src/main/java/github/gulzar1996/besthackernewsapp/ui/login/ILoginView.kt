package github.gulzar1996.besthackernewsapp.ui.login

import android.app.Activity
import android.content.Context
import github.gulzar1996.besthackernewsapp.ui.base.IBaseView

interface ILoginView : IBaseView {

    fun showSignInDialog()

    fun showProgress()

    fun hideProgress()

    fun showToast(str: String)

    fun navigateToHomeActivty()

    fun context(): Activity

}
