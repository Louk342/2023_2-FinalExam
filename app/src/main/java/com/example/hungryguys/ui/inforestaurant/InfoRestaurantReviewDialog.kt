package com.example.hungryguys.ui.inforestaurant

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.core.view.indices
import androidx.fragment.app.DialogFragment
import com.example.hungryguys.R
import com.example.hungryguys.databinding.DialogAddReviewBinding

class InfoRestaurantReviewDialog : DialogFragment() {

    lateinit var binding: DialogAddReviewBinding
    lateinit var stars: LinearLayout
    var reviewStar = 1 //최초 별점
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isCancelable = false
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding = DialogAddReviewBinding.inflate(inflater, container, false)

        stars = binding.starLayout
        stars.forEach {
            it.setOnClickListener(fillStars)
        }

        // 취소이벤트
        binding.buttonCencel.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    // 별점 선택 이벤트
    private val fillStars = View.OnClickListener { thisview ->
        // 전체리셋
        stars.forEach {
            (it as ImageView).setImageResource(R.drawable.grade_disablestar_icon)
        }

        for (i in stars.indices) {
            (stars[i] as ImageView).setImageResource(R.drawable.grade_star_icon)
            if (stars[i].id == thisview.id) {
                Log.d("로그", "별점: ${i+1}")
                return@OnClickListener
            }
        }
    }
}