package com.kimchiguk.sizanggaja.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kimchiguk.sizanggaja.CustomDialog;
import com.kimchiguk.sizanggaja.DBContactHelper;
import com.kimchiguk.sizanggaja.R;
import com.kimchiguk.sizanggaja.model.StoreForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinJae on 2015-10-26.
 */
public class MyAdapter extends BaseAdapter{

    private StoreForm store_form;
    private LayoutInflater inflater;
    private int isClicked=0;
    private ListView listView;
    MyAdapter myAdapter;
    TextView textview;
    Button no_button;
    List<StoreForm> menu_list;


    private String databaseName="sizang.db";
    private SQLiteDatabase database;
    private String tableName="sizang";
    private View.OnClickListener mOnClickListener = null;
    private ArrayList<Integer> intList;

    private TextView dialog_textView;

    ViewHolder holder;
    Context context;

    DBContactHelper db;
    int number;



    private CustomDialog mCustomDialog;

    private ArrayList<StoreForm> List_Data;

    public MyAdapter(Context context, TextView textView, Button button) {
        super();
        //this.listView = listView;
        no_button = button;
        textview = textView;
        inflater = LayoutInflater.from(context);
        List_Data = new ArrayList<StoreForm>();
        this.myAdapter = this;



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
        //final ViewHolder holder;
        //final Context context = parent.getContext();

        //final DBContactHelper db = new DBContactHelper(context);


        context = parent.getContext();

        db = new DBContactHelper(context);

        if(convertView == null) {

            convertView = inflater.inflate(R.layout.item_store,null);



            holder = new ViewHolder();
            holder.pic_img=(ImageView)convertView.findViewById(R.id.itemStore_image);
            holder.textView1=(TextView)convertView.findViewById(R.id.itemStore_name);
            holder.textView2=(TextView)convertView.findViewById(R.id.itemStore_description);
            holder.pic_img2=(ImageView)convertView.findViewById(R.id.itemStore_favorite);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        store_form = getItem(position);
        number = position;

        if(store_form != null) {

            //    holder.pic_img.setImageDrawable(context.getResources().getDrawable(R.drawable.list_heart_or));
            Glide.with(context).load(store_form.getMain_img()).into(holder.pic_img);
            holder.pic_img2.setImageDrawable(context.getResources().getDrawable(R.drawable.list_heart_or));
            holder.textView1.setText(store_form.getName());
            holder.textView2.setText(store_form.getMain_sale());

            //holder.pic_img2.setTag(List_Data.get(position));
            // holder.pic_img2.setOnClickListener(mOnClickListener);
        }


        holder.pic_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomDialog = new CustomDialog(context, leftListener, rightListener);
                mCustomDialog.show();

                holder.pic_img2.setBackground(context.getResources().getDrawable(R.drawable.list_heart_gr));
            }
        });
        return convertView;
    }

    private View.OnClickListener leftListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mCustomDialog.dismiss();

        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            holder.pic_img2.setBackground(context.getResources().getDrawable(R.drawable.list_heart_gr));
            db.deleteMenu(List_Data.get(number));

            List_Data.remove(number);
            notifyDataSetChanged();

            menu_list = db.getAllMenu();
            int size = menu_list.size();

            if(size == 0) {
                no_button.setVisibility(View.GONE);
                textview.setVisibility(View.VISIBLE);
            }

            mCustomDialog.dismiss();
        }
    };



    public void add(StoreForm store_form) { List_Data.add(store_form);}
    public void removeAll() {List_Data.clear();}

    private class ViewHolder {
        public ImageView pic_img;
        public ImageView pic_img2;
        public TextView textView1;
        public TextView textView2;

    }


}
