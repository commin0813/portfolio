package commin.pro.lectureschedule.page.lecture;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import commin.pro.lectureschedule.R;

/**
 * Created by user on 2016-12-15.
 */
public class Adapter2GridDay extends ArrayAdapter<String> {
    Context context;
    LayoutInflater inflater;
    ArrayList item;

    public Adapter2GridDay(Context context, int resource, ArrayList<String> item) {
        super(context, resource, item);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.item = item;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            view = inflater.inflate(R.layout.item_gird_day, parent, false);
        }
        TextView textView = (TextView) view.findViewById(R.id.tv_item_day);
        textView.setText(item.get(position).toString());
        Log.w("Adapter", "///////////" + item.get(position).toString());
        return view;
    }

}
