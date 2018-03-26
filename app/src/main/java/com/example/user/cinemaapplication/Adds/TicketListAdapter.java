package com.example.user.cinemaapplication.Adds;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.cinemaapplication.R;

import java.util.List;
import java.util.Set;

public class TicketListAdapter extends  ArrayAdapter<String>{
    private final Activity context;
    private final List<String> info;

    public TicketListAdapter(Activity context, List<String> info){
        super(context,R.layout.list_text, info);
        this.context = context;
        this.info = info;
    }

    static class ViewHolder {
        //public ImageView imageView;
        public TextView textView;
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
            rowView = inflater.inflate(R.layout.list_text, null, true);
            holder = new ViewHolder();
            holder.textView = (TextView) rowView.findViewById(R.id.label);
           //holder.imageView = (ImageView) rowView.findViewById(R.id.icon);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        holder.textView.setText(info.get(position));
//        String s = info.get(position);
//        if(s.endsWith("3")){
//            holder.imageView.setImageResource(R.mipmap.active);
//        }
//        if(s.endsWith("5")){
//            holder.imageView.setImageResource(R.mipmap.sold);
//        }
//        if(s.endsWith("7")){
//            holder.imageView.setImageResource(R.mipmap.reserved);
//        }
        return rowView;
    }
}
