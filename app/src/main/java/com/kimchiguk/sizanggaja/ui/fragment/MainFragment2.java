package com.kimchiguk.sizanggaja.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimchiguk.sizanggaja.R;


/**
 * Created by CJR on 2015-10-26.
 */
public class MainFragment2 extends Fragment {

    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    View view;

    EditText keyWordEditText;
    Fragment currentFragment;
    ImageView imageView_search;

    public EditText getKeyWordEditText() {
        return keyWordEditText;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main2, container, false);

        setView();
        setOnClickListener();

        fragmentReplace(FRAGMENT_ONE);

        return view;
    }

    void setView() {
        keyWordEditText = (EditText)view.findViewById(R.id.fragment2_keyWordSearch);
        imageView_search = (ImageView)view.findViewById(R.id.fragment2_searchBtn);
    }

    void setOnClickListener() {
        imageView_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentReplace(FRAGMENT_TWO);
            }
        });

        keyWordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                fragmentReplace(FRAGMENT_TWO);
                return true;
            }
        });
    }

    public void fragmentReplace(int index) {

        currentFragment = getFragment(index);

        // replace fragment
        final FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();


        transaction.replace(R.id.fragment2_fragment, currentFragment);

        // Commit the transaction
        transaction.commit();

    }

    private Fragment getFragment(int index) {
        Fragment newFragment = null;

        switch (index) {
            case FRAGMENT_ONE:
                return new Fragment2_fragment1();
            case FRAGMENT_TWO:
                Bundle bundle = new Bundle();
                bundle.putString("TAG", keyWordEditText.getText().toString());
                Fragment2_fragment2 fragment = new Fragment2_fragment2();
                fragment.setArguments(bundle);
                return fragment;
            default:
                break;
        }

        return null;
    }

}