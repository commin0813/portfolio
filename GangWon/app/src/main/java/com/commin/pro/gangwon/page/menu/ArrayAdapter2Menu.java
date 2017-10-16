package com.commin.pro.gangwon.page.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.commin.pro.gangwon.R;

import java.util.ArrayList;

/**
 * Created by user on 2017-09-12.
 */

public class ArrayAdapter2Menu extends ArrayAdapter<String> {
    private Context context;
    private int resource;
    private ArrayList<String> items;

    public ArrayAdapter2Menu(Context context, int resource, ArrayList<String> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
    }

    class ViewHolder {
        TextView tv_item_menu_child_name = null;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        final ArrayList<String> model = items;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, null);

            ArrayAdapter2Menu.ViewHolder viewHolder = new ArrayAdapter2Menu.ViewHolder();
            viewHolder.tv_item_menu_child_name = (TextView) view.findViewById(R.id.tv_item_menu_child_name);

            view.setTag(viewHolder);
        }

        final ArrayAdapter2Menu.ViewHolder viewHolder = (ArrayAdapter2Menu.ViewHolder) view.getTag();

        viewHolder.tv_item_menu_child_name.setText(model.get(position));

        return view;
    }
}
