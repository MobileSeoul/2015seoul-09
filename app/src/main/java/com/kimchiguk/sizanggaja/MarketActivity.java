package com.kimchiguk.sizanggaja;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.kimchiguk.sizanggaja.adapter.StoreAdapter;
import com.kimchiguk.sizanggaja.model.StoreForm;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MarketActivity extends ActionBarActivity {
    private static final String TAG = "MarketActivity";
    GoogleMap map;
    LocationManager manager;

    ImageView imageView_backBtn;
    TextView textView_name;
    TextView textView_location;
    TextView textView_phoneNumber;
    TextView textView_type;
    TextView textView_calendar;
    TextView textView_title;
    ScrollView scrollView;

    private ListView listView;
    private StoreAdapter adapter;
    Handler handler = new Handler();

    private int jsonSize = 0;

    ArrayList<StoreForm> menu_list;
    ArrayList<HashMap<String, String>> map_list;


    private String getJSON = "";
    private HashMap<String, String> hashMap;
    private HashMap<String, String> jsonMap;

    Double dnum1;
    Double dnum2;

    private String num = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        setView();
        setListener();

        map_list = new ArrayList<HashMap<String, String>>();
        adapter = new StoreAdapter(getApplicationContext());
        menu_list = new ArrayList<StoreForm>();

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        setListView();

    }

    void setView() {
        imageView_backBtn = (ImageView) findViewById(R.id.marketActivity_backBtn);
        hashMap = new HashMap<String, String>();
        textView_name = (TextView) findViewById(R.id.market_name);
        textView_location = (TextView) findViewById(R.id.market_location);
        textView_phoneNumber = (TextView) findViewById(R.id.market_phoneNumber);
        textView_type = (TextView) findViewById(R.id.market_type);
        textView_calendar = (TextView) findViewById(R.id.market_calendar);
        listView = (ListView) findViewById(R.id.market_storeList);
        textView_title = (TextView) findViewById(R.id.marketActivity_marketName);
        scrollView = (ScrollView) findViewById(R.id.marketActivity_scrollView);
        map = ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.market_map)).getMap();
    }

    void setListener() {
        imageView_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.market_map)).setListener(new WorkaroundMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                scrollView.requestDisallowInterceptTouchEvent(true);
            }
        });
    }


    private void setListView() {
        listView.setAdapter(adapter);

        getImageFromServer();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MarketActivity.this, StoreAcitvity.class);
                StoreForm temp_form = (StoreForm) parent.getAdapter().getItem(position);
                intent.putExtra("STORE", temp_form);
                startActivity(intent);
            }
        });

    }

    private void getImageFromServer() {
        RequestThread thread = new RequestThread();
        thread.start();
    }

    class RequestThread extends Thread {
        public void run() {
            try {
                SendByHttp();

                dnum1 = Double.valueOf(hashMap.get("latitude")).doubleValue();
                dnum2 = Double.valueOf(hashMap.get("longitude")).doubleValue();

                for (int i = 0; i < jsonSize; i++) {
                    HashMap<String, String> hmap = map_list.get(i);
                    StoreForm m_form = new StoreForm(hmap.get("id"), hmap.get("name"), hmap.get("phone_number"), hmap.get("latitude"), hmap.get("longitude"), hmap.get("blog_url"), hmap.get("main_img"), hmap.get("main_sale"));

                    menu_list.add(m_form);

                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showCurrentMap(dnum1, dnum2);
                        textView_title.setText(hashMap.get("name"));
                        textView_name.setText(hashMap.get("name"));
                        textView_location.setText(hashMap.get("address"));
                        textView_phoneNumber.setText(hashMap.get("phone_number"));
                        textView_type.setText(hashMap.get("main_sale"));
                        textView_calendar.setText(hashMap.get("holiday"));

                        for (int i = 0; i < jsonSize; i++) {
                            adapter.add(menu_list.get(i));
                        }
                        adapter.notifyDataSetChanged();

                        setListViewHeightBasedOnChildren(listView);
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void SendByHttp() {

        OkHttpClient client = new OkHttpClient();

        Intent intent2 = getIntent();
        String temp = intent2.getStringExtra("id");
        String url = "http://wkdgusdn3.dothome.co.kr/market.php?id=" + temp;

        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            getJSON = response.body().string();

            JSONArray jsonArray = new JSONArray(getJSON);
            JSONObject jsonObject = new JSONObject();
            jsonObject = jsonArray.getJSONObject(0);

            hashMap.put("name", jsonObject.getString("name"));
            hashMap.put("address", jsonObject.getString("address"));
            hashMap.put("phone_number", jsonObject.getString("phone_number"));
            hashMap.put("holiday", jsonObject.getString("holiday"));
            hashMap.put("main_sale", jsonObject.getString("main_sale"));
            hashMap.put("latitude", jsonObject.getString("latitude"));
            hashMap.put("longitude", jsonObject.getString("longitude"));

            String jsonStr = jsonObject.getString("store");
            jsonArray = new JSONArray(jsonStr);

            jsonSize = jsonArray.length();

            for (int i = 0; i < jsonSize; i++) {
                jsonObject = jsonArray.getJSONObject(i);
                jsonMap = new HashMap<String, String>();
                jsonMap.put("id", jsonObject.getString("id"));
                jsonMap.put("name", jsonObject.getString("name"));
                jsonMap.put("phone_number", jsonObject.getString("phone_number"));
                jsonMap.put("latitude", jsonObject.getString("latitude"));
                jsonMap.put("longitude", jsonObject.getString("longitude"));
                jsonMap.put("blog_url", jsonObject.getString("blog_url"));
                jsonMap.put("main_img", "http://" + jsonObject.getString("main_img"));
                jsonMap.put("main_sale", jsonObject.getString("main_sale"));
                map_list.add(jsonMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showCurrentMap(Double latitude, Double longitude) {
        LatLng curPoint = new LatLng(latitude, longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

//        map.getUiSettings().setAllGesturesEnabled(false);

        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(latitude, longitude));
        marker.title(hashMap.get("name"));
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
