package com.aoslec.mynamecard.common;

import android.view.View;

public interface ItemTouchHelperListener {
    boolean onItemMove(int from_position, int to_position);
    void onItemSwipe(int position);
}

