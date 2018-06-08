package github.gulzar1996.wheelstreetc.ui.base

import github.gulzar1996.besthackernewsapp.base.IBaseInteractor
import github.gulzar1996.besthackernewsapp.base.IBaseView

interface IBasePresenter<V : IBaseView, I : IBaseInteractor> {

    fun onAttach(baseView: V)

    fun onDetach()
}