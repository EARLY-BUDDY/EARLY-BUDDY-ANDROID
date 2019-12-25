package com.devaon.early_buddy_android.feature.place_search_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.place.GetPlaceData

class PlaceSearchAdapter(
    private val context: Context
) : RecyclerView.Adapter<PlaceSearchAdapter.PlaceSearchViewHolder>() {
    var data: List<GetPlaceData> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceSearchViewHolder {
        // list item follower.xml을 객체화 한다(inflate 한다).
        val view: View = LayoutInflater
            .from(context)
            .inflate(R.layout.list_item_place_search, parent, false)
        return PlaceSearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PlaceSearchViewHolder, position: Int) {
        holder.onBind(data[position])
        holder.addressDetail.text = data[position].addressDetail
        holder.address.text = data[position].address
        holder.loadAddress.text = data[position].loadAddress
    }

    inner class PlaceSearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val address: TextView = view.findViewById(R.id.li_place_search_address)
        val addressDetail : TextView = view.findViewById(R.id.li_place_search_address_detail)
        val loadAddress : TextView = view.findViewById(R.id.li_place_search_load_address)

        fun onBind(placedata: GetPlaceData) {
            itemView.setOnClickListener {
                val context = it.context
                //val intent = Intent(context, GitRepoListActivity::class.java)
                //context.startActivity(intent)
            }
        }
    }
}