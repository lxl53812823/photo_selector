package test.lxl.com.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Luxulong on 2018/4/18.
 */

public class PictureScanAdapter extends RecyclerView.Adapter {

    private List<PictureSelectBean> list;
    private Context context;

    public PictureScanAdapter(Context context, List<PictureSelectBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PictureViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pic_scan, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PictureViewHolder viewHolder = (PictureViewHolder) holder;
        GlideImageUtil.loadThumbnail(context,list.get(position).getPath(),viewHolder.imageView);
        viewHolder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onPictureSelectListener != null)
                    onPictureSelectListener.pictureSelectListener(position);
            }
        });
        if (list.get(position).isSelected()) {
            viewHolder.tv.setBackgroundResource(R.drawable.bg_red_circle);
            viewHolder.tv.setText(list.get(position).getSelectIndex() + "");
        } else {
            viewHolder.tv.setBackgroundResource(R.mipmap.ic_xztp_al);
            viewHolder.tv.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PictureViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tv;

        public PictureViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            tv = itemView.findViewById(R.id.radio_btn);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onPictureSelectListener != null)
                        onPictureSelectListener.pictureClickListener(getLayoutPosition());
                }
            });
        }
    }

    private OnPictureSelectListener onPictureSelectListener;

    public void setOnPictureSelectListener(OnPictureSelectListener onPictureSelectListener) {
        this.onPictureSelectListener = onPictureSelectListener;
    }

    public interface OnPictureSelectListener {
        void pictureSelectListener(int position);
        void pictureClickListener(int position);
    }
}
