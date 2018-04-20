package test.lxl.com.demo;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Luxulong on 2018/4/20.
 */

public class PictureSelectAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<PictureSelectBean> list;


    public PictureSelectAdapter(Context context, List<PictureSelectBean> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PictureViewHolder(LayoutInflater.from(context).inflate(R.layout.item_select_img, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (list.get(position).isPreviewSelected())
            ((PictureViewHolder) holder).imageView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        else
            ((PictureViewHolder) holder).imageView.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
        Glide.with(context).load(list.get(position).getPath()).into(((PictureViewHolder) holder).imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class PictureViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public PictureViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
        }
    }
}
