package com.kimchiguk.sizanggaja.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kimchiguk.sizanggaja.CustomDialog;
import com.kimchiguk.sizanggaja.DBContactHelper;
import com.kimchiguk.sizanggaja.R;
import com.kimchiguk.sizanggaja.StoreAcitvity;
import com.kimchiguk.sizanggaja.adapter.MyAdapter;
import com.kimchiguk.sizanggaja.model.StoreForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJR on 2015-10-26.
 */
public class MainFragment3 extends Fragment {

    View view;

    private TextView textView;
    private TextView dialog_textView;
    private ListView listView;
    private MyAdapter adapter;
    private Button button;
    private DBContactHelper db;
    private CustomDialog mCustomDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main3, container, false);
        db = new DBContactHelper(getActivity());
        List<StoreForm> menu_list = new ArrayList<StoreForm>();

        menu_list = db.getAllMenu();

        listView = (ListView)view.findViewById(R.id.listView);
        button = (Button)view.findViewById(R.id.button);
        textView = (TextView)view.findViewById(R.id.textView);

        adapter = new MyAdapter(getActivity(), textView, button);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),StoreAcitvity.class);
                StoreForm m_f = (StoreForm)parent.getAdapter().getItem(position);
                intent.putExtra("pic_infor", m_f);
                startActivity(intent);
            }
        });

        int size = menu_list.size();

        if(size == 0) {
            button.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }

        //db.deleteAll();


        for(int i=0 ; i<size; i++) {
            adapter.add(menu_list.get(i));
        }

        adapter.notifyDataSetChanged();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomDialog = new CustomDialog(getActivity(), leftListener, rightListener);
                mCustomDialog.show();
            }
        });

        //setListViewHieghtBasedOnChildren(listView);

        return view;
    }

    private View.OnClickListener leftListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mCustomDialog.dismiss();

        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            db.deleteAll();
            adapter.removeAll();
            adapter.notifyDataSetChanged();
            mCustomDialog.dismiss();

            button.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
    };

}