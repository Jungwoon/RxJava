package com.byjw.rxjava;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by jungwoon on 2018. 1. 5..
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<RecyclerItem> items = new ArrayList<>();

    private PublishSubject<RecyclerItem> publishSubject;

    RecyclerViewAdapter() {
        this.publishSubject = PublishSubject.create();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RecyclerItem item = items.get(position);

        holder.imageView.setImageDrawable(item.getImage());
        holder.title.setText(item.getTitle());
        holder.getClickObserver(item).subscribe(publishSubject);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItems(List<RecyclerItem> item) {
        items.addAll(item);
    }

    public void updateItems(RecyclerItem item) {
        items.add(item);
    }

    public PublishSubject<RecyclerItem> getItemPublishSubject() {
        return publishSubject;
    }
}
