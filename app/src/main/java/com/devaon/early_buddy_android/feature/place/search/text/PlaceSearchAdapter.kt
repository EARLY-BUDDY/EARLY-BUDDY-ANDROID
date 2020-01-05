package com.devaon.early_buddy_android.feature.place.search.text

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Constraints
import androidx.recyclerview.widget.RecyclerView
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.place.PlaceSearch

class PlaceSearchAdapter(
    private val context: Context
) : RecyclerView.Adapter<PlaceSearchAdapter.PlaceSearchViewHolder>() {
    var data: ArrayList<PlaceSearch> = arrayListOf()

    private lateinit var listener : onPlaceClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceSearchViewHolder {
        val view: View = LayoutInflater
            .from(context)
            .inflate(R.layout.item_place_search, parent, false)
        return PlaceSearchViewHolder(view)
    }

    fun replaceAll(list : ArrayList<PlaceSearch>){
        data.clear()
        data.addAll(list)
    }

    fun clearAll(){
        data.clear()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PlaceSearchViewHolder, position: Int) {
        holder.onBind(data[position])
        holder.placeName.text = data[position].placeName
        holder.addressName.text = data[position].addressName
        holder.roadAddressName.text = data[position].roadAddressName

        holder.container.setOnClickListener {
            listener.onItemClick(data[position].placeName, data[position].x, data[position].y)
        }
    }

    inner class PlaceSearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var placeName: TextView = view.findViewById(R.id.li_place_search_address)
        val addressName : TextView = view.findViewById(R.id.li_place_search_address_detail)
        val roadAddressName : TextView = view.findViewById(R.id.li_place_search_load_address)
        val container : ConstraintLayout = view.findViewById(R.id.li_place_search_container)


        //var i :TextView = view.fi
        var str : String = placeName.text.toString()

       /* if(str == ""){
            placeName = addressName
        }*/

        fun onBind(placedata: PlaceSearch ) {
            itemView.setOnClickListener {
                Log.d("testest","PlaceSearchViewHolder container")
                /* val context = it.context*/
                //val intent = Intent(context, GitRepoListActivity::class.java)
                //context.startActivity(intent)
            }
        }
    }

    interface onPlaceClickListener{
        fun onItemClick(placeName: String, x : Double, y : Double)
    }

    fun setOnPlaceClickListener(listener: onPlaceClickListener){
        this.listener = listener
    }

}