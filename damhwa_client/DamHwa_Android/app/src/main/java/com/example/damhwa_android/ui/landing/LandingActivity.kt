package com.example.damhwa_android.ui.landing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.damhwa_android.R
import com.example.damhwa_android.databinding.ActivityLandingBinding


class LandingActivity : AppCompatActivity() {
    private lateinit var landingBinding: ActivityLandingBinding
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContentView에서는 인자가 2개 들어감
        landingBinding = DataBindingUtil.setContentView(this, R.layout.activity_landing)

        // 2. 초기화 지연시킨 viewPager2 객체를 여기서 초기화함
        viewPager2 = landingBinding.viewPager

        // 3. viewPager2 뷰 객체에 어댑터 적용하기
        viewPager2.adapter = ViewPagerAdapter(this)
    }

    // 추가 기능 ) back 버튼 클릭시 화면 슬라이드 과거로 이동시키기
    override fun onBackPressed() {
        if (viewPager2.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager2.currentItem = viewPager2.currentItem - 1
        }
    }
}
