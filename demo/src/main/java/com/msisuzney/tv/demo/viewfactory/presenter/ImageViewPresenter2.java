package com.msisuzney.tv.demo.viewfactory.presenter;

import android.graphics.Color;

import com.msisuzney.tv.demo.R;
import com.msisuzney.tv.waterfallayout.leanback.Presenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.msisuzney.tv.demo.WaterfallFragment;
import com.msisuzney.tv.demo.bean.TabBean;
import com.msisuzney.tv.waterfallayout.OnItemKeyListener;
/**
 * @author: chenxin
 * @date: 2019-12-20
 * @email: chenxin7930@qq.com
 */
public class ImageViewPresenter2 extends Presenter {


    private static Interpolator sOvershootInterpolator = new OvershootInterpolator();

    private OnItemKeyListener onItemKeyListener;

    public ImageViewPresenter2(OnItemKeyListener onItemKeyListener) {
        this.onItemKeyListener = onItemKeyListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.block_image, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        if (viewHolder instanceof MyViewHolder) {
            TabBean.ResultBean.HorizontalLayoutListBean bean = (TabBean.ResultBean.HorizontalLayoutListBean) item;
            MyViewHolder vh = (MyViewHolder) viewHolder;
            View containerView = vh.view;
            final ImageView imageView = vh.imageView;
            final FrameLayout imageDock = vh.imageDock;
            ViewGroup.LayoutParams vlp = vh.view.getLayoutParams();
            vlp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            vlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            vh.view.setLayoutParams(vlp);

            FrameLayout.LayoutParams imageDockLp = (FrameLayout.LayoutParams) imageDock.getLayoutParams();
            imageDockLp.setMargins(WaterfallFragment.COLUMN_ITEM_PADDING, WaterfallFragment.COLUMN_ITEM_PADDING,
                    WaterfallFragment.COLUMN_ITEM_PADDING, WaterfallFragment.COLUMN_ITEM_PADDING);
            imageDock.setLayoutParams(imageDockLp);

            containerView.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    imageDock.setBackgroundColor(Color.RED);
                    v.animate().scaleX(1.2f).scaleY(1.2f).setInterpolator(sOvershootInterpolator).setDuration(150).start();
                } else {
                    imageDock.setBackgroundResource(0);
                    v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(150).start();
                }
            });
            containerView.setOnKeyListener((v, keyCode, event) -> {
                if (onItemKeyListener != null && onItemKeyListener.onKey(v, event, item)) {
                    return true;
                }
                return false;
            });

            if (onItemKeyListener != null) {
                containerView.setOnClickListener(v -> {
                    onItemKeyListener.onClick(item);
                });
            }
            Glide.with(imageView)
                    .load(R.drawable.tu)
                    .into(imageView);
        }
    }


    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        if (viewHolder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
            myViewHolder.view.setOnKeyListener(null);
            myViewHolder.view.setOnFocusChangeListener(null);
            Glide.with(myViewHolder.imageView).clear(myViewHolder.imageView);
        }
    }


    public static class MyViewHolder extends ViewHolder {

        public ImageView imageView;
        public FrameLayout imageDock;

        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            imageDock = view.findViewById(R.id.image_dock);

        }
    }


}
