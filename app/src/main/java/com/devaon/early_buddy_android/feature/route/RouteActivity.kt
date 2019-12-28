package com.devaon.early_buddy_android.feature.route

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.route.Route
import com.devaon.early_buddy_android.feature.HomeActivity
import kotlinx.android.synthetic.main.activity_route.*

class RouteActivity : AppCompatActivity() {

    private var detailList: ArrayList<Route> = ArrayList()
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
        detailList.add(
            Route(
                startAddress = "공릉",
                endAddress = "건대입구",
                clicked = false,
                path = arrayListOf("상계역", "노원역", "중계역", "하계역", "공릉역")
            )
        )
        detailList.add(
            Route(
                startAddress = "공릉",
                endAddress = "건대입구",
                clicked = false,
                path = arrayListOf("상계역", "노원역", "중계역", "하계역", "공릉역")
            )
        )
        detailList.add(
            Route(
                startAddress = "공릉",
                endAddress = "건대입구",
                clicked = false,
                path = arrayListOf("상계역", "노원역", "중계역", "하계역", "공릉역")
            )
        )

        routeAdapter.setRouteItem(detailList)
    }

    private fun intent() {
        act_route_iv_back.setOnClickListener {
            var goBack = Intent(this, HomeActivity::class.java)
            startActivity(goBack)
        }
    }
}
