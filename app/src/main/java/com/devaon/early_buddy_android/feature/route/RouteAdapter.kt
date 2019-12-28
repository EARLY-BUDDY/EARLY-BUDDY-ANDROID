package com.devaon.early_buddy_android.feature.route

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.route.Route

class RouteAdapter(
    var context: Context,
    private val clickListener: RouteViewHolder.ItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var routeDetailAdapter: RouteDetailAdapter
    private val routeList: ArrayList<Route> = ArrayList()

    //경로데이터 넣기
    fun setRouteItem(newRouteList: ArrayList<Route>) {
        with(routeList) {
            clear()
            addAll(newRouteList)
        }
        notifyDataSetChanged()
    }

    //클릭 여부 확인
    fun getClicked(position: Int): Boolean {
        return routeList[position].clicked
    }

    override fun getItemCount(): Int {
        return routeList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RouteViewHolder) {
            holder.dropDown.setOnClickListener {
                if (routeList[position].clicked) {
                    holder.detailList.visibility = View.GONE
                    holder.dropDownUp.setImageResource(R.drawable.ic_dropbox_down)
                    routeList[position].clicked = false
                } else {
                    routeDetailAdapter = RouteDetailAdapter(routeList[position].path)
                    holder.detailList.visibility = View.VISIBLE
                    holder.detailList.adapter = routeDetailAdapter
                    holder.detailList.layoutManager = LinearLayoutManager(context)
                    holder.dropDownUp.setImageResource(R.drawable.ic_dropbox_up)
                    routeList[position].clicked = true
                }
            }
        } else {

        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_pass_through_route_first, parent, false)
                FirstViewHolder(view)
            }
            else -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_pass_through_route, parent, false)
                RouteViewHolder(view, clickListener)
            }
        }
    }
}


class RouteViewHolder(itemView: View, private val clickListener: ItemClickListener) :
    RecyclerView.ViewHolder(itemView) {
    var dropDown: ConstraintLayout = itemView.findViewById(R.id.item_pass_cl_drop_down_up)
    var detailList: RecyclerView = itemView.findViewById(R.id.item_pass_rv_riding_info_detail)
    var dropDownUp: ImageView = itemView.findViewById(R.id.item_pass_iv_drop_down_up_icon)

    init {
        dropDown.setOnClickListener {
            clickListener.dropDownClick(adapterPosition)
        }
    }

    interface ItemClickListener {
        fun dropDownClick(position: Int)
    }
}

class FirstViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}