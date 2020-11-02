package h2mud2.ganpanproject.gandan.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.R.*
import h2mud2.ganpanproject.gandan.activity.item.BannerActivity
import h2mud2.ganpanproject.gandan.activity.item.DesignItemActivity
import h2mud2.ganpanproject.gandan.activity.item.HangingActivity
import h2mud2.ganpanproject.gandan.activity.item.SteelBannerActivity
import h2mud2.ganpanproject.gandan.adapter.HorizonAdapter
import h2mud2.ganpanproject.gandan.crawler.WebCrawler
import h2mud2.ganpanproject.gandan.model.Item
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import okio.Utf8.size
import org.jetbrains.anko.Orientation
import org.jetbrains.anko.doAsync

class home_fragment: Fragment() {

    var sampleImages = arrayOf(drawable.carousel1, drawable.carousel2, drawable.carousel3, drawable.carousel4)
    lateinit var bannerBtn : Button
    lateinit var steelBannerBtn : Button
    lateinit var hangingBtn : Button
    lateinit var designItemBtn : Button

    lateinit var bestItemAdapter : HorizonAdapter
    lateinit var newItemAdapter : HorizonAdapter
    lateinit var recommendedItemAdapter : HorizonAdapter

    val webCrawler = WebCrawler()
    var bestItemList : ArrayList<Item> = arrayListOf()
    var newItemList : ArrayList<Item> = arrayListOf()
    var recommendedItemList : ArrayList<Item> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(layout.home_fragment, container, false)
        var carouselView = view.findViewById(R.id.item_carousel) as CarouselView
        var bestItemGridView = view.findViewById<RecyclerView>(R.id.best_item)
        var newItemGridView = view.findViewById<RecyclerView>(R.id.new_item)
        var recommendedItemGridView = view.findViewById<RecyclerView>(R.id.recommended_item)

        var bestlm = LinearLayoutManager(view.context)
        bestlm.orientation = LinearLayoutManager.HORIZONTAL

        var newlm = LinearLayoutManager(view.context)
        newlm.orientation = LinearLayoutManager.HORIZONTAL

        var recommendedlm = LinearLayoutManager(view.context)
        recommendedlm.orientation = LinearLayoutManager.HORIZONTAL

        carouselView.pageCount = sampleImages.size
        carouselView.setImageListener(imageListener)

        bannerBtn = view.findViewById(R.id.bannerButton)
        steelBannerBtn = view.findViewById(R.id.steelBannerButton)
        hangingBtn = view.findViewById(R.id.hangingButton)
        designItemBtn = view.findViewById(R.id.designItemButton)

        bannerBtn.setOnClickListener {
            activity?.let{
                var intent = Intent(view.context, BannerActivity::class.java)
                startActivity(intent)
            }
        }

        steelBannerBtn.setOnClickListener {
            activity?.let{
                var intent = Intent(view.context, SteelBannerActivity::class.java)
                startActivity(intent)
            }
        }

        hangingBtn.setOnClickListener {
            activity?.let{
                var intent = Intent(view.context, HangingActivity::class.java)
                startActivity(intent)
            }
        }

        designItemBtn.setOnClickListener {
            activity?.let{
                var intent = Intent(view.context, DesignItemActivity::class.java)
                startActivity(intent)
            }
        }

        bestItemList.clear()
        newItemList.clear()
        recommendedItemList.clear()

        CoroutineScope(Dispatchers.Default).launch {
            val bestItemSize = async{FireStoreManager().size("bestitem")}
            val newItemSize = async{FireStoreManager().size("newitem")}
            val recommendedItemSize = async{FireStoreManager().size("recommendeditem")}


            if(bestItemSize.await() == 0 || newItemSize.await() == 0 || recommendedItemSize.await() == 0) {

                // best 상품 크롤링, firestore 등록, gridView 뿌려주기
                webCrawler.bestItemCrawler()

                // 신상품 크롤링, firestore 등록, gridView 뿌려주기
                webCrawler.newItemCrawler()

                // 추천상품 크롤링, firestore 등록, gridView 뿌려주기
                webCrawler.recommendedItemCrawler()

            }
        }

        CoroutineScope(Dispatchers.IO).launch {

            val operation = async(Dispatchers.IO){
                bestItemList = FireStoreManager().addListItem(bestItemList, "bestitem")
                newItemList = FireStoreManager().addListItem(newItemList, "newitem")
                recommendedItemList = FireStoreManager().addListItem(recommendedItemList, "recommendeditem")
            }
            operation.await()

            withContext(Dispatchers.Main){
                bestItemAdapter = HorizonAdapter(view.context, bestItemList)
                newItemAdapter = HorizonAdapter(view.context, newItemList)
                recommendedItemAdapter = HorizonAdapter(view.context, recommendedItemList)

                bestItemGridView.adapter = bestItemAdapter
                newItemGridView.adapter = newItemAdapter
                recommendedItemGridView.adapter = recommendedItemAdapter

                bestItemGridView.layoutManager = bestlm
                newItemGridView.layoutManager = newlm
                recommendedItemGridView.layoutManager = recommendedlm
                return@withContext
            }
        }
        return view
    }

    var imageListener = ImageListener(){ i: Int, imageView: ImageView ->
        imageView.setImageResource((sampleImages[i]))
    }
}

class FireStoreManager {
    val db = FirebaseFirestore.getInstance()

    suspend fun size(str : String): Int {
        return db.collection(str).get().await().documents.size
    }

    fun deleteCollection(str: String) {
        db.collection(str).get().addOnSuccessListener {
            for(i in it){
                db.collection(str).document(i.get("name") as String).delete()
            }
        }
    }

    suspend fun addListItem(itemList : ArrayList<Item>, title: String): ArrayList<Item> {
        for(i in db.collection(title).get().await().documents){
            itemList.add(Item(i.get("name").toString(), i.get("price").toString(), i.get("imgURL").toString()))
        }
        return itemList
    }


}