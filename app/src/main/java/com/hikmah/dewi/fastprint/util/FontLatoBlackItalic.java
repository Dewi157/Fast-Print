package com.hikmah.dewi.fastprint.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by dewi on 9/21/2017.
 */

public class FontLatoBlackItalic extends TextView {


    public FontLatoBlackItalic(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Lato-BlackItalic.ttf");
        this.setTypeface(face);
    }

    public FontLatoBlackItalic(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Lato-BlackItalic.ttf");
        this.setTypeface(face);
    }

    public FontLatoBlackItalic(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face= Typeface.createFromAsset(context.getAssets(), "Lato-BlackItalic.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }

}