package com.example.sweetapp.Interface;

import android.view.View;

import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;

public interface ItemClickListener {
    void onClick(View view,int position,boolean isLongClick);
}
