package github.gulzar1996.besthackernewsapp.ui.detail

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import github.gulzar1996.besthackernewsapp.ui.base.IBasePresenter
import github.gulzar1996.besthackernewsapp.ui.login.ILoginView
import github.gulzar1996.besthackernewsapp.ui.login.IloginInteractor

interface ILoginPresenter<V : ILoginView, I : IloginInteractor> : IBasePresenter<V, I> {

    fun onSignInButtonClick()

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount, auth: FirebaseAuth?)

}