package com.commin.pro.exerciseproject.page.menu_list;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.commin.pro.exerciseproject.R;
import com.commin.pro.exerciseproject.model.Model2Menu;

import java.util.ArrayList;


/**
 * Created by user on 2016-11-28.
 */
public class Adapter2MenuList extends ArrayAdapter<Model2Menu> {
    private ArrayList<Model2Menu> items;
    private Context context;


    public Adapter2MenuList(Context context, int resource, ArrayList<Model2Menu> items) {
        super(context, resource, items);
        this.items = items;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.page_list_item, null);
        }
        final Model2Menu model = items.get(position);
        TextView textView = (TextView) view.findViewById(R.id.tv_list_menu_name_item);
        final ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon_item);

        textView.setText(model.getMenu_text());
        imageView.setImageDrawable(model.getIcon());

        return view;
    }


}
