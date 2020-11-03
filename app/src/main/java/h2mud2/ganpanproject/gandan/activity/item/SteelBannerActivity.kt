package h2mud2.ganpanproject.gandan.activity.item

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.adapter.GridAdapter
import h2mud2.ganpanproject.gandan.adapter.HorizonAdapter
import h2mud2.ganpanproject.gandan.crawler.WebCrawler
import h2mud2.ganpanproject.gandan.decoration.RecyclerViewDecoration
import h2mud2.ganpanproject.gandan.fragment.FireStoreManager
import h2mud2.ganpanproject.gandan.model.Item
import kotlinx.coroutines.*
import java.util.ArrayList

class SteelBannerActivity : AppCompatActivity(){

    lateinit var backBtn: Button
    lateinit var steelBannerRecyclerView : RecyclerView
    lateinit var itemAdapter : GridAdapter
    var steelItemList : ArrayList<Item> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_steelbanner)

        backBtn = findViewById(R.id.back_btn)

        steelBannerRecyclerView = findViewById(R.id.steelBannerItem)
        CoroutineScope(Dispatchers.Default).launch {
            val steelItemSize = async{ FireStoreManager().size("steelitem")}

            if(steelItemSize.await() == 0) {
                // design 상품 크롤링
                WebCrawler().SteelItemCrawler()
            }

            withContext(Dispatchers.IO){
                val operation = async(Dispatchers.IO){
                    steelItemList = FireStoreManager().addListItem(steelItemList, "steelitem")
                }
                operation.await()

                withContext(Dispatchers.Main){
                    itemAdapter = GridAdapter(applicationContext, steelItemList)
                    steelBannerRecyclerView.adapter = itemAdapter
                    steelBannerRecyclerView.addItemDecoration(RecyclerViewDecoration())
                    steelBannerRecyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
                    return@withContext
                }
            }
        }

        backBtn.setOnClickListener {
            finish()
        }

    }
}