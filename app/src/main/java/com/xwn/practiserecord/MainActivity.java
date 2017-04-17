package com.xwn.practiserecord;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;


import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.xwn.practiserecord.fragment.BlankFragment;
import com.xwn.practiserecord.fragment.MyselfFragment;
import com.xwn.practiserecord.fragment.WorksFragment;
import com.xwn.practiserecord.util.DensityUtil;
import com.xwn.practiserecord.util.NoScrollViewPager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 314;
    private NoScrollViewPager noScrollViewPager;
    private List<Fragment> fragmentList;
    private BottomBar bottomBar;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.main_title);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }else{

        }
        DensityUtil.MIUISetStatusBarLightMode(getWindow(),true);
        Bmob.initialize(this, "682a73b9e47c1a7e0e3e5514b92614a9");

        initFragmentList();
        noScrollViewPager = (NoScrollViewPager) findViewById(R.id.no_scroll_viewpager);
        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        noScrollViewPager.setAdapter(mAdapter);
        initBottomBar();
        noScrollViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("TAG", "onPageSelected: "+bottomBar.getCurrentTabPosition());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initFragmentList() {
        fragmentList = new ArrayList<>();
        BlankFragment blankFragment = new BlankFragment();
        WorksFragment worksFragment = new WorksFragment();
        MyselfFragment myselfFragment = new MyselfFragment();
        fragmentList.add(blankFragment);
        fragmentList.add(worksFragment);
        fragmentList.add(myselfFragment);
    }

    private void initBottomBar() {
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_article:
                        noScrollViewPager.setCurrentItem(0);
                        title.setText("文章");
                        break;
                    case R.id.tab_works:
                        noScrollViewPager.setCurrentItem(1);
                        title.setText("作品");
                        break;
                    case R.id.tab_myself:
                        noScrollViewPager.setCurrentItem(2);
                        title.setText("作者");
                        break;
                }
            }
        });
        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                //Toast.makeText(MainActivity.this,tabId+"再次",Toast.LENGTH_SHORT).show();
            }
        });
    }



}