package com.example.advancedrecyclerview;

import android.support.v7.widget.RecyclerView;

public interface ListItem {

    void bind(RecyclerView.ViewHolder viewHolder, int position);

    int getLayout();

    int getSpanSize(int spanCount, int position);

}
