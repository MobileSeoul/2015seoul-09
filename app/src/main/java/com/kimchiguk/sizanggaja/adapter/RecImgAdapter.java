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
import com.kimchiguk.sizanggaja.model.Rec_Img;

import java.util.ArrayList;

/**
 * Created by MinJae on 2015-10-18.
 */
public class RecImgAdapter extends BaseAdapter {

    private Rec_Img r_img;
    private LayoutInflater inflater;

    private ArrayList<Rec_Img> List_Data;

    public RecImgAdapter(Context context) {
        super();
        inflater = LayoutInflater.from(context);
        List_Data = new ArrayList<Rec_Img>();

    }

    @Override
    public int getCount() {
        return List_Data.size();
    }

    @Override
    public Rec_Img getItem(int position) {
        return List_Data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final Context context = parent.getContext();

        if(convertView == null) {

            convertView = inflater.inflate(R.layout.item_rec_market,null);

            holder = new ViewHolder();
            holder.pic_img=(ImageView)convertView.findViewById(R.id.itemRecMarket_imgae);
            holder.name=(TextView)convertView.findViewById(R.id.itemRecMarket_name);
            holder.mainSale=(TextView)convertView.findViewById(R.id.itemRecMarket_mainSale);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        r_img = getItem(position);

        if(r_img != null) {
            if(r_img.getURL() != null && r_img.getInforStr() != null) {

                Glide.with(context).load(r_img.getURL()).into(holder.pic_img);
                holder.name.setText(r_img.getInforStr());
                holder.mainSale.setText(r_img.getMainsale());
            }

        }
        return convertView;
    }

    public void add(Rec_Img rec_img) { List_Data.add(rec_img);}

    private class ViewHolder {
        public ImageView pic_img;
        public TextView name;
        public TextView mainSale;
    }



}
