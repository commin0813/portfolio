package com.commin.pro.gangwon.page.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.commin.pro.gangwon.R;
import com.commin.pro.gangwon.model.Model2News;

import java.util.ArrayList;

public class ArrayAdapter2News extends ArrayAdapter<Model2News> {
    private Context context;
    private int resource;
    private ArrayList<Model2News> items;

    public ArrayAdapter2News(Context context, int resource, ArrayList<Model2News> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
    }

    class ViewHolder {
        TextView tv_item_news_title = null;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        final Model2News model = items.get(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tv_item_news_title = (TextView) view.findViewById(R.id.tv_item_news_title);

            view.setTag(viewHolder);
        }

        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.tv_item_news_title.setText(model.getTitle());

        return view;
    }
}
