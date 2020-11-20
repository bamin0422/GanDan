package h2mud2.ganpanproject.gandan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.model.ItemName

    /* 2020.11.07 / 김학균
    검색한 텍스트를 포함한 내용들 리사이클러뷰로 나타내기 위한 어댑터
     */
class SearchAdapter(val itemList: ArrayList<ItemName>) : RecyclerView.Adapter<SearchAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_searchitems, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val itemName = itemView?.findViewById<TextView>(R.id.overlapWord)

        fun bind(item: ItemName){
            itemName?.text = item.name
        }
    }

}