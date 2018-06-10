package github.gulzar1996.besthackernewsapp.ui.detail

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import github.gulzar1996.besthackernewsapp.ui.base.BasePresenter
import github.gulzar1996.besthackernewsapp.ui.login.ILoginView
import github.gulzar1996.besthackernewsapp.ui.login.IloginInteractor
import github.gulzar1996.besthackernewsapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoginPresenter<V : ILoginView, I : IloginInteractor>
@Inject
constructor(schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable, interactor: I)
    : BasePresenter<V, I>(schedulerProvider, compositeDisposable, interactor),
        ILoginPresenter<V, I> {


    override fun firebaseAuthWithGoogle(acct: GoogleSignInAccount, auth: FirebaseAuth?) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        getView.showProgress()
        auth?.signInWithCredential(credential)
                ?.addOnCompleteListener(getView.context(), {
                    getView.hideProgress()
                    when (it.isSuccessful) {
                        false -> getView.showToast("FAILED")
                        true -> getView.navigateToHomeActivty()
                    }
                })
    }

    override fun onSignInButtonClick() {
        getView.showSignInDialog()
    }

}