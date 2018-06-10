package github.gulzar1996.besthackernewsapp.ui.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragmentLists = ArrayList<Fragment>()
    private val fragmentTitles = ArrayList<String>()
    private val data = ArrayList<Bundle>()

    fun addContainer(fragment: Fragment, title: String, bundle: Bundle) {
        fragmentLists.add(fragment)
        fragmentTitles.add(title)
        data.add(bundle)
    }

    /**
     * Passing postID to every fragment on the view pager
     */
    override fun getItem(position: Int): Fragment {
        val fg = fragmentLists[position]
        val bundle = data[position]
        fg.arguments = bundle
        return fg

    }

    override fun getCount(): Int = fragmentLists.size

    override fun getPageTitle(position: Int): CharSequence? = fragmentTitles[position]


}