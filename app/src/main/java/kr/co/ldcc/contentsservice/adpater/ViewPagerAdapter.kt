package kr.co.ldcc.contentsservice.adpater

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kr.co.ldcc.contentsservice.fragment.BookmarkFragment
import kr.co.ldcc.contentsservice.fragment.MainFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, tabCount: Int) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val tabCount: Int

    init {
        this.tabCount = tabCount
    }

    override fun getItem(position: Int): Fragment {

        val fragment = when (position) {
            1 -> BookmarkFragment.newInstance()
            else -> MainFragment.newInstance() // default 0
        }
        return fragment
    }

    override fun getCount(): Int {
        return this.tabCount
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val title = when (position) {
            1 -> "북마크"
            else -> "전체보기" // default 0
        }
        return title
    }
}