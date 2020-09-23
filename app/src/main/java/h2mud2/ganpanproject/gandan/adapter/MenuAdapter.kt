package h2mud2.ganpanproject.gandan.adapter

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.contentValuesOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import h2mud2.ganpanproject.gandan.R

class MenuAdapter(val context: Context, val menuL: MutableList<Banner>, val itemClick: (Banner) -> Unit) :
    RecyclerView.Adapter<MenuAdapter.Holder>() {

    inner class Holder(view: View?, itemClick: (Banner) -> Unit) : RecyclerView.ViewHolder(view!!) {
        val menuName = view?.findViewById<TextView>(R.id.menuNameTV)
        val menuIcon = view?.findViewById<ImageView>(R.id.menuIcon)
        val view = view

        fun bind (banner: Banner, context: Context){
            if(banner.icon != ""){
                val resourceId = context.resources.getIdentifier(banner.icon, "drawable", context.packageName)
                menuIcon?.setImageResource(resourceId)
            }else{
                menuIcon?.setImageResource(R.mipmap.ic_launcher)
            }
            menuName?.text = banner.menuName
            view?.setOnClickListener{itemClick(banner)}

        }
    }

    override fun getItemCount(): Int = menuL.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false)
        return Holder(view,itemClick)

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(menuL[position], context)
    }

}

class Banner(var menuName : String, var icon: String)