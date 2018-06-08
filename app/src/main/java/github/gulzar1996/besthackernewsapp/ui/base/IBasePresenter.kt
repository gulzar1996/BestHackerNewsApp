package github.gulzar1996.besthackernewsapp.ui.base

interface IBasePresenter<V : IBaseView, I : IBaseInteractor> {

    fun onAttach(baseView: V)

    fun onDetach()
}