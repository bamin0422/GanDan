package h2mud2.ganpanproject.gandan.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.activity.item.BannerActivity
import h2mud2.ganpanproject.gandan.activity.item.DesignItemActivity
import h2mud2.ganpanproject.gandan.activity.item.HangingActivity
import h2mud2.ganpanproject.gandan.activity.item.SteelBannerActivity
import h2mud2.ganpanproject.gandan.adapter.Banner
import java.util.*

class search_fragment: Fragment() {

    lateinit var banner_btn : Button
    lateinit var steelBanner_btn: Button
    lateinit var hanging_btn : Button
    lateinit var designItem_btn: Button
    lateinit var search_item: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.search_fragment, container, false)

        banner_btn = view.findViewById<Button>(R.id.banner)
        steelBanner_btn = view.findViewById<Button>(R.id.steelbanner)
        hanging_btn = view.findViewById<Button>(R.id.hanging)
        designItem_btn = view.findViewById<Button>(R.id.designItem)

        banner_btn.setOnClickListener{
            activity?.let{
                var intent = Intent(view.context, BannerActivity::class.java)
                startActivity(intent)
            }
        }

        steelBanner_btn.setOnClickListener{
            activity?.let{
                var intent = Intent(view.context, SteelBannerActivity::class.java)
                startActivity(intent)
            }
        }

        hanging_btn.setOnClickListener{
            activity?.let{
                var intent = Intent(view.context, HangingActivity::class.java)
                startActivity(intent)
            }
        }

        designItem_btn.setOnClickListener{
            activity?.let{
                var intent = Intent(view.context, DesignItemActivity::class.java)
                startActivity(intent)
            }
        }


        return view
    }
}