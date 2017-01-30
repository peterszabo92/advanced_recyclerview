package com.example.advancedrecyclerview;

import android.support.v7.widget.RecyclerView;

import java.util.Random;

public class LittleItem implements ListItem {

    private String randomNumber;

    public LittleItem() {
        randomNumber = String.valueOf(new Random().nextInt(100));
    }

    @Override
    public void bind(RecyclerView.ViewHolder viewHolder, int position) {
        BasicItemHolder basicItemHolder = (BasicItemHolder) viewHolder;
        basicItemHolder.update(randomNumber);
    }

    @Override
    public int getLayout() {
        return R.layout.item_layout;
    }

    @Override
    public int getSpanSize(int spanCount, int position) {
        return 1;
    }
}
