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
import h2mud2.ganpanproject.gandan.crawler.WebCrawler
import h2mud2.ganpanproject.gandan.model.Item
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import okio.Utf8.size
import org.jetbrains.anko.doAsync

class home_fragment: Fragment() {

    var sampleImages = arrayOf(drawable.carousel1, drawable.carousel2, drawable.carousel3, drawable.carousel4)
    lateinit var bannerBtn : Button
    lateinit var steelBannerBtn : Button
    lateinit var hangingBtn : Button
    lateinit var designItemBtn : Button
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


        var bestItemSize : Int = 0
        var newItemSize : Int = 0
        var recommendedItemSize : Int = 0

        doAsync {
            bestItemSize = FireStoreManager().size("bestitem")
            newItemSize = FireStoreManager().size("newitem")
            recommendedItemSize = FireStoreManager().size("recommendeditem")
        }
        Log.d("ㅎㅇ", "ㅇㅇㅇㅇㅇㅇㅇㅇㅇ "+bestItemSize+",    ddddd"+newItemSize+" dddd  "+recommendedItemSize)
        if(bestItemSize == 0 || newItemSize == 0 || recommendedItemSize == 0) {

            // best 상품 크롤링, firestore 등록, gridView 뿌려주기
            webCrawler.bestItemCrawler()

            // 신상품 크롤링, firestore 등록, gridView 뿌려주기
            webCrawler.newItemCrawler()

            // 추천상품 크롤링, firestore 등록, gridView 뿌려주기
            webCrawler.recommendedItemCrawler()
        }


        return view
    }

    var imageListener = ImageListener(){ i: Int, imageView: ImageView ->
        imageView.setImageResource((sampleImages[i]))
    }
}

class FireStoreManager {
    val db = FirebaseFirestore.getInstance()

    fun size(str : String): Int {
        var curSize : Int = 0
        db.collection(str).get().addOnSuccessListener {
            curSize = it.size()
        }
        return curSize
    }

    fun deleteCollection(str: String) {
        db.collection(str).get().addOnSuccessListener {
            for(i in it){
                db.collection(str).document(i.get("name") as String).delete()
            }
        }
    }


}