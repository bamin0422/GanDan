package h2mud2.ganpanproject.gandan.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appViewPager.adapter = MainPagerAdapter(supportFragmentManager)
        appViewPager.offscreenPageLimit = 4


        bottomNavigationMenu.menu.getItem(2).isChecked = true
        appViewPager.currentItem = 2

        appViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                bottomNavigationMenu.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        bottomNavigationMenu.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.main_menu -> appViewPager.currentItem = 0
                R.id.search -> appViewPager.currentItem = 1
                R.id.home -> appViewPager.currentItem = 2
                R.id.account -> appViewPager.currentItem = 3
                R.id.cart -> appViewPager.currentItem = 4
            }
            true
        }

    }
}

class MainPagerAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getCount() = 5

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> menu_fragment()
            1 -> search_fragment()
            2 -> home_fragment()
            3 -> account_fragment()
            else -> cart_fragment()
        }

    }

}