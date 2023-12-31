package com.example.hungryguys.ui.inforestaurant

import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hungryguys.databinding.InfoRestaurantMenuItemBinding
import com.example.hungryguys.databinding.InfoRestaurantReviewItemBinding
import com.example.hungryguys.utills.ActivityUtills

// 메뉴, 리뷰 리사이클러 처리
class InfoRestaurantTabAdapter (
    val data: MutableList<MutableMap<String, String>>,
    val recyclertype: String,
    val activity: FragmentActivity
) :
    RecyclerView.Adapter<InfoRestaurantTabAdapter.InfoRestaurantHolder>() {
    class InfoRestaurantHolder(
        val menuBinding: InfoRestaurantMenuItemBinding? = null,
        val reviewBinding: InfoRestaurantReviewItemBinding? = null
    ) : RecyclerView.ViewHolder(menuBinding?.root ?: reviewBinding?.root!!)

    /* menu: viewType = 1 review: viewType = 2 */
    override fun getItemViewType(position: Int): Int {
        return if (recyclertype == "menu") 1
        else 2
    }

    // 뷰타입에 따라 메뉴 아이템, 리뷰 아이템 처리

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoRestaurantHolder {
        return if (viewType == 1) {
            val binding = InfoRestaurantMenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            InfoRestaurantHolder(menuBinding = binding)
        } else {
            val binding = InfoRestaurantReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            InfoRestaurantHolder(reviewBinding = binding)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: InfoRestaurantHolder, position: Int) {
        if (holder.menuBinding != null) {
            /* 메뉴 아이템 */
            val activityUtills = ActivityUtills(activity)

            val foodname = data[position][InfoMenuItem.food_name.name]!!
            val fooddescription = data[position][InfoMenuItem.food_description.name]!!
            val foodimg = data[position][InfoMenuItem.food_img.name]!!
            var foodprice = data[position][InfoMenuItem.food_price.name]!!
            // 숫자에 콤마찍기
            val dateFormat = DecimalFormat("###,###")
            foodprice = "${dateFormat.format(foodprice.toInt())}원"

            holder.menuBinding.apply {
                foodName.text = foodname
                foodDescription.text = fooddescription
                foodPrice.text = foodprice
                foodImg.visibility = View.VISIBLE
                activityUtills.setWebImg(foodImg, foodimg).start()
            }

        } else {
            /* 리뷰 아이템 */
            val username = data[position][InfoReviewItem.user_name.name]
            val restaurantstar = data[position][InfoReviewItem.restaurant_star.name]
            val reviewtext = data[position][InfoReviewItem.review_text.name]

            holder.reviewBinding!!.apply {
                userName.text = username
                restaurantStar.text = restaurantstar
                reviewText.text = reviewtext
            }
        }
    }
}