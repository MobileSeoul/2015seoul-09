package com.kimchiguk.sizanggaja;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kimchiguk.sizanggaja.ui.fragment.MainFragment1;
import com.kimchiguk.sizanggaja.ui.fragment.MainFragment2;
import com.kimchiguk.sizanggaja.ui.fragment.MainFragment3;
import com.kimchiguk.sizanggaja.ui.fragment.MainFragment4;


public class MainActivity extends AppCompatActivity {

    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENT_THREE = 2;
    public static final int FRAGMENT_FOUR = 3;

    Toolbar toolbar;
    ImageView imageView_home;
    ImageView imageView_search;
    ImageView imageView_heart;
    ImageView imageView_setting;

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setView();
        setClickListener();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch(position) {
                    case FRAGMENT_ONE :
                        imageView_home.setBackgroundResource(R.drawable.ic_home_or);
                        imageView_search.setBackgroundResource(R.drawable.ic_search_gr);
                        imageView_heart.setBackgroundResource(R.drawable.ic_heart_gr);
                        imageView_setting.setBackgroundResource(R.drawable.ic_setting_gr);
                        break;
                    case FRAGMENT_TWO :
                        imageView_home.setBackgroundResource(R.drawable.ic_home_gr);
                        imageView_search.setBackgroundResource(R.drawable.ic_search_or);
                        imageView_heart.setBackgroundResource(R.drawable.ic_heart_gr);
                        imageView_setting.setBackgroundResource(R.drawable.ic_setting_gr);
                        break;
                    case FRAGMENT_THREE :
                        imageView_home.setBackgroundResource(R.drawable.ic_home_gr);
                        imageView_search.setBackgroundResource(R.drawable.ic_search_gr);
                        imageView_heart.setBackgroundResource(R.drawable.ic_heart_or);
                        imageView_setting.setBackgroundResource(R.drawable.ic_setting_gr);
                        break;
                    case FRAGMENT_FOUR :
                        imageView_home.setBackgroundResource(R.drawable.ic_home_gr);
                        imageView_search.setBackgroundResource(R.drawable.ic_search_gr);
                        imageView_heart.setBackgroundResource(R.drawable.ic_heart_gr);
                        imageView_setting.setBackgroundResource(R.drawable.ic_setting_or);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setSupportActionBar(toolbar);

    }

    void setView() {
        toolbar = (Toolbar) findViewById(R.id.mainActivity_toolbar);
        imageView_home = (ImageView) findViewById(R.id.mainActivity_home);
        imageView_search = (ImageView) findViewById(R.id.mainActivity_search);
        imageView_heart = (ImageView) findViewById(R.id.mainActivity_heart);
        imageView_setting = (ImageView) findViewById(R.id.mainActivity_setting);
        mViewPager = (ViewPager) findViewById(R.id.mainActivity_fragment);
    }

    void setClickListener() {
        imageView_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(FRAGMENT_ONE);
            }
        });

        imageView_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(FRAGMENT_TWO);
            }
        });

        imageView_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(FRAGMENT_THREE);
            }
        });

        imageView_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(FRAGMENT_FOUR);
            }
        });
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            Fragment fragment = null;

            switch(position) {
                case FRAGMENT_ONE :
                    fragment = new MainFragment1();
                    break;
                case FRAGMENT_TWO :
                    fragment = new MainFragment2();
                    break;
                case FRAGMENT_THREE :
                    fragment = new MainFragment3();
                    break;
                case FRAGMENT_FOUR :
                    fragment = new MainFragment4();
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main1, container, false);
            return rootView;
        }
    }

}
