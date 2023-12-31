package com.example.hungryguys.ui.inforestaurant

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hungryguys.databinding.ActivityInfoRestaurantBinding
import com.example.hungryguys.ui.searchrestaurant.RestaurantItemId
import com.example.hungryguys.utills.ActivityUtills
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.tabs.TabLayoutMediator

class InfoRestaurantActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var binding: ActivityInfoRestaurantBinding
    lateinit var menuFragment: InfoRestaurantMenuFragment
    lateinit var reviewFragment: InfoRestaurantReviewFragment
    lateinit var restaurantname: String // 식당이름
    private var restaurantwe: Double ?= null //식당 위도
    private var restaurantky: Double ?= null //식당 경도
    var restaurantid: String ?= null //식당 DB 식별용 값

    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 상단 상테바, 하단 내비게이션 투명화 및 보정
        val activityUtills = ActivityUtills(this)
        activityUtills.setStatusBarTransparent()
        activityUtills.setStatusBarAllPadding(binding.root)

        // 툴바 설정
        val toolbar = binding.toolbar
        toolbar.setContentInsetsRelative(0, 0)
        setSupportActionBar(binding.toolbar)

        // 툴바에 백 버튼 만들기
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //인텐트로 넘어온 데이터 처리 함수
        setIntentData()

        // 텝 매뉴 프래그먼트 생성
        menuFragment = InfoRestaurantMenuFragment()
        reviewFragment = InfoRestaurantReviewFragment()

        // 뷰 패이저 어뎁터 설정 및 탭 구성
        val pagerAdapter = restaurantMenuAdapter
        binding.menuPager.adapter = pagerAdapter
        setTabLayout()

        // 파티생성 추가 텍스트 이벤트
        binding.addPartyButton.setOnClickListener {
            InfoRestaurantPartyDialog().show(supportFragmentManager, "파티추가")
        }

        doLocation()
    }

    fun doLocation() {
        val mapFragment = supportFragmentManager.findFragmentById(com.example.hungryguys.R.id.mapView) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    // 인텐트로 넘어온값 처리
    private fun setIntentData() {
        intent.apply {
            //db 고유 식별자
            restaurantid = getStringExtra(RestaurantItemId.inforestaurant_id.name)
            // 식당이름
            restaurantname = getStringExtra(RestaurantItemId.restaurant_name.name)!!
            // 위도
            restaurantwe = getStringExtra(RestaurantItemId.restaurant_we.name)?.toDouble()
            // 경도
            restaurantky = getStringExtra(RestaurantItemId.restaurant_ky.name)?.toDouble()

            val restaurantstar = getStringExtra(RestaurantItemId.restaurant_star.name)
            val restaurantstarcount = "(${getStringExtra(RestaurantItemId.restaurant_star_count.name)})"

            binding.restaurantName.text = restaurantname
            binding.restaurantStar.text = restaurantstar
            binding.restaurantStarCount.text = restaurantstarcount
            binding.restaurantName.text = restaurantname
        }
    }

    // NULL이 아닌 GoogleMap 객체를 파라미터로 제공해 줄 수 있을 때 호출
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL

        val groupLatLng = LatLng(restaurantwe!!, restaurantky!!)
        val position = CameraPosition.Builder()
            .target(groupLatLng)
            .zoom(16f)
            .build()
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(position))

        val markerOptions = MarkerOptions().run {
            position(groupLatLng)
            title(restaurantname)
        }
        val marker = googleMap.addMarker(markerOptions)
        marker?.showInfoWindow()
    }

    // 리뷰추가 리사이클러 갱신
    fun addReview(item: MutableMap<String, String>) {
        reviewFragment.recyclerAdapter.apply {
            data.add(0, item)
            notifyItemInserted(0)
            reviewFragment.recyclerView.smoothScrollToPosition(0)
        }
    }

    // 백버튼 클릭 이벤트
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }


    // 탭 레이아웃 구성
    private val restaurantMenuAdapter = object: FragmentStateAdapter(this) {
        override fun getItemCount() = 2
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> menuFragment
                1 -> reviewFragment
                else -> Fragment()
            }
        }
    }

    // 텝 제목 설정
    private fun setTabLayout() {
        TabLayoutMediator(binding.menuLayout, binding.menuPager) { tab, position ->
            tab.text = when(position) {
                0 -> "메뉴"
                1 -> "리뷰"
                else -> ""
            }
        }.attach()
    }
}