package com.kimchiguk.sizanggaja.network;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.kimchiguk.sizanggaja.adapter.SearchStoreAdapter;
import com.kimchiguk.sizanggaja.model.NoStore;
import com.kimchiguk.sizanggaja.model.StoreForm;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLDecoder;

/**
 * Created by HyunWoo on 2015-10-28.
 */
public class ReceiveSearchStore extends AsyncTask<String, Integer, String> {

    Context context;
    SearchStoreAdapter searchStoreAdapter;
    String json;
    ListView listView_searchStore;
    String tag;

    public ReceiveSearchStore(Context context, ListView listView_searchStore, String tag) {
        this.context = context;
        this.listView_searchStore = listView_searchStore;
        this.tag = tag;
    }

    @Override
    protected String doInBackground(String... urls) {

        searchStoreAdapter = new SearchStoreAdapter(context);

        String url = "http://wkdgusdn3.dothome.co.kr/tag.php?tag=" + tag;

        OkHttpClient client = new OkHttpClient();

        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            json = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String temp){

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray tagInfoArray = jsonObject.getJSONArray("tag_info");

            if(tagInfoArray.length() == 0) {
                searchStoreAdapter.addNoStore(new NoStore(tag));
            }

            for(int i=0; i<tagInfoArray.length(); i++) {
                JSONObject recInfoObject = tagInfoArray.getJSONObject(i);

                String id = URLDecoder.decode(recInfoObject.getString("id"), "utf-8");
                String name = URLDecoder.decode(recInfoObject.getString("name"), "utf-8");
                String phone_number = URLDecoder.decode(recInfoObject.getString("phone_number"), "utf-8");
                String latitude = URLDecoder.decode(recInfoObject.getString("latitude"), "utf-8");
                String longitude = URLDecoder.decode(recInfoObject.getString("longitude"), "utf-8");
                String blog_url = URLDecoder.decode(recInfoObject.getString("blog_url"), "utf-8");
                String imgUrl = URLDecoder.decode(recInfoObject.getString("main_img"), "utf-8");
                String mainSale = URLDecoder.decode(recInfoObject.getString("main_sale"), "utf-8");

                StoreForm searchStore = new StoreForm(id, name, phone_number,
                        latitude, longitude, blog_url, imgUrl, mainSale);
                searchStoreAdapter.addStore(searchStore);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        listView_searchStore.setAdapter(searchStoreAdapter);
        setListViewHeightBasedOnChildren(listView_searchStore);
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