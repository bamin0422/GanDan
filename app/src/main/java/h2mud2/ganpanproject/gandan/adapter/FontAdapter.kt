package h2mud2.ganpanproject.gandan.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import h2mud2.ganpanproject.gandan.R

class FontAdapter(val context: Context, val itemList : ArrayList<FontSt>, val itemClick: (FontSt) -> Unit) : Adapter<FontAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.menu_font, parent, false)
        return Holder(view, itemClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(itemList[position], context)
    }

    override fun getItemCount() = itemList.size

    inner class Holder(itemView: View?, itemClick: (FontSt) -> Unit) : RecyclerView.ViewHolder(itemView!!) {
        val itemName = itemView?.findViewById<TextView>(R.id.fontNameTV)


        fun bind(item: FontSt, context: Context){
            var typeface = Typeface.createFromAsset(context.assets, "font/"+item.font+".ttf")
            itemName?.text = item.name
            itemName?.typeface = typeface
            itemView.setOnClickListener{ itemClick(item) }
        }
    }

}

class FontSt(var name : String, var font: String)