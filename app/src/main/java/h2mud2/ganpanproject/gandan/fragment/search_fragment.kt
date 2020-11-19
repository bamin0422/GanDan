package h2mud2.ganpanproject.gandan.fragment

import android.content.ContentValues
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.activity.item.BannerActivity
import h2mud2.ganpanproject.gandan.activity.item.DesignItemActivity
import h2mud2.ganpanproject.gandan.activity.item.HangingActivity
import h2mud2.ganpanproject.gandan.activity.item.SteelBannerActivity
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot
import h2mud2.ganpanproject.gandan.activity.MainActivity
import h2mud2.ganpanproject.gandan.adapter.SearchAdapter
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import h2mud2.ganpanproject.gandan.model.ItemName
import kotlinx.android.synthetic.main.search_fragment.view.*
import kotlin.collections.ArrayList

class search_fragment: Fragment() {
    var firestore : FirebaseFirestore? = null

    lateinit var banner_btn : Button
    lateinit var steelBanner_btn: Button
    lateinit var hanging_btn : Button
    lateinit var designItem_btn: Button
    var productList : ArrayList<ItemName> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firestore = FirebaseFirestore.getInstance()

        val view = inflater.inflate(R.layout.search_fragment, container, false)
        val searchWord = view!!.findViewById(R.id.searchWord) as EditText
        val searchBtn = view!!.findViewById(R.id.searchBtn) as ImageButton
        val backBtn = view!!.findViewById(R.id.back_btn) as ImageButton
        val searchItem_Rv = view!!.findViewById(R.id.searchItemRecyclerview) as RecyclerView

        var overlapStringList: ArrayList<ItemName> = arrayListOf()

        val productKinds = listOf(
            "banneritem",
            "bestitem",
            "designitem",
            "newitem",
            "recommendeditem",
            "steelitem"
        )

        val scope = CoroutineScope(Dispatchers.Default)

        scope.launch {
            //for (product in productKinds) {
            for(product in productKinds) {
                val deferred: Deferred<QuerySnapshot?> = async {
                    var documentDB: QuerySnapshot? = null
                    var docRef = firestore!!.collection(product.toString()).get()
                        .addOnSuccessListener { documents ->
                            documentDB = documents
                        }.await()

                    documentDB
                }

                val job: Job = async {

                    val documents: QuerySnapshot? = deferred.await()

                    if (documents != null) {
                        for (document in documents) {
                            var name: String = document.get("name").toString()
                            var post = ItemName(name);
                            productList.add(post)
                        }
                    }
                }
                job.join()
            }

            activity?.runOnUiThread(Runnable {
                searchBtn.setOnClickListener {
                    val searchedString: String = searchWord.text.toString()
                    if(searchedString != ""){
                        for(List in productList){
                            if (List.name.contains(searchedString)) {
                                overlapStringList.add(List)
                                Log.d(ContentValues.TAG, "overlapStringList : ${List.name.toString()}")
                            }
                        }
                    }else{
                        Toast.makeText(context, "검색어를 입력해주세요", Toast.LENGTH_LONG).show();
                        overlapStringList = arrayListOf()
                    }
                    val adapter = SearchAdapter(overlapStringList)
                    searchItem_Rv.adapter = adapter
                }
            })
        }

        backBtn.setOnClickListener {
            activity?.let {
                var intent = Intent(view.context, MainActivity::class.java)
                startActivity(intent)
            }
        }


        banner_btn = view.findViewById<Button>(R.id.banner)
        steelBanner_btn = view.findViewById<Button>(R.id.steelbanner)
        hanging_btn = view.findViewById<Button>(R.id.hanging)
        designItem_btn = view.findViewById<Button>(R.id.designItem)

        banner_btn.setOnClickListener {
            activity?.let {
                var intent = Intent(view.context, BannerActivity::class.java)
                startActivity(intent)
            }
        }

        steelBanner_btn.setOnClickListener {
            activity?.let {
                var intent = Intent(view.context, SteelBannerActivity::class.java)
                startActivity(intent)
            }
        }

        hanging_btn.setOnClickListener {
            activity?.let {
                var intent = Intent(view.context, HangingActivity::class.java)
                startActivity(intent)
            }
        }

        designItem_btn.setOnClickListener {
            activity?.let {
                var intent = Intent(view.context, DesignItemActivity::class.java)
                startActivity(intent)
            }
        }

        val spaceDecoration = VerticalSpaceItemDecoration(20)
        searchItem_Rv.addItemDecoration(spaceDecoration)
        searchItem_Rv.layoutManager = LinearLayoutManager(context)
        searchItem_Rv.setHasFixedSize(true)
       return view
    }

    inner class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
            outRect.bottom = verticalSpaceHeight
        }
    }
}