package github.gulzar1996.besthackernewsapp.ui.detail.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import github.gulzar1996.besthackernewsapp.Const
import github.gulzar1996.besthackernewsapp.R
import github.gulzar1996.besthackernewsapp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_webiew.*
import javax.inject.Inject

class WebFragment : BaseFragment(), IWebFragmentView {


    @Inject
    internal lateinit var presenter: IWebFragmentPresenter<IWebFragmentView, IWebFragmentInteractor>

    var webV: WebView? = null

    private var postId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_webiew, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webV = this.webview
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        bundle.let {
            postId = it?.get(Const.POST_ID) as Int
        }
        presenter.onAttach(this)
        postId?.let { presenter.loadUrl(it) }

    }

    override fun loadUrl(url: String) {
        webV?.loadUrl(url)
    }
}