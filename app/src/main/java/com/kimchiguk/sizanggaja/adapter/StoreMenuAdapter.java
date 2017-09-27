package com.kimchiguk.sizanggaja.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kimchiguk.sizanggaja.R;
import com.kimchiguk.sizanggaja.model.Menu;

import java.util.ArrayList;

/**
 * Created by HyunWoo on 2015-10-31.
 */
public class StoreMenuAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<Menu> mListData = new ArrayList<Menu>();

    public StoreMenuAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_storemenu, parent, false);

            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.itemstoremenu_image);
            holder.name = (TextView) convertView.findViewById(R.id.itemstoremenu_name);
            holder.price = (TextView) convertView.findViewById(R.id.itemstoremenu_price);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Menu menu = mListData.get(position);

        holder.name.setText(menu.getName());
        holder.price.setText(menu.getPrice() + "");
        Glide.with(mContext)
                .load(menu.getURL())
                .into(holder.image);

        return convertView;

    }

    class ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView price;
    }

    public void addItem(String name, int price, String url) {
        Menu addInfo = null;
        addInfo = new Menu(name, price, url);

        mListData.add(addInfo);
    }


}