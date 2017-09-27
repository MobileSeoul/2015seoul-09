package com.kimchiguk.sizanggaja;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by MinJae on 2015-10-28.
 */
public class CustomDialog extends Dialog {

    private TextView textView;
    private ImageView l_button;
    private ImageView r_button;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.custom_dialog);

        textView = (TextView)findViewById(R.id.textView);
        l_button = (ImageView)findViewById(R.id.left_button);
        r_button = (ImageView)findViewById(R.id.right_button);

        textView.setBackgroundResource(R.drawable.graybox);

        if(mLeftClickListener != null && mRightClickListener != null) {
            l_button.setOnClickListener(mLeftClickListener);
            r_button.setOnClickListener(mRightClickListener);
        } else if(mLeftClickListener != null && mRightClickListener == null) {
            l_button.setOnClickListener(mLeftClickListener);
        } else {
        }
    }

    public CustomDialog(Context context, View.OnClickListener leftListener, View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }
}
