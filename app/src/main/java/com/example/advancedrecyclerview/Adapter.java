package com.example.advancedrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final PublishSubject<Object> onClickSubject = PublishSubject.create();

    private List<ListItem> data;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return new BasicItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSubject.onNext(holder);
            }
        });
        data.get(position).bind(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getLayout();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public ListItem getItem(int position) {
        return data.get(position);
    }

    public void setData(List<ListItem> newData) {
        data = newData;
    }

    public Observable<Object> getPositionClicks() {
        return onClickSubject.asObservable();
    }
}
