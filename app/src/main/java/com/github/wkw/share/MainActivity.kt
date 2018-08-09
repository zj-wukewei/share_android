package com.github.wkw.share

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.Toolbar
import com.github.wkw.share.base.AbstractPagerAdapter
import com.github.wkw.share.base.BaseActivity
import com.github.wkw.share.databinding.ActivityMainBinding
import com.github.wkw.share.extens.getCompatColor
import com.github.wkw.share.ui.home.HomeFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector


    lateinit var pagerAdapter: FragmentStatePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        pagerAdapter = object : AbstractPagerAdapter(supportFragmentManager, arrayOf("主页", "热门", "社区")) {
            override fun getItem(pos: Int): Fragment? {
                list[pos] = HomeFragment.newInstance()
                return list[pos]
            }
        }
        mBinding.viewPager.adapter = pagerAdapter
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
        syncToolBar(mBinding.toolbar)
    }

    private fun syncToolBar(toolbar: Toolbar) {
        val toggle = ActionBarDrawerToggle(
                this, mBinding.drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        val drawerArrowDrawable = DrawerArrowDrawable(this)
        drawerArrowDrawable.color = getCompatColor(R.color.app_title)
        toggle.drawerArrowDrawable = drawerArrowDrawable
        mBinding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}
