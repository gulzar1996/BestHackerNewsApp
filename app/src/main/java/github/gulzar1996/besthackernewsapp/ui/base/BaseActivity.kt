package github.gulzar1996.besthackernewsapp.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection

abstract class BaseActivity : AppCompatActivity(), IBaseView, BaseFragment.CallBack {


    override fun onCreate(savedInstanceState: Bundle?) {
        setupDI()
        super.onCreate(savedInstanceState)
    }

    private fun setupDI() = AndroidInjection.inject(this)
}