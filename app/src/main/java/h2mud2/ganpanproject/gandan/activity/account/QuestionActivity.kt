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
import h2mud2.ganpanproject.gandan.adapter.QuestionAdapter
import h2mud2.ganpanproject.gandan.model.QuestionItem
import kotlinx.android.synthetic.main.activity_question.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class QuestionActivity : AppCompatActivity() {
    /* 2020.11.13 / 김학균
    웹 크롤링 Jsoup을 통해 자주 보는 질문에 대한 질문과 대답을 가져오도록 함.
    질문만 나와있는 Activity에서 클릭하게 된다면 QuestionDetailActivity로 이동하게 됨.
     */

    val weburl = "http://bannermall.co.kr/shopinfo/faq.html#2"
    val TAG = "Question Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        MyAsyncTask().execute(weburl)

        question_recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
    }

    inner class MyAsyncTask: AsyncTask<String, String, String>(){
        private var result : String =""
        var questionList : ArrayList<QuestionItem> = arrayListOf<QuestionItem>()

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String? {

            for(i in 1..30) {
                val document: Document = Jsoup.connect("$weburl").get()
                val noticeTitle: Elements = document.select("td.faq_p").select("b").eq(i)
                val noticeContent: Elements = document.select("td.faq_b").eq(i)

                Log.d("TAG", "$noticeTitle /  $noticeContent")

                var mQuestion = QuestionItem(noticeTitle.text(), noticeContent.text())
                questionList.add(mQuestion)
            }

            return "ok"
        }

        @SuppressLint("WrongConstant")
        override fun onPostExecute(result: String?) {

            question_recyclerView.layoutManager = LinearLayoutManager(this@QuestionActivity, LinearLayout.VERTICAL, false)
            var adapter = QuestionAdapter(questionList, this@QuestionActivity)
            question_recyclerView.adapter = adapter

            adapter.setItemClickListener( object : QuestionAdapter.ItemClickListener{
                override fun onClick(view: View, position: Int) {
                    val intent = Intent(view?.context, QuestionDetailActivity::class.java)

                    intent.putExtra("title", questionList[position].title)
                    intent.putExtra("context", questionList[position].contents)

                    startActivity(intent)
                }
            })
        }
    }
}