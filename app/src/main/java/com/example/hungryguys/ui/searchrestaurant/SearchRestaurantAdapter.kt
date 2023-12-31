package com.example.hungryguys.ui.searchrestaurant

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hungryguys.databinding.SearchRestaurantItemBinding
import com.example.hungryguys.ui.inforestaurant.InfoRestaurantActivity
import com.example.hungryguys.utills.ActivityUtills

class SearchRestaurantAdapter(
    var data: MutableList<MutableMap<String, String>>,
    val activity: FragmentActivity
) :
    RecyclerView.Adapter<SearchRestaurantAdapter.SearchRestaurantHolder>() {

    class SearchRestaurantHolder(
        val binding: SearchRestaurantItemBinding,
        private val context: Context,
        private val data: MutableList<MutableMap<String, String>>
    ) : RecyclerView.ViewHolder(binding.root) {

        // 리사이클러뷰 이벤트 처리
        fun recyclerevent(position: Int) {
            binding.root.setOnClickListener {
                val restaurantItemId = data[position][RestaurantItemId.inforestaurant_id.name]
                val restaurantname = data[position][RestaurantItemId.restaurant_name.name]
                val restaurantstar = data[position][RestaurantItemId.restaurant_star.name]
                val restaurantstarcount = data[position][RestaurantItemId.restaurant_star_count.name]
                val restaurantwe = data[position][RestaurantItemId.restaurant_we.name]
                val restaurantky = data[position][RestaurantItemId.restaurant_ky.name]

                val intent = Intent(context, InfoRestaurantActivity::class.java)
                intent.putExtra(RestaurantItemId.inforestaurant_id.name, restaurantItemId)
                intent.putExtra(RestaurantItemId.restaurant_name.name, restaurantname)
                intent.putExtra(RestaurantItemId.restaurant_star.name, restaurantstar)
                intent.putExtra(RestaurantItemId.restaurant_star_count.name, restaurantstarcount)
                intent.putExtra(RestaurantItemId.restaurant_we.name, restaurantwe)
                intent.putExtra(RestaurantItemId.restaurant_ky.name, restaurantky)

                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRestaurantHolder {
        val binding =
            SearchRestaurantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchRestaurantHolder(binding, parent.context, data)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SearchRestaurantHolder, position: Int) {
        val restaurantname = data[position][RestaurantItemId.restaurant_name.name]!!
        val restaurantdescription = data[position][RestaurantItemId.restaurant_description.name]!!
        val restaurantimg = data[position][RestaurantItemId.restaurant_img.name]!!
        val restaurantstar = data[position][RestaurantItemId.restaurant_star.name]!!
        val distance = data[position][RestaurantItemId.restaurant_distance.name]!!

        val activityUtills = ActivityUtills(activity)

        holder.binding.apply {
            restaurantName.text = restaurantname
            restaurantDescription.text = restaurantdescription
            restaurantStar.text = restaurantstar
            restaurantDistance.text = distance
            restaurantImg.visibility = View.VISIBLE
            activityUtills.setWebImg(restaurantImg, restaurantimg).start()
        }

        holder.recyclerevent(position)
    }
}