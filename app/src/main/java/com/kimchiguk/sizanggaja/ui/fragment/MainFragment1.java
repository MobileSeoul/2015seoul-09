package com.kimchiguk.sizanggaja.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.kimchiguk.sizanggaja.MarketActivity;
import com.kimchiguk.sizanggaja.R;
import com.kimchiguk.sizanggaja.adapter.RecImgAdapter;
import com.kimchiguk.sizanggaja.model.Rec_Img;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Created by CJR on 2015-10-26.
 */
public class MainFragment1 extends Fragment {

    View view;
    Glide glide;
    private static final String TAG = "MainActivity";
    private String temp="";
    private String idStr="";

    private ListView list;
    private RecImgAdapter recImgAdapter;

    private String getJSON = "";

    private ArrayList<String> ImgUrlList;
    private ArrayList<String> NameList;
    private ArrayList<String> MainList;
    private ArrayList<String> IdList;
    private ArrayList<Rec_Img> RecList;

    private int jsonSize=0;

    Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main1, container, false);

        ImgUrlList = new ArrayList<String>();
        NameList = new ArrayList<String>();
        RecList = new ArrayList<Rec_Img>();
        MainList = new ArrayList<String>();
        IdList = new ArrayList<String>();

        setListView();

        return view;
    }

    private void setListView() {
        recImgAdapter = new RecImgAdapter(view.getContext());

        list = (ListView)view.findViewById(R.id.fragment1_listView);
        list.setAdapter(recImgAdapter);

        getImageFromServer();


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                Rec_Img r_img = (Rec_Img)parent.getAdapter().getItem(position);
                String id_Number = r_img.getId();

                intent = new Intent(view.getContext(), MarketActivity.class);
                intent.putExtra("id",id_Number);
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

                for(int i=0; i<jsonSize; i++) {
                    String real_img_url = "http://"+ImgUrlList.get(i);
                    String for_img_name = NameList.get(i);
                    String for_main_name = MainList.get(i);
                    String for_id = IdList.get(i);
                    Rec_Img recimg = new Rec_Img(for_id,real_img_url,for_img_name,for_main_name);
                    RecList.add(recimg);
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0; i<jsonSize; i++) {
                            recImgAdapter.add(RecList.get(i));
                        }
                        recImgAdapter.notifyDataSetChanged();

                    }
                });


            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void SendByHttp() {

        OkHttpClient client = new OkHttpClient();

        String url = "http://wkdgusdn3.dothome.co.kr/main_info.php";


        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            getJSON = response.body().string();

            JSONObject json = new JSONObject(getJSON);
            JSONArray jArr = json.getJSONArray("market");
            jsonSize = jArr.length();

            for(int i=0; i<jsonSize; i++) {
                json = jArr.getJSONObject(i);
                ImgUrlList.add(json.getString("img_url"));
                IdList.add(json.getString("id"));
                temp = json.getString("name");
                temp = URLDecoder.decode(temp, "utf-8");
                NameList.add(temp);
                temp = json.getString("main_sale");
                temp = URLDecoder.decode(temp,"utf-8");
                MainList.add(temp);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void println(final Rec_Img data) {
        handler.post(new Runnable() {
            @Override
            public void run() {

                recImgAdapter.add(data);

                recImgAdapter.notifyDataSetChanged();
            }
        });
    }
}