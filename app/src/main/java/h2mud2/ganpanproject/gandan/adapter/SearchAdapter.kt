package h2mud2.ganpanproject.gandan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.model.ItemName

class SearchAdapter(val itemList : ArrayList<ItemName>) : RecyclerView.Adapter<SearchAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_searchitems, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemName?.text = itemList[position].name
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val itemName = itemView?.findViewById<TextView>(R.id.overlapWord)
    }

}