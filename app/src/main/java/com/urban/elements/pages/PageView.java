package com.urban.elements.pages;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.example.test.R;

public abstract class PageView extends RelativeLayout {

    private String text;

    protected static String a = "";

    public PageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public PageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PageView);

        setText(ta.getString(R.styleable.PageView_tabText));
        ta.recycle();
    }

    public PageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
