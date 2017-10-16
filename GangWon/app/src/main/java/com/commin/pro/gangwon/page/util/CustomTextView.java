package com.commin.pro.gangwon.page.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by user on 2017-09-12.
 */

public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
    }
    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override public void setText(CharSequence text, BufferType type) {
        super.setText(text.toString().replace(" ", "\u00A0"), type);
    }
}