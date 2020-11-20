package h2mud2.ganpanproject.gandan.activity.account

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.adapter.NoticeAdapter
import h2mud2.ganpanproject.gandan.model.NoticeItem
import kotlinx.android.synthetic.main.activity_notice.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class NoticeActivity : AppCompatActivity() {

    val weburl = "http://bannermall.co.kr/board/free/read.html?"
    val TAG = "Notice Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        MyAsyncTask().execute(weburl)

        notice_recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))

    }

    inner class MyAsyncTask: AsyncTask<String, String, String>(){
        private var result : String =""
        var noticeList : ArrayList<NoticeItem> = arrayListOf<NoticeItem>()

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String? {
            val queryurl = arrayListOf<String>(
                "no=1175&board_no=1",
                "no=1176&board_no=1",
                "no=1177&board_no=1",
                "no=1178&board_no=1",
                "no=1179&board_no=1"
                /*"no=1149&board_no=1",
                "no=1150&board_no=1",
                "no=1151&board_no=1"*/
            )


            for(query in queryurl){
                val document : Document = Jsoup.connect("$weburl" + query).get()
                val noticeTitle : Elements = document.select("div.boardView").select("table")
                    .select("tbody").select("tr").select("td").eq(0)
                val noticeDate: Elements = document.select("span.td").eq(0)
                val noticeContent : Elements = document.select("tr.view").select("td").select("div.detail")
                    .select("p")

                Log.d("TAG", "$noticeTitle / $noticeDate / $noticeContent")

                var mNotice = NoticeItem(noticeTitle.text(), noticeDate.text(), noticeContent.text())
                noticeList.add(mNotice)
            }


            return "ok"
        }

        @SuppressLint("WrongConstant")
        override fun onPostExecute(result: String?) {

            notice_recyclerView.layoutManager = LinearLayoutManager(this@NoticeActivity, LinearLayout.VERTICAL, false)
            var adapter = NoticeAdapter(noticeList, this@NoticeActivity)
            notice_recyclerView.adapter = adapter

            adapter.setItemClickListener( object : NoticeAdapter.ItemClickListener{
                override fun onClick(view: View, position: Int) {
                    val intent = Intent(view?.context, NoticeDetailActivity::class.java)

                    intent.putExtra("title", noticeList[position].title)
                    intent.putExtra("date", noticeList[position].date)
                    intent.putExtra("context", noticeList[position].contents)

                    startActivity(intent)
                }
            })
        }
    }
}