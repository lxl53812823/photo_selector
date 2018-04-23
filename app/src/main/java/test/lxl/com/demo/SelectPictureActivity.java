package test.lxl.com.demo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SelectPictureActivity extends AppCompatActivity implements View.OnClickListener, PictureScanAdapter.OnPictureSelectListener {
    private RecyclerView recyclerView;
    private PictureScanAdapter adapter;
    private ArrayList<PictureSelectBean> list = new ArrayList<>();
    private ArrayList<PictureSelectBean> selectList = new ArrayList<>();
    private TextView tipsTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        setContentView(R.layout.activity_relativelayout);
        recyclerView = findViewById(R.id.rv);
        tipsTV = findViewById(R.id.tips_tv);
        findViewById(R.id.back_tv).setOnClickListener(this);
        findViewById(R.id.confirm_btn).setOnClickListener(this);

        HashMap<String, Integer> map = new HashMap<>();
        map.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION, 2);
        map.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION, 4);
        map.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, 4);
        map.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, 2);
        RecyclerViewSpacesItemDecoration itemDecoration = new RecyclerViewSpacesItemDecoration(map);
        recyclerView.addItemDecoration(itemDecoration);
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));


        adapter = new PictureScanAdapter(this, list);
        adapter.setOnPictureSelectListener(this);
        recyclerView.setAdapter(adapter);

        getPictureList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_tv:
                finish();
                break;


            case R.id.confirm_btn:
                Intent intent = new Intent();
                intent.putExtra("pic_list", (Serializable) selectList);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    private void getPictureList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str[] = {MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.DATA};
                Cursor cursor = SelectPictureActivity.this.getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, str,
                        null, null, null);

                while (cursor.moveToNext()) {
                    System.out.println(cursor.getString(2)); // 图片绝对路径
                    PictureSelectBean bean = new PictureSelectBean();
                    bean.setName(cursor.getString(1));
                    bean.setPath(cursor.getString(2));
                    File file = new File(cursor.getString(2));
                    if (file.exists())
                        list.add(bean);
                }
                Collections.reverse(list);
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setIndex(i);
                }
                handler.sendEmptyMessage(0);
            }
        }).start();

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -1) {
                Toast.makeText(SelectPictureActivity.this, "最多只能选择9张", Toast.LENGTH_SHORT).show();
                return;
            }
            adapter.notifyDataSetChanged();
            tipsTV.setText("已选择" + selectList.size() + "张图片");
        }
    };

    @Override
    public void pictureSelectListener(final int position) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int index = selectList.indexOf(list.get(position));
                if (index != -1) {
                    selectList.remove(index);
                    list.get(position).setSelected(false);
                    list.get(position).setSelectIndex(-1);
                    for (int i = 0; i < selectList.size(); i++) {
                        int findIndex = list.indexOf(selectList.get(i));
                        if (findIndex == -1)
                            return;
                        list.get(findIndex).setSelectIndex(i + 1);
                    }
                } else {

                    if (selectList.size() == 9) {
                        handler.sendEmptyMessage(-1);

                        return;
                    }
                    selectList.add(list.get(position));
                    list.get(position).setSelected(true);
                    list.get(position).setSelectIndex(selectList.size());
                }
                handler.sendEmptyMessage(1);
            }
        }).start();


    }

    @Override
    public void pictureClickListener(int position) {
        Intent intent = new Intent(this, SelectPicturesPreviewActivity.class);
        intent.putExtra("all_list", (Serializable) list);
        intent.putExtra("select_list", (Serializable) selectList);
        intent.putExtra("index", position);
        startActivityForResult(intent, 100);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final PictureSelectBean bean) {
        if (bean != null) {
            if (bean.isSelected()) {
                list.get(bean.getIndex()).setSelected(true);
                list.get(bean.getIndex()).setSelectIndex(selectList.size() + 1);
                selectList.add(list.get(bean.getIndex()));
            } else {
                list.get(bean.getIndex()).setSelected(false);
                list.get(bean.getIndex()).setSelectIndex(-1);
                Iterator<PictureSelectBean> it = selectList.iterator();
                while (it.hasNext()) {
                    PictureSelectBean b = it.next();
                    if (b.getIndex() == bean.getIndex())
                        it.remove();
                }

                for (int i = 0; i < selectList.size(); i++) {
                    int scanIndex = list.indexOf(selectList.get(i));
                    if (scanIndex != -1) {
                        list.get(scanIndex).setSelectIndex(i + 1);
                    }
                }

            }
          handler.sendEmptyMessage(0);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
