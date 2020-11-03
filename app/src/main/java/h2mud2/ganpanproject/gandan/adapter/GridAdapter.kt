package h2mud2.ganpanproject.gandan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import h2mud2.ganpanproject.gandan.R
import h2mud2.ganpanproject.gandan.model.Item

class GridAdapter(val context: Context, val itemList : ArrayList<Item>) : Adapter<GridAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.grid_items, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(itemList[position], context)
    }

    override fun getItemCount() = itemList.size

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val itemImg = itemView?.findViewById<ImageView>(R.id.item_img)
        val itemName = itemView?.findViewById<TextView>(R.id.item_name)
        val itemPrice = itemView?.findViewById<TextView>(R.id.item_price)

        fun bind(item : Item, context: Context){
            itemImg?.let { Glide.with(context).load("http://"+item.imgURL).into(it) }
            itemName?.text = item.name
            itemPrice?.text = item.price+"원"
        }
    }

}