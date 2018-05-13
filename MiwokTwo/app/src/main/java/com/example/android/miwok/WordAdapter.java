package com.example.android.miwok;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by karolgrzesiak on 17.07.17.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    private int mColor;

    public WordAdapter(Context context, ArrayList<Word> words, int color){
        super(context,0,words);
        mColor = color;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Word currentWord = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        TextView english = (TextView) convertView.findViewById(R.id.english_word);
        TextView miwok = (TextView) convertView.findViewById(R.id.miwok_word);
        ImageView image = (ImageView) convertView.findViewById(R.id.icon_image_view);

        english.setText(currentWord.getEnglishTranslation());
        miwok.setText(currentWord.getMiwokTranslation());

        if (currentWord.hasImage()) {
            image.setImageResource(currentWord.getImageResourceId());
            image.setVisibility(View.VISIBLE);
        }
        else{
            image.setVisibility(View.GONE);
        }
        LinearLayout textViewsLinearLayout = (LinearLayout) convertView.findViewById(R.id.textviews_linear_layout);
        textViewsLinearLayout.setBackgroundColor(ContextCompat.getColor(getContext(),mColor));
        return convertView;
    }
}
