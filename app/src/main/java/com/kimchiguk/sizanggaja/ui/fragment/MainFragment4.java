package com.kimchiguk.sizanggaja.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimchiguk.sizanggaja.R;


/**
 * Created by CJR on 2015-10-26.
 */
public class MainFragment4 extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main4, container, false);

        return view;
    }
}