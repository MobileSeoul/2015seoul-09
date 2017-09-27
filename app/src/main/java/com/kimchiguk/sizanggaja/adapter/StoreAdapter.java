package com.kimchiguk.sizanggaja.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kimchiguk.sizanggaja.DBContactHelper;
import com.kimchiguk.sizanggaja.R;
import com.kimchiguk.sizanggaja.model.StoreForm;

import java.util.ArrayList;

/**
 * Created by MinJae on 2015-10-26.
 */
public class StoreAdapter extends BaseAdapter{
    private StoreForm store_form;
    private LayoutInflater inflater;
    private int isClicked=0;

    private String databaseName="sizang.db";
    private SQLiteDatabase database;
    private String tableName="sizang";
    private View.OnClickListener mOnClickListener = null;
    private ArrayList<Integer> intList;

    private ArrayList<StoreForm> List_Data;

    public StoreAdapter(Context context) {
        super();
        inflater = LayoutInflater.from(context);
        List_Data = new ArrayList<StoreForm>();


    }

    @Override
    public int getCount() {
        return List_Data.size();
    }

    @Override
    public StoreForm getItem(int position) {
        return List_Data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final Context context = parent.getContext();

        final DBContactHelper db = new DBContactHelper(context);


        if(convertView == null) {

            convertView = inflater.inflate(R.layout.item_store,null);

            holder = new ViewHolder();
            holder.image=(ImageView)convertView.findViewById(R.id.itemStore_image);
            holder.name=(TextView)convertView.findViewById(R.id.itemStore_name);
            holder.description=(TextView)convertView.findViewById(R.id.itemStore_description);
            holder.favorite=(ImageView)convertView.findViewById(R.id.itemStore_favorite);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        store_form = getItem(position);

        if(store_form != null) {

            //    holder.pic_img.setImageDrawable(context.getResources().getDrawable(R.drawable.list_heart_or));
                Glide.with(context).load(store_form.getMain_img()).into(holder.image);

                if(db.preventDup(List_Data.get(position))==false) {
                    holder.favorite.setBackground(context.getResources().getDrawable(R.drawable.list_heart_gr));
                } else {
                    holder.favorite.setBackground(context.getResources().getDrawable(R.drawable.list_heart_or));
                }

                holder.name.setText(store_form.getName());
                holder.description.setText(store_form.getMain_sale());

                //holder.pic_img2.setTag(List_Data.get(position));
               // holder.pic_img2.setOnClickListener(mOnClickListener);
        }


        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.preventDup(List_Data.get(position)) == true) {
                    holder.favorite.setBackground(context.getResources().getDrawable(R.drawable.list_heart_gr));
                    db.deleteMenu(List_Data.get(position));
                } else {
                    holder.favorite.setBackground(context.getResources().getDrawable(R.drawable.list_heart_or));
                    db.addContact(List_Data.get(position));
                }


                /*
                if(intList == null) {
                    intList = new ArrayList<Integer>();
                    intList.add(0);
                } else {
                    if(position+1>intList.size()) {
                        for(int i=position-intList.size()+1;i>0;i--) {
                            intList.add(0);
                        }
                    }
                }

                if(intList.get(position) == 0 ) {
                    holder.pic_img2.setImageDrawable(context.getResources().getDrawable(R.drawable.list_heart_or));
                    intList.set(position, 1);
                    db.addContact(List_Data.get(position));
                } else {
                    holder.pic_img2.setImageDrawable(context.getResources().getDrawable(R.drawable.list_heart_gr));
                    intList.set(position, 0);
                    db.deleteMenu(List_Data.get(position));

                }*/

            }
        });

        return convertView;
    }

    public void add(StoreForm store_form) { List_Data.add(store_form);}

    private class ViewHolder {
        public ImageView image;
        public ImageView favorite;
        public TextView name;
        public TextView description;
    }
}
