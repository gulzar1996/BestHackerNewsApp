package github.gulzar1996.besthackernewsapp.ui.detail.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import github.gulzar1996.besthackernewsapp.R
import github.gulzar1996.besthackernewsapp.ui.base.BaseFragment
import github.gulzar1996.besthackernewsapp.ui.base.IBaseView

class CommentFragment() : BaseFragment(), IBaseView {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comment, container, false)
    }
}