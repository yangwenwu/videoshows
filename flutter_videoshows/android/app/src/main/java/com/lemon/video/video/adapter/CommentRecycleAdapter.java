package com.lemon.video.video.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lemon.video.R;
import com.lemon.video.http.common.HttpConstants;
import com.lemon.video.http.httpModel.CommentBean;
import com.lemon.video.utils.DataUtilNew;
import com.lemon.video.utils.GlideImageUtils;
import com.lemon.video.utils.StringUrlUtil;

import java.util.List;

public class CommentRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<CommentBean> mData;
    protected Context context;
    protected OnItemClickListener onItemClickListener;

    public CommentRecycleAdapter(Context context, List<CommentBean> mData) {
        this.mData = mData;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
//        view = LayoutInflater.from(context).inflate(R.layout.video_list_item2, parent, false);
//        view = LayoutInflater.from(context).inflate(R.layout.video_recommend_list_item, parent, false);
        view = LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false);
        holder = new ViewHolderOne(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolderOne holderOne = (ViewHolderOne) holder;
        CommentBean bean = mData.get(position);
        AssetManager mgr = context.getAssets();
        //根据路径得到Typeface
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/Lato-Bold.ttf");
        Typeface tf2 = Typeface.createFromAsset(mgr, "fonts/Lato-Regular.ttf");
        holderOne.comment_nickname.setTypeface(tf);
        holderOne.comment_time.setTypeface(tf2);
        holderOne.comment_content.setTypeface(tf2);

        if (bean.nickName != null && !bean.nickName.equals("")) {
            holderOne.comment_nickname.setText(bean.nickName.toUpperCase());
        } else {
            holderOne.comment_nickname.setText("");
        }

//        holderOne.comment_time.setText(DataUtilNew.getDateDifferentValue(context, bean.createTime));
        holderOne.comment_time.setText(DataUtilNew.getTimeDifference(bean.createTime).toUpperCase());
//        holderOne.comment_time.setText("15 MIN AGO");
        holderOne.comment_content.setText(bean.content);

        if (bean.headImage != null && !"".equals(bean.headImage)) {
            String imgUrl = bean.headImage;
            if (imgUrl.startsWith("http")) {
                GlideImageUtils.LoadCircleImage(context, imgUrl, holderOne.comment_img);
            } else {
                GlideImageUtils.LoadCircleImage(context, HttpConstants.IMAGEURL + StringUrlUtil.checkSeparator(imgUrl), holderOne.comment_img);
            }
        } else {
            holderOne.comment_img.setImageDrawable(context.getResources().getDrawable(R.mipmap.avatar));
        }

        onItemEventClick(holderOne, position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }


    class ViewHolderOne extends RecyclerView.ViewHolder {
        ImageView comment_img;
        TextView comment_nickname, comment_time, comment_content;

        public ViewHolderOne(View itemView) {
            super(itemView);
            comment_img = (ImageView) itemView.findViewById(R.id.comment_img);
            comment_nickname = (TextView) itemView.findViewById(R.id.comment_nickname);
            comment_time = (TextView) itemView.findViewById(R.id.comment_time);
            comment_content = (TextView) itemView.findViewById(R.id.comment_content);
        }
    }

    protected void onItemEventClick(RecyclerView.ViewHolder holder, final int position) {
//        final int position = holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(v, position);
            }
        });
    }

    public interface OnItemClickListener {
        void OnItemClick(View view, int position);
    }

}
