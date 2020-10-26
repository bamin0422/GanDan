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
import h2mud2.ganpanproject.gandan.adapter.Banner

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
            Toast.makeText(view.context, "배너가 클릭되었습니다.", Toast.LENGTH_SHORT).show();
        }

        steelBanner_btn.setOnClickListener{
            Toast.makeText(view.context, "스틸배너가 클릭되었습니다.", Toast.LENGTH_SHORT).show();
        }

        hanging_btn.setOnClickListener{
            Toast.makeText(view.context, "현수막이 클릭되었습니다.", Toast.LENGTH_SHORT).show();
        }

        designItem_btn.setOnClickListener{
            Toast.makeText(view.context, "디자인상품이 클릭되었습니다.", Toast.LENGTH_SHORT).show();
        }


        return view
    }
}