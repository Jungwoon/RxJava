package com.byjw.rxjava;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by jungwoon on 2018. 1. 5..
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_image)
    ImageView imageView;

    @BindView(R.id.item_title)
    TextView title;


    public MyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    Observable<RecyclerItem> getClickObserver(RecyclerItem item) {
        return Observable.create(e -> itemView.setOnClickListener(view -> e.onNext(item)));
    }
}
