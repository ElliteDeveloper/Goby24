package com.goby24.goby24.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.goby24.goby24.R;

public class PaginatorFooter extends LinearLayout {
    private Color mClMsgTxtColor;
    private String mStrPrevTitle;
    private String mStrNextTitle;
    private int     m_nPrevDrawableID;
    private int     m_nNextDrawableID;
    private int     m_nBg;

    public PaginatorFooter(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.paginator_footer, this);
    }

    public PaginatorFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PaginatorFooter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PaginatorFooter(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
