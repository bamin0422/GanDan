package h2mud2.ganpanproject.gandan.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import h2mud2.ganpanproject.gandan.R
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
            Toast.makeText(context, "${banner.menuName}을 클릭하였습니다.", Toast.LENGTH_SHORT).show()
        }
        view.menuList.adapter = adapter

        val lm = LinearLayoutManager(view.context)
        view.menuList.layoutManager = lm
        view.menuList.addItemDecoration(DividerItemDecoration(view.context, LinearLayoutManager.VERTICAL))
        view.menuList.setHasFixedSize(true)

        return view
    }
}