package com.kimchiguk.sizanggaja;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kimchiguk.sizanggaja.adapter.StoreImageAdapter;
import com.kimchiguk.sizanggaja.adapter.StoreMenuAdapter;
import com.kimchiguk.sizanggaja.model.Menu;
import com.kimchiguk.sizanggaja.model.StoreForm;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Created by MinJae on 2015-10-27.
 */
public class StoreAcitvity extends ActionBarActivity {
    GoogleMap map;
    LocationManager manager;


    private Double d1;
    private Double d2;

    private ImageView imageView_back;
    private TextView textView_title;
    private TextView textView_name;
    private TextView textView_mainSale;
    private TextView textView_phone;
    private ImageView imageView_goToBlog;
    private ViewPager viewPager_image;
    private ViewPager viewPager_storemenu;
    private StoreImageAdapter storeImageAdapter;

    private ImageView imageView_arrowLeft;
    private ImageView imageView_arrowRight;
    private ScrollView scrollView;
    private TextView textView_menuTxt;
    private View view;


    private String getJSON = "";

    private Intent intent;
    private ArrayList<Menu> url_arr_list;
    private ArrayList<Menu> url_arr_storemenu_list;

    Handler handler = new Handler();

    StoreForm store_Form;

    int img_arr_size;
    int menu_arr_size;


    private StoreMenuAdapter storeMenuAdapter;
    private ListView storemenulistview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        setView();
        setListener();

        url_arr_list = new ArrayList<Menu>();

        url_arr_storemenu_list = new ArrayList<Menu>();
//        store_menu = new ArrayList<Menu>(this, R.layout.item_storemenu, url_arr_storemenu_list);

        storeImageAdapter = new StoreImageAdapter(getApplicationContext());
        storeMenuAdapter = new StoreMenuAdapter(getApplicationContext());

        //menuAdapter = new StoreAdapter(getApplicationContext());

        intent = getIntent();
        store_Form = intent.getParcelableExtra("STORE");

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        RequestThread thread = new RequestThread();
        thread.start();

        storemenulistview.setScrollContainer(false);
//        setListViewHieghtBasedOnChildren(storemenulistview);
    }

    void setView() {
        imageView_back = (ImageView) findViewById(R.id.storeActivity_backBtn);
        textView_title = (TextView) findViewById(R.id.storeActivity_title);
        textView_name = (TextView) findViewById(R.id.storeActivity_name);
        textView_mainSale = (TextView) findViewById(R.id.storeActivity_mainSale);
        textView_phone = (TextView) findViewById(R.id.storeActivity_phone);
        imageView_goToBlog = (ImageView) findViewById(R.id.storeActivity_goToBlog);
        viewPager_image = (ViewPager) findViewById(R.id.storeActivity_viewPager);
        imageView_arrowLeft = (ImageView) findViewById(R.id.storeActivity_arrowLeft);
        imageView_arrowRight = (ImageView) findViewById(R.id.storeActivity_arrowRight);
        scrollView = (ScrollView) findViewById(R.id.storeActivity_scrollView);
        map = ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.storeActivity_map)).getMap();
        storemenulistview = (ListView) findViewById(R.id.market_meun_List);
        textView_menuTxt = (TextView)findViewById(R.id.storeActivity_menuTxt);
        view = (View)findViewById(R.id.storeActivity_view);
    }

    void setListener() {
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imageView_goToBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BlogActivity.class);
                intent.putExtra("URL", store_Form.getBlog_url());
                intent.putExtra("TITLE", store_Form.getName());
                startActivity(intent);
            }
        });

        imageView_arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = viewPager_image.getCurrentItem();

                viewPager_image.setCurrentItem(current - 1);
            }
        });

        imageView_arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = viewPager_image.getCurrentItem();

                viewPager_image.setCurrentItem(current + 1);
            }
        });

        ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.storeActivity_map)).setListener(new WorkaroundMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                scrollView.requestDisallowInterceptTouchEvent(true);
            }
        });
    }

    class RequestThread extends Thread {

        @Override
        public void run() {
            super.run();

            try {
                SendByHttp();

                d1 = Double.valueOf(store_Form.getLatitude()).doubleValue();
                d2 = Double.valueOf(store_Form.getLongitude()).doubleValue();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView_title.setText(store_Form.getName());
                        textView_name.setText(store_Form.getName());
                        textView_mainSale.setText(store_Form.getMain_sale());
                        textView_phone.setText(store_Form.getPhone_number());

                        showCurrentMap(d1, d2);

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void SendByHttp() {

        OkHttpClient client = new OkHttpClient();


        String temp = store_Form.getId();
        String url = "http://wkdgusdn3.dothome.co.kr/store_img.php?id=" + temp;

        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            getJSON = response.body().string();

            JSONObject jsonObject = new JSONObject(getJSON);
            JSONArray jsonArrayMenu = jsonObject.getJSONArray("menu");
            JSONArray jsonArrayImage = jsonObject.getJSONArray("image");

            menu_arr_size = jsonArrayMenu.length();
            for (int i = 0; i < menu_arr_size; i++) {

                String m_name = URLDecoder.decode(jsonArrayMenu.getJSONObject(i).getString("name"));

                int m_price = jsonArrayMenu.getJSONObject(i).getInt("price");
                String m_url = "http://" + jsonArrayMenu.getJSONObject(i).getString("img_url");

                storeMenuAdapter.addItem(m_name, m_price, m_url);

                Menu store_menu = new Menu(m_name, m_price, m_url);
                url_arr_storemenu_list.add(store_menu);
            }

            img_arr_size = jsonArrayImage.length();
            for (int i = 0; i < img_arr_size; i++) {
                String i_url = "http://" + jsonArrayImage.getJSONObject(i).getString("img_url");

                storeImageAdapter.addImage(i_url);

                Menu l_img = new Menu(null, 0, i_url);
                url_arr_list.add(l_img);
            }
;
            handler.post(new Runnable() {
                @Override
                public void run() {

                    if (menu_arr_size > 0) {
                        textView_menuTxt.setVisibility(View.VISIBLE);
                        view.setVisibility(View.VISIBLE);
                    }
                    viewPager_image.setAdapter(storeImageAdapter);
                    storemenulistview.setAdapter(storeMenuAdapter);
                    setListViewHeightBasedOnChildren(storemenulistview);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showCurrentMap(Double latitude, Double longitude) {
        LatLng curPoint = new LatLng(latitude, longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 17));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        map.getUiSettings().setAllGesturesEnabled(false);

        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(latitude, longitude));
        marker.title(store_Form.getName());
//        marker.snippet("시장 위치");
        marker.draggable(true);

        // marker size 조정
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),
                getResources().getIdentifier("icon_location_map", "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap,
                imageBitmap.getWidth() / 3,
                imageBitmap.getHeight() / 3,
                false);

        marker.icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap));

        map.addMarker(marker);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
