package github.gulzar1996.besthackernewsapp.ui.detail.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import github.gulzar1996.besthackernewsapp.R
import github.gulzar1996.besthackernewsapp.ui.base.BaseFragment
import github.gulzar1996.besthackernewsapp.ui.base.IBaseInteractor
import github.gulzar1996.besthackernewsapp.ui.base.IBaseView
import javax.inject.Inject

class WebFragment : BaseFragment(), IWebFragmentView {

    @Inject
    internal lateinit var presenter: IWebFragmentPresenter<IWebFragmentView, IWebFragmentInteractor>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_webiew, container, false)
    }
}