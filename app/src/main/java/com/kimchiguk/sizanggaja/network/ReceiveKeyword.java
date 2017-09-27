package com.kimchiguk.sizanggaja.network;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.kimchiguk.sizanggaja.adapter.KeyWordAdapter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by HyunWoo on 2015-10-28.
 */
public class ReceiveKeyword extends AsyncTask<String, Integer, String> {

    Context context;
    KeyWordAdapter keyWordAdapter;
    String json;
    ListView listView_keyWord;

    public ReceiveKeyword(Context context, ListView listView_keyWord) {
        this.context = context;
        this.listView_keyWord = listView_keyWord;
    }

    @Override
    protected String doInBackground(String... urls) {

        keyWordAdapter = new KeyWordAdapter(context);


        String url = "http://wkdgusdn3.dothome.co.kr/recommend.php";

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
            JSONArray recInfoArray = jsonObject.getJSONArray("rec_info");

            for(int i=0; i<recInfoArray.length(); i++) {
                JSONObject recInfoObject = recInfoArray.getJSONObject(i);
                keyWordAdapter.addKeyWord(recInfoObject.getString("tag"));

            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        listView_keyWord.setAdapter(keyWordAdapter);
        setListViewHeightBasedOnChildren(listView_keyWord);
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