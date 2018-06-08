package github.gulzar1996.besthackernewsapp.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import github.gulzar1996.besthackernewsapp.base.IBaseView

open class BaseActivity : AppCompatActivity(), IBaseView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDI()
    }

    override fun showProgress() {

    }

    override fun hideProgress() {
    }

    private fun setupDI() = AndroidInjection.inject(this)
}