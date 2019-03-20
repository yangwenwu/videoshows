package com.lemon.fluttervideoshows.video.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lemon.fluttervideoshows.R;
import com.lemon.fluttervideoshows.application.GlideApp;
import com.lemon.fluttervideoshows.http.common.HttpConstants;
import com.lemon.fluttervideoshows.http.httpModel.HomeNewsBean;
import com.lemon.fluttervideoshows.utils.DeviceConfig;
import com.lemon.fluttervideoshows.utils.StringUrlUtil;

import java.util.List;


public class RecommendRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<HomeNewsBean> mData;
    protected Context context;
    protected OnItemClickListener onItemClickListener;

    public RecommendRecycleAdapter(Context context, List<HomeNewsBean> mData) {
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
        view = LayoutInflater.from(context).inflate(R.layout.video_recommend_list_item, parent, false);
        holder = new ViewHolderOne(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                final ViewHolderOne holderOne = (ViewHolderOne) holder;
        HomeNewsBean nb = mData.get(position);
        AssetManager mgr = context.getAssets();
        //根据路径得到Typeface
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/Lato-Bold.ttf");
        holderOne.video_title.setTypeface(tf);
        if (nb.title != null && !nb.title.equals("")){
            holderOne.video_title.setText(nb.title);
        }

                DeviceConfig.reinstallScreenSize(context);
                int width = DeviceConfig.getDeviceWidth() - (int) context.getResources().getDimension(R.dimen.padding_medium)
                        * 2;
                //int hight = (int) context.getResources().getDimension(R.dimen.height_all_img);

                ViewGroup.LayoutParams laParams = (ViewGroup.LayoutParams) holderOne.video_image.getLayoutParams();
//                laParams.height = width * 2 / 3;    宽高都缩小到3/5
                laParams.height = width * 9/16 *4/5;
//                laParams.width = RecyclerView.LayoutParams.WRAP_CONTENT;
                laParams.width = width* 4/5 ;
//                holderOne.video_image.setLayoutParams(laParams);
       holderOne.parent_layout.setLayoutParams(laParams); //设置父容器
        String img = nb.bigTitleImage;
//        String img = "http://www.chinadailyhk.com/attachments/image/0/133/32/56159_53265/56159_53265_620_356_jpg.jpg";
        if (img != null && !img.equals("")){
            String imgUrl = HttpConstants.SERVICEURL + StringUrlUtil.checkSeparator(img);
            GlideApp.with(context)
                    .load(imgUrl)
                    .placeholder(R.mipmap.news_big_default)
                    .error(R.mipmap.news_big_default)
                    .centerCrop()
//                    .skipMemoryCache( true )
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .dontAnimate()
                    .into(holderOne.video_image);
        }else{
            img = nb.titleImage;
            if (img != null && !img.equals("")){
                String imgUrl = HttpConstants.SERVICEURL + StringUrlUtil.checkSeparator(img);
                GlideApp.with(context)
                        .load(imgUrl)
                        .placeholder(R.mipmap.news_big_default)
                        .error(R.mipmap.news_big_default)
                        .centerCrop()
//                        .skipMemoryCache( true )
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .dontAnimate()
                        .into(holderOne.video_image);
            }else{
                holderOne.video_image.setImageResource(R.mipmap.news_big_default);
            }
        }
//                String imgUrl = HttpConstants.STATICURL + StringUrlUtil.checkSeparator(img);
//
//                Glide.with(context)
//                        .load(imgUrl)
//                        .placeholder(R.mipmap.news_big_default)
//                        .error(R.mipmap.news_big_default)
//                        .centerCrop()
//                        .dontAnimate()
//                        .into(holderOne.video_image);

                onItemEventClick(holderOne,position);
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
        private ImageView video_image;
        private TextView video_title;
        private LinearLayout parent_layout;

        public ViewHolderOne(View itemView) {
            super(itemView);
            video_image = (ImageView) itemView.findViewById(R.id.video_image);

            video_title = (TextView) itemView.findViewById(R.id.video_title);
            parent_layout = (LinearLayout) itemView.findViewById(R.id.parent_layout);
        }
    }

    protected void onItemEventClick(RecyclerView.ViewHolder holder,final int position) {
//        final int position = holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(v, position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.OnItemLongClick(v, position);
                return true;
            }
        });
    }

    public interface OnItemClickListener {
        void OnItemClick(View view, int position);

        void OnItemLongClick(View view, int position);
    }


}
