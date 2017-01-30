package com.example.advancedrecyclerview;

import android.support.v7.util.DiffUtil;

/**
 * Created by Szabo Peter on 2017. 01. 24..
 */

public class CustomDiffUtil extends DiffUtil.Callback {
    @Override
    public int getOldListSize() {
        return 0;
    }

    @Override
    public int getNewListSize() {
        return 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }
}
