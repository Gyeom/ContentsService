package kr.co.ldcc.contentsservice.adpater

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kr.co.ldcc.contentsservice.fragment.BookmarkFragment
import kr.co.ldcc.contentsservice.fragment.MainFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, tabCount: Int) : FragmentStatePagerAdapter(fragmentManager,  BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
   private var tabCount: Int? = null

    init {
        this.tabCount = tabCount
    }

    override fun getItem(position: Int): Fragment {

        val fragment = when(position)
        {
            1 -> BookmarkFragment.newInstance()
            else -> MainFragment.newInstance() // default 0
        }
        Log.d("test",position.toString())
        return fragment
    }

    override fun getCount(): Int {
        return this.tabCount?:0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val title = when(position) {
            1 -> "북마크"
            else -> "전체보기" // default 0
        }
        return title
    }
}