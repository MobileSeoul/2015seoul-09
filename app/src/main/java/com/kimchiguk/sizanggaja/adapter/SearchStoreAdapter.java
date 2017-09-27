package com.kimchiguk.sizanggaja.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kimchiguk.sizanggaja.R;
import com.kimchiguk.sizanggaja.model.NoStore;
import com.kimchiguk.sizanggaja.model.StoreForm;

import java.net.URLDecoder;
import java.util.ArrayList;

public class SearchStoreAdapter extends BaseAdapter {
    Context context;
    ArrayList<Object> storeForms = new ArrayList<Object>();

    public SearchStoreAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return storeForms.size();
    }

    @Override
    public Object getItem(int position) {
        return storeForms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        StoreHolder storeHolder;
        NoStoreHolder noStoreHolder;

        if(storeForms.get(position) instanceof StoreForm) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_search_store, parent, false);

                storeHolder = new StoreHolder();
                storeHolder.image = (ImageView) convertView.findViewById(R.id.itemSearchStore_image);
                storeHolder.name = (TextView) convertView.findViewById(R.id.itemSearchStore_storeName);
                storeHolder.type = (TextView) convertView.findViewById(R.id.itemSearchStore_type);

                convertView.setTag(storeHolder);
            } else {
                storeHolder = (StoreHolder) convertView.getTag();
            }

            StoreForm storeForm = (StoreForm)storeForms.get(position);

            try {
                storeHolder.name.setText(URLDecoder.decode(storeForm.getName(), "utf-8"));
                storeHolder.type.setText(URLDecoder.decode(storeForm.getMain_sale(), "utf-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            Glide.with(context)
                    .load("http://" + storeForm.getMain_img())
                    .into(storeHolder.image);
        } else {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_search_no_store, parent, false);

                noStoreHolder= new NoStoreHolder();

                noStoreHolder.searchResult = (TextView)convertView.findViewById(R.id.itemSearchNoStore_searchResult);

                convertView.setTag(noStoreHolder);
            } else {
                noStoreHolder = (NoStoreHolder) convertView.getTag();
            }

            NoStore noStore = (NoStore)storeForms.get(position);
            String searchResult = noStore.getKeyWord();

            noStoreHolder.searchResult.setText("\"" + searchResult + "\"를 검색한 결과가 없습니다.");
        }
        return convertView;
    }

    public void addStore(StoreForm storeForm) {
        storeForms.add(storeForm);
    }

    public void addNoStore(NoStore noStore) {
        storeForms.add(noStore);
    }

    class StoreHolder {
        RelativeLayout layout;
        ImageView image;
        TextView name;
        TextView type;
    }

    class NoStoreHolder {
        TextView searchResult;
    }
}
