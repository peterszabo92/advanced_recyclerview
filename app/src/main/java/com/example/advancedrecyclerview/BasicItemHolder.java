package com.example.advancedrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class BasicItemHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public BasicItemHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.item_text);
    }

    public void update(String text) {
        textView.setText(text);
    }
}
