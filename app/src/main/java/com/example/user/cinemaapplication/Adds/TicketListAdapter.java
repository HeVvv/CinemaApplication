package com.example.user.cinemaapplication.Adds;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.cinemaapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TicketListAdapter extends  ArrayAdapter<String>{
    private final Activity context;
    private final List<String> info;

    public TicketListAdapter(Activity context, List<String> info){
        super(context,R.layout.list_text, info);
        this.context = context;
        this.info = info;
    }

    static class ViewHolder {
        public ImageView imageView;
        public TextView mainInfo;
        public TextView time;
        public TextView additionalinfo;
        public TextView glassesCount;
    }

    public List<String> contentString(String s){
        List<String> info = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(s, "|");
        while(st.hasMoreTokens()){
            info.add(st.nextToken());
        }
        return info;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable String item) {
        return super.getPosition(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ViewHolder буферизирует оценку различных полей шаблона элемента

        ViewHolder holder;
        // Очищает сущетсвующий шаблон, если параметр задан
        // Работает только если базовый шаблон для всех классов один и тот же
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_item_layout, null, true);
            rowView.setBackgroundColor(Color.argb(55,51,51,51));
            holder = new ViewHolder();

            holder.mainInfo = (TextView) rowView.findViewById(R.id.mainInfo);
            holder.additionalinfo = (TextView) rowView.findViewById(R.id.additionalInfo);
            holder.time = (TextView) rowView.findViewById(R.id.time);
            holder.glassesCount = (TextView) rowView.findViewById(R.id.imageView);
            holder.imageView = (ImageView) rowView.findViewById(R.id.responseImage);

            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        String toParse = info.get(position);
        List<String> items = contentString(toParse);
        if(!items.isEmpty()) {
            if(items.size() == 4){
                holder.mainInfo.setText(items.get(1));
                holder.additionalinfo.setText(items.get(2));
                holder.glassesCount.setText(" ");
                holder.time.setText(items.get(3));
            }
            if(items.size() == 5){

                SpannableString str = new SpannableString(items.get(3));
                str.setSpan(new StyleSpan(Typeface.BOLD),0,str.length(),0);

                holder.mainInfo.setText(items.get(1));
                holder.additionalinfo.setText(items.get(2));
                holder.glassesCount.setText(str);
                holder.time.setText(items.get(4));
            }
        }

        String s = info.get(position);
        if(s.startsWith("0")){
            holder.imageView.setImageResource(R.drawable.cancel);
        }
        if(s.startsWith("1")){
            holder.imageView.setImageResource(R.drawable.accept);
        }
        if(s.startsWith("2")){
            holder.imageView.setImageResource(R.drawable.exclam);
        }
        if(s.startsWith("3")){
            holder.imageView.setImageResource(R.drawable.glasses);
        }
        return rowView;
    }
}
