package h2mud2.ganpanproject.gandan.crawler

import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup

class WebCrawler{

    val url = "http://bannermall.co.kr/"

    fun bestItemCrawler(){
        val document = Jsoup.connect(url).get()
        val bestItem = document.select("div[class=xans-element- xans-product xans-product-listmain-3 xans-product-listmain xans-product-3]").select("ul[class=prdList column5]").select("li[class=item xans-record-]")
        bestItem.forEach {
            var img = Replacer().replaceImg(it.select("img").toString())
            var title = Replacer().replaceTitle(it.select("p[class=name]").select("span")[1].toString())
            var price = Replacer().replacePrice(it.select("li[class= xans-record-]").select("span")[1].toString())
        }
    }

    fun newItemCrawler(){
        doAsync {

        }
    }

    fun recommendedItemCrawler() {
        doAsync {

        }
    }
}

class Replacer{
    fun replaceImg(str : String): String {
        var newStr : String = ""
        str.replace("<img src=\"//", "")
        for(i in 12..(str.length-1)){
            if(str[i] == '\"')break
            newStr += str[i]
        }
        return newStr
    }

    fun replaceTitle(str : String): String{
        var newStr : String = ""
        for(i in 44..(str.length-1)){
            if(str[i] == '<')break
            newStr += str[i]
        }
        return newStr
    }

    fun replacePrice(str : String): String{
        var newStr : String = ""
        for(i in 61..(str.length-1)){
            if(str[i] == '<')break
            newStr += str[i]
        }
        return newStr
    }
}