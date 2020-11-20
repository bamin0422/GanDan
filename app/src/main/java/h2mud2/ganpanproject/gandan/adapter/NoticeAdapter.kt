package h2mud2.ganpanproject.gandan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.model.NoticeItem

    /* 2020.11.12 / 김학균
    공지사항을 리사이클러뷰로 나타내기 위한 어댑터
     */
class NoticeAdapter(val items : MutableList<NoticeItem>, val context: Context) : RecyclerView.Adapter<NoticeAdapter.ViewHolder>(){

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    //클릭리스너 선언
    private lateinit var itemClickListner: ItemClickListener

    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: Any) {
        this.itemClickListner = itemClickListener as ItemClickListener
    }

    inner class ViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val noticeTitle = view?.findViewById<TextView>(R.id.text_title)
        //val noticeDate = view?.findViewById<TextView>(R.id.text_date)
        //val noticeContext = view?.findViewById<TextView>(R.id.text_content)

        fun bind (noticeItem: NoticeItem, context: Context){
            noticeTitle?.text = noticeItem.title
            //noticeDate?.text = noticeItem.date
            //noticeContext?.text = noticeItem.contents

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_notice_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NoticeAdapter.ViewHolder, position: Int) {
        holder?.bind(items[position], context)

        holder.itemView.setOnClickListener {
            itemClickListner.onClick(it, position)
        }
    }
}
