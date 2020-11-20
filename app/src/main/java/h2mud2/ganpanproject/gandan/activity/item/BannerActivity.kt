package h2mud2.ganpanproject.gandan.activity.item

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.activity.tool.DesignToolActivity
import h2mud2.ganpanproject.gandan.adapter.GridAdapter
import h2mud2.ganpanproject.gandan.adapter.HorizonAdapter
import h2mud2.ganpanproject.gandan.crawler.WebCrawler
import h2mud2.ganpanproject.gandan.decoration.RecyclerViewDecoration
import h2mud2.ganpanproject.gandan.fragment.FireStoreManager
import h2mud2.ganpanproject.gandan.model.Item
import kotlinx.coroutines.*

class BannerActivity : AppCompatActivity(){

    lateinit var backBtn: Button
    lateinit var bannerRecyclerView : RecyclerView
    lateinit var itemAdapter : GridAdapter
    lateinit var itemClicked : LinearLayout
    lateinit var scrollView : ScrollView
    lateinit var designBtn : Button

    var bannerList : ArrayList<Item> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_banner)
        itemClicked = findViewById(R.id.itemClicked)
        scrollView = findViewById(R.id.scrollView3)
        backBtn = findViewById(R.id.back_btn)

        designBtn = findViewById(R.id.design_btn)

        designBtn.setOnClickListener {
            val intent = Intent(applicationContext, DesignToolActivity::class.java)
            startActivity(intent)
        }

        bannerRecyclerView = findViewById(R.id.bannerItem)

        CoroutineScope(Dispatchers.Default).launch {
            val bannerItemSize = async{FireStoreManager().size("banneritem")}

            if(bannerItemSize.await() == 0) {
                // banner 상품 크롤링
                WebCrawler().bannerItemCrawler()
            }
            withContext(Dispatchers.IO){
                val operation = async(Dispatchers.IO){
                    bannerList = FireStoreManager().addListItem(bannerList, "banneritem")
                }
                operation.await()

                withContext(Dispatchers.Main){
                    itemAdapter = GridAdapter(applicationContext, bannerList){
                        itemClicked.visibility = View.VISIBLE
                    }
                    bannerRecyclerView.adapter = itemAdapter
                    bannerRecyclerView.addItemDecoration(RecyclerViewDecoration())
                    bannerRecyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
                    return@withContext
                }
            }
        }

        scrollView.setOnScrollChangeListener(object : View.OnScrollChangeListener{
            override fun onScrollChange(
                v: View?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                itemClicked.visibility =View.GONE
            }

        })

        backBtn.setOnClickListener {
            finish()
        }
    }
}