package h2mud2.ganpanproject.gandan.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.activity.item.BannerActivity
import h2mud2.ganpanproject.gandan.activity.item.DesignItemActivity
import h2mud2.ganpanproject.gandan.activity.item.HangingActivity
import h2mud2.ganpanproject.gandan.activity.item.SteelBannerActivity
import h2mud2.ganpanproject.gandan.adapter.Banner
import h2mud2.ganpanproject.gandan.adapter.MenuAdapter
import kotlinx.android.synthetic.main.menu_fragment.*
import kotlinx.android.synthetic.main.menu_fragment.view.*

class menu_fragment: Fragment() {


    var menus = arrayListOf<Banner>(
        Banner("배너", "banner"),
        Banner("스틸 배너", "steelbanner"),
        Banner("현수막", "hangingbanner"),
        Banner("디자인 상품", "design"),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.menu_fragment, container, false)

        val adapter = MenuAdapter(view.context, menus){banner ->
            when(banner.menuName){
                "배너" -> {
                    activity?.let{
                        val intent = Intent(context, BannerActivity::class.java)
                        startActivity(intent)
                    }
                }
                "스틸 배너" -> {
                    activity?.let{
                        val intent = Intent(context, SteelBannerActivity::class.java)
                        startActivity(intent)
                    }
                }
                "현수막" -> {
                    activity?.let{
                        val intent = Intent(context, HangingActivity::class.java)
                        startActivity(intent)
                    }
                }
                "디자인 상품" -> {
                    activity?.let{
                        val intent = Intent(context, DesignItemActivity::class.java)
                        startActivity(intent)
                    }
                }

            }
        }


        view.menuList.adapter = adapter


        val lm = LinearLayoutManager(view.context)
        view.menuList.layoutManager = lm
        view.menuList.addItemDecoration(DividerItemDecoration(view.context, LinearLayoutManager.VERTICAL))
        view.menuList.setHasFixedSize(true)

        return view
    }
}