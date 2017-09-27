package com.kimchiguk.sizanggaja.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kimchiguk.sizanggaja.R;
import com.kimchiguk.sizanggaja.network.ReceiveKeyword;


/**
 * Created by HyunWoo on 2015-10-28.
 */
public class Fragment2_fragment1 extends Fragment {

    View view;
    ListView listView_keyWord;
    MainFragment2 mainFragment2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment2_fragment1, container, false);

        setView();

        ReceiveKeyword receiveKeyword = new ReceiveKeyword(view.getContext(), listView_keyWord);
        receiveKeyword.execute();

        setListener();
        mainFragment2 = (MainFragment2)getParentFragment();

        return view;
    }

    void setView() {
        listView_keyWord = (ListView) view.findViewById(R.id.fragment2_fragment1_keyWordListView);
    }

    void setListener() {

        listView_keyWord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String keyWord = (String) parent.getAdapter().getItem(position);
                mainFragment2.getKeyWordEditText().setText(keyWord);
                mainFragment2.fragmentReplace(MainFragment2.FRAGMENT_TWO);
            }
        });
    }
}