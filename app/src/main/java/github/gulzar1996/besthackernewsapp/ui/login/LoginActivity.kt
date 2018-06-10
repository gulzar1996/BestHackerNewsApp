package github.gulzar1996.besthackernewsapp.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.view.RxView
import github.gulzar1996.besthackernewsapp.R
import github.gulzar1996.besthackernewsapp.ui.base.BaseActivity
import github.gulzar1996.besthackernewsapp.ui.detail.LoginPresenter
import github.gulzar1996.besthackernewsapp.ui.home.HomeActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject


class LoginActivity : BaseActivity(), ILoginView {


    @Inject
    lateinit var loginPresenter: LoginPresenter<ILoginView, IloginInteractor>

    lateinit var gso: GoogleSignInOptions
    var googleSignInClient: GoogleSignInClient? = null
    var auth: FirebaseAuth? = null

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPresenter.onAttach(this)
        initFirebaseAuth()
        initGoogleClient()
        initClickListeners()
    }

    override fun onStart() {
        super.onStart()
        if (auth?.currentUser != null)
            navigateToHomeActivty()
    }

    private fun initFirebaseAuth() {
        auth = FirebaseAuth.getInstance()
    }

    private fun initClickListeners() {
        compositeDisposable.add(Observable.merge(RxView.clicks(googleSignFakeLayout)
                , RxView.clicks(googleSignFakeButton))
                .subscribe({ loginPresenter.onSignInButtonClick() }))
    }

    override fun showSignInDialog() {
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    private fun initGoogleClient() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    loginPresenter.firebaseAuthWithGoogle(account, auth)
                } catch (e: ApiException) {
                    showToast("FAILED")
                }
            }
            else -> showToast("FAILED")
        }
    }

    override fun showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()


    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun context(): Activity = this

    override fun navigateToHomeActivty() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun onFragmentDetached(tag: String) {}

    override fun onFragmentAttached() {}
}