package com.kimchiguk.sizanggaja.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kimchiguk.sizanggaja.R;

import java.util.ArrayList;

public class KeyWordAdapter extends BaseAdapter {
    Context context;
    ArrayList<Object> keyWords = new ArrayList<Object>();

    public KeyWordAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return keyWords.size();
    }

    @Override
    public Object getItem(int position) {
        return keyWords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        KeyWordHolder keyWordHolder;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_keyword, parent, false);

                keyWordHolder = new KeyWordHolder();
                keyWordHolder.keyWord = (TextView) convertView.findViewById(R.id.itemKeyword_keyWord);

                convertView.setTag(keyWordHolder);
            } else {
                keyWordHolder = (KeyWordHolder) convertView.getTag();
            }

            keyWordHolder.keyWord.setText((String)keyWords.get(position));

        return convertView;
    }

    public void addKeyWord(String keyWord) {
        keyWords.add(keyWord);
    }

    class KeyWordHolder {
        TextView keyWord;
    }
}
