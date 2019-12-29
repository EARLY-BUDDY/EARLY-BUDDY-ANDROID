package com.devaon.early_buddy_android.feature.route

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.route.Route
import com.devaon.early_buddy_android.data.route.RouteResponse
import com.devaon.early_buddy_android.data.route.SubPath
import com.devaon.early_buddy_android.feature.HomeActivity
import com.devaon.early_buddy_android.server.EarlyBuddyServiceImpl
import kotlinx.android.synthetic.main.activity_route.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RouteActivity : AppCompatActivity() {

    private lateinit var routeRecyclerView: RecyclerView
    private lateinit var routeAdapter: RouteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        routeRecyclerView = findViewById(R.id.act_route_rv_riding_info)
        routeAdapter = RouteAdapter(this, object : RouteViewHolder.ItemClickListener {
            override fun dropDownClick(position: Int) {
                when (routeAdapter.getClicked(position)) {
                    false -> {
                        Log.e("sdas", "Asd")
                    }
                    else -> {
                        Log.e("aaa", "Bbb")
                    }
                }
            }
        })
        routeRecyclerView.adapter = routeAdapter
        makeListItem()
        intent()

    }

    private fun makeListItem() {
        val callRoute: Call<RouteResponse> = EarlyBuddyServiceImpl.service.getRoute(
            127.077863,
            37.625399,
            126.953504,
            37.496536
        )

        callRoute.enqueue(object : Callback<RouteResponse> {
            override fun onFailure(call: Call<RouteResponse>, t: Throwable) {
                Log.e("error is ", t.toString())
            }

            override fun onResponse(call: Call<RouteResponse>, response: Response<RouteResponse>) {
                if(response.isSuccessful){
                    Log.e("result is ", response.body().toString())
                    val route = response.body()!!
//                    var i  = 0
//                    var list : ArrayList<SubPath> =ArrayList()
//                    while (i<route.data.path.size){
//                        list.add()
//                    }
                    routeAdapter.setRouteItem(route.data.path[0].subPath)
                    routeAdapter.routeList[0].startName="내집은 짹짹이!!"
                    routeAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun intent() {
        act_route_iv_back.setOnClickListener {
            finish()
        }
    }
}
