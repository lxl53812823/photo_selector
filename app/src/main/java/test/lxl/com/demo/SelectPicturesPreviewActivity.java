package test.lxl.com.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import test.lxl.com.demo.LargeImageView.LargeImageView;
import test.lxl.com.demo.photoview.ImageViewPager;

/**
 * Created by Luxulong on 2018/4/20.
 */

public class SelectPicturesPreviewActivity extends Activity implements View.OnClickListener,
        ViewPager.OnPageChangeListener, Runnable,PictureSelectAdapter.OnPicturePreviewClickListener{
    private ImageView backImg;
    private TextView stateTV;
    private ImageViewPager pager;
    private RecyclerView recyclerView;
    private TextView confirmTV;
    private PictureSelectAdapter adapter;
    private ArrayList<PictureSelectBean> allList;
    private ArrayList<PictureSelectBean> selectList;
    private int currentIndex;
    private ViewPagerAdapter viewPagerAdapter;

    private ExecutorService singleThreadPool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture_preview);
        backImg = findViewById(R.id.back);
        stateTV = findViewById(R.id.state_img);
        pager = findViewById(R.id.viewpager);
        pager.addOnPageChangeListener(this);
        stateTV.setOnClickListener(this);
        recyclerView = findViewById(R.id.rv);
        confirmTV = findViewById(R.id.confirm_btn);

        backImg.setOnClickListener(this);
        confirmTV.setOnClickListener(this);

        allList = (ArrayList<PictureSelectBean>) getIntent().getSerializableExtra("all_list");
        selectList = (ArrayList<PictureSelectBean>) getIntent().getSerializableExtra("select_list");
        currentIndex = getIntent().getIntExtra("index", 0);
        adapter.setOnPicturePreviewClickListener(this);
        pager.setJumpFast(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new PictureSelectAdapter(this, selectList);
        recyclerView.setAdapter(adapter);
        singleThreadPool = Executors.newSingleThreadExecutor();
        if (allList != null && allList.size() != 0) {
            viewPagerAdapter = new ViewPagerAdapter();
            pager.setAdapter(viewPagerAdapter);
            currentIndex = currentIndex < viewPagerAdapter.getCount() ? currentIndex : 0;
            pager.setCurrentItem(currentIndex);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
            case R.id.confirm_btn:
                finish();
                break;
            case R.id.state_img:
                if (selectIndex == -1) {
                    allList.get(currentIndex).setSelected(true);
                    selectList.add(allList.get(currentIndex));
                    selectList.get(selectList.size() - 1).setPreviewSelected(true);
                    selectIndex = selectList.size() - 1;
                    mHandler.sendEmptyMessage(0);
                    EventBus.getDefault().post(allList.get(currentIndex));
                } else {
                    selectList.get(selectIndex).setSelected(false);
                    allList.get(currentIndex).setSelected(false);
                    EventBus.getDefault().post(allList.get(currentIndex));
                    selectList.remove(selectIndex);
                    selectIndex = -1;
                    mHandler.sendEmptyMessage(0);
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentIndex = position;
        singleThreadPool.execute(this);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    int selectIndex = -1;

    @Override
    public void run() {

        for (PictureSelectBean bean : selectList)
            bean.setPreviewSelected(false);

        for (int i = 0; i < selectList.size(); i++) {

            if (selectList.get(i).getPath().equals(allList.get(currentIndex).getPath())) {
                selectList.get(i).setPreviewSelected(true);
                selectIndex = i;
                mHandler.sendEmptyMessage(0);
                return;
            }
            if (i == selectList.size() - 1) {
                selectIndex = -1;
                mHandler.sendEmptyMessage(0);

            }
        }


    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            adapter.notifyDataSetChanged();
            if (selectIndex != -1) {
                stateTV.setBackgroundResource(R.drawable.bg_red_circle);
                stateTV.setText(selectIndex + 1 + "");

            } else {
                stateTV.setBackgroundResource(R.mipmap.ic_xztp_al);
                stateTV.setText("");
            }

            confirmTV.setText("确定（" + selectList.size() + "）");
            recyclerView.scrollToPosition(selectIndex);
        }
    };

    @Override
    public void picturePreviewClickListener(int position) {
        pager.setCurrentItem(selectList.get(position).getIndex());
    }

    public class ViewPagerAdapter extends PagerAdapter {


        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }


        @Override
        public int getCount() {
            return allList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup arg0, int arg1) {
            LargeImageView imageView = new LargeImageView(SelectPicturesPreviewActivity.this);
            final String imgUrl = allList.get(arg1).getPath();
            GlideImageUtil.downLoadPicture(getApplicationContext(), imgUrl, imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    overridePendingTransition(0, R.anim.page_alpha_out);
                }
            });

            arg0.addView(imageView);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }
    }

}
