package test.lxl.com.demo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Luxulong on 2018/4/18.
 */

public class MainActivity extends Activity implements PermissionListener, PictureScanAdapter.OnPictureSelectListener {
    private final static String[] MULTI_PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.ACCESS_WIFI_STATE};

    private List<PictureSelectBean> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private PictureScanAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new TedPermission(this)
                .setPermissionListener(this)
                .setPermissions(MULTI_PERMISSIONS)
                .check();

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, SelectPictureActivity.class), 100);
            }
        });

        recyclerView = findViewById(R.id.rv);
        HashMap<String, Integer> map = new HashMap<>();
        map.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION, 2);
        map.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION, 4);
        map.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, 4);
        map.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, 2);
        RecyclerViewSpacesItemDecoration itemDecoration = new RecyclerViewSpacesItemDecoration(map);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));


        adapter = new PictureScanAdapter(this, list);
        adapter.setOnPictureSelectListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onPermissionGranted() {

    }

    @Override
    public void onPermissionDenied(ArrayList<String> deniedPermissions) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        List<PictureSelectBean> tempList = (ArrayList<PictureSelectBean>) data.getSerializableExtra("pic_list");
        list.clear();
        list.addAll(tempList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void pictureSelectListener(int position) {

    }
}
