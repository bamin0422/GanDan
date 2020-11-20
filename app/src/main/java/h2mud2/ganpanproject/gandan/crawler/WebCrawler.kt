package h2mud2.ganpanproject.gandan.crawler

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import h2mud2.ganpanproject.gandan.model.Item
import kotlinx.coroutines.*
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup

class WebCrawler{
    
    /* 2020.11.04 / 민대인
    Jsoup라이브러리를 사용해, bannermall에서 coroutine을 사용해 웹 크롤링 해옴.
     */

    fun bestItemCrawler() {
        CoroutineScope(Dispatchers.IO).launch {
            runBlocking {
                val url = "http://bannermall.co.kr/"
                val document = Jsoup.connect(url).get()
                val bestItem =
                    document.select("div[class=xans-element- xans-product xans-product-listmain-3 xans-product-listmain xans-product-3]")
                        .select("ul[class=prdList column5]").select("li[class=item xans-record-]")
                bestItem.forEach {
                    var img = Replacer().replaceImg(it.select("img").toString())
                    var title =
                        Replacer().replaceTitle(it.select("p[class=name]").select("span")[1].toString())
                    var price = Replacer().replacePrice(
                        it.select("li[class= xans-record-]").select("span")[1].toString()
                    )
                    val bestItem = Item(title, price, img)
                    FirebaseFirestore.getInstance().collection("bestitem").document(title).set(bestItem)
                        .addOnSuccessListener {
                            Log.d("SUCCESS", "bestItem에 데이터가 추가되었습니다.")
                        }.addOnFailureListener { e -> Log.d("FAILURE", "데이터 추가에 실패했습니다.") }
                }
            }
        }
    }

    fun newItemCrawler(){
        CoroutineScope(Dispatchers.IO).launch{
            runBlocking {
                val url = "http://bannermall.co.kr/"
                val document = Jsoup.connect(url).get()
                val newItem =
                    document.select("div[class=xans-element- xans-product xans-product-listmain-2 xans-product-listmain xans-product-2]")
                        .select("ul[class=prdList column5]").select("li[class=item xans-record-]")
                newItem.forEach {
                    var img = Replacer().replaceImg(it.select("img").toString())
                    var title =
                        Replacer().replaceTitle(it.select("p[class=name]").select("span")[1].toString())
                    var price = Replacer().replacePrice(
                        it.select("li[class= xans-record-]").select("span")[1].toString()
                    )
                    val newItem = Item(title, price, img)
                    FirebaseFirestore.getInstance().collection("newitem").document(title).set(newItem)
                        .addOnSuccessListener {
                            Log.d("SUCCESS", "newItem에 데이터가 추가되었습니다.")
                        }.addOnFailureListener { e -> Log.d("FAILURE", "데이터 추가에 실패했습니다.") }
                }
            }
        }
    }

    fun recommendedItemCrawler() {
        CoroutineScope(Dispatchers.IO).launch {
            runBlocking {
                val url = "http://bannermall.co.kr/"
                val document = Jsoup.connect(url).get()
                val recommendedItem =
                    document.select("div[class=xans-element- xans-product xans-product-listmain-1 xans-product-listmain xans-product-1]")
                        .select("ul[class=prdList column5]").select("li[class=item xans-record-]")
                recommendedItem.forEach {
                    var img = Replacer().replaceImg(it.select("img").toString())
                    var title =
                        Replacer().replaceTitle(it.select("p[class=name]").select("span")[1].toString())
                    var price = Replacer().replacePrice(
                        it.select("li[class= xans-record-]").select("span")[1].toString()
                    )
                    val recommendedItem = Item(title, price, img)
                    FirebaseFirestore.getInstance().collection("recommendeditem").document(title).set(recommendedItem)
                        .addOnSuccessListener {
                            Log.d("SUCCESS", "newItem에 데이터가 추가되었습니다.")
                        }.addOnFailureListener { e -> Log.d("FAILURE", "데이터 추가에 실패했습니다.") }
                }
            }
        }
    }

    fun bannerItemCrawler() {
        CoroutineScope(Dispatchers.IO).launch {
            runBlocking {
                for (i in 1..7) {
                    val url = "http://bannermall.co.kr/product/list.html?cate_no=121&page=" + i.toString()
                    val document = Jsoup.connect(url).get()
                    val recommendedItem =
                        document.select("div[class=xans-element- xans-product xans-product-listnormal]")
                            .select("ul[class=prdList column5]").select("li[class=item xans-record-]")
                    recommendedItem.forEach {
                        var img = Replacer().replaceImg(it.select("img").toString())
                        var title =
                            Replacer().replaceTitle(
                                it.select("p[class=name]").select("span")[2].toString()
                            )
                        var price = Replacer().replacePrice(
                            it.select("li[class= xans-record-]").select("span")[1].toString()
                        )
                        val bannerItem = Item(title, price, img)

                        FirebaseFirestore.getInstance().collection("banneritem").document(title)
                            .set(bannerItem)
                            .addOnSuccessListener {
                                Log.d("SUCCESS", "bannerItem에 데이터가 추가되었습니다.")
                            }.addOnFailureListener { e -> Log.d("FAILURE", "데이터 추가에 실패했습니다.") }
                    }
                }
            }
        }
    }

    fun DesignItemCrawler() {
        CoroutineScope(Dispatchers.IO).launch {
            runBlocking {
                for (i in 1..6) {
                    val url = "http://bannermall.co.kr/product/list.html?cate_no=126&page=" + i.toString()
                    val document = Jsoup.connect(url).get()
                    val recommendedItem =
                        document.select("div[class=xans-element- xans-product xans-product-listnormal]")
                            .select("ul[class=prdList column5]").select("li[class=item xans-record-]")
                    recommendedItem.forEach {
                        var img = Replacer().replaceImg(it.select("img").toString())
                        var title =
                            Replacer().replaceTitle(
                                it.select("p[class=name]").select("span")[2].toString()
                            )
                        var price = Replacer().replacePrice(
                            it.select("li[class= xans-record-]").select("span")[1].toString()
                        )
                        val bannerItem = Item(title, price, img)

                        FirebaseFirestore.getInstance().collection("designitem").document(title)
                            .set(bannerItem)
                            .addOnSuccessListener {
                                Log.d("SUCCESS", "designItem에 데이터가 추가되었습니다.")
                            }.addOnFailureListener { e -> Log.d("FAILURE", "데이터 추가에 실패했습니다.") }
                    }
                }
            }
        }
    }

    fun SteelItemCrawler() {
        CoroutineScope(Dispatchers.IO).launch {
            runBlocking {
                val url = "http://bannermall.co.kr/product/list.html?cate_no=125"
                val document = Jsoup.connect(url).get()
                val recommendedItem =
                    document.select("div[class=xans-element- xans-product xans-product-listnormal]")
                        .select("ul[class=prdList column5]").select("li[class=item xans-record-]")
                recommendedItem.forEach {
                    var img = Replacer().replaceImg(it.select("img").toString())
                    var title =
                        Replacer().replaceTitle(
                            it.select("p[class=name]").select("span")[2].toString()
                        )
                    var price = Replacer().replacePrice(
                        it.select("li[class= xans-record-]").select("span")[1].toString()
                    )
                    val bannerItem = Item(title, price, img)

                    FirebaseFirestore.getInstance().collection("steelitem").document(title)
                        .set(bannerItem)
                        .addOnSuccessListener {
                            Log.d("SUCCESS", "steelitem에 데이터가 추가되었습니다.")
                        }.addOnFailureListener { e -> Log.d("FAILURE", "데이터 추가에 실패했습니다.") } }
            }
        }
    }

}

class Replacer{
    fun replaceImg(str : String): String {
        var newStr : String = ""
        for(i in 12..(str.length-1)){
            if(str[i] == '\"')break
            newStr += str[i]
        }
        return newStr
    }

    fun replaceTitle(str : String): String{
        var replaceStr = str.replace("<br>","")
        replaceStr = replaceStr.replace("\"", "")
        replaceStr = replaceStr.replace("/", "")
        var newStr : String = ""
        for(i in 42..(replaceStr.length-1)){
            if(replaceStr[i] == '<')break
            newStr += replaceStr[i]
        }
        return newStr
    }

    fun replacePrice(str : String): String{
        var replaceStr = str.replace(",", "")
        replaceStr = replaceStr.replace("원", "")
        replaceStr = replaceStr.replace("/", "")

        var newStr : String = ""
        for(i in 61..(replaceStr.length-1)){
            if(replaceStr[i] == '<')break
            newStr += replaceStr[i]
        }
        return newStr
    }
}
