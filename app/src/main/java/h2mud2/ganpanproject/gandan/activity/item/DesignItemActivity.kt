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

class DesignItemActivity : AppCompatActivity(){

    lateinit var backBtn: Button
    lateinit var designItemRecyclerView : RecyclerView
    lateinit var itemAdapter : GridAdapter
    var designItemList : ArrayList<Item> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_designitem)

        backBtn = findViewById(R.id.back_btn)

        designItemRecyclerView = findViewById(R.id.designItemRecycler)
        CoroutineScope(Dispatchers.Default).launch {
            val designItemSize = async{ FireStoreManager().size("designitem")}

            if(designItemSize.await() == 0) {
                // design 상품 크롤링
                WebCrawler().DesignItemCrawler()
            }
            withContext(Dispatchers.IO){
                val operation = async(Dispatchers.IO){
                    designItemList = FireStoreManager().addListItem(designItemList, "designitem")
                }
                operation.await()

                withContext(Dispatchers.Main){
                    itemAdapter = GridAdapter(applicationContext, designItemList)
                    designItemRecyclerView.adapter = itemAdapter
                    designItemRecyclerView.addItemDecoration(RecyclerViewDecoration())
                    designItemRecyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
                    return@withContext
                }
            }
        }


        backBtn.setOnClickListener {
            finish()
        }

    }
}