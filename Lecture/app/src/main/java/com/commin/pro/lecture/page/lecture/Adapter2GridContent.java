package com.commin.pro.lecture.page.lecture;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.commin.pro.lecture.R;
import com.commin.pro.lecture.model.Model2Course;
import com.commin.pro.lecture.util.UtilCheck;


import java.util.ArrayList;


public class Adapter2GridContent extends ArrayAdapter<Model2Course> {

    private Context context;
    private int resource;
    private ArrayList item;
    public SharedPreferences.Editor editor;
    public SharedPreferences sharedPreferences;
    public GridView gridView;

    public Adapter2GridContent(Context context, int resource, GridView gridView, ArrayList<Model2Course> item) {
        super(context, resource, item);
        this.context = context;
        this.resource = resource;
        this.gridView = gridView;
        this.item = item;
    }

    class ViewHolder {
        TextView tv_item_description = null;
        TextView tv_item_start = null;
        TextView tv_item_end = null;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        final Model2Course model = (Model2Course) item.get(position);
        int view_height = 0;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);

            int time_resource = R.array.time;
            String[] arr_string_time = context.getResources().getStringArray(time_resource);

            view_height = gridView.getHeight() / arr_string_time.length;
            view.setMinimumHeight(view_height);
            ViewHolder viewHolder = new ViewHolder();

            //자원 할당.
            viewHolder.tv_item_description = (TextView) view.findViewById(R.id.tv_item_description);
            viewHolder.tv_item_start = (TextView) view.findViewById(R.id.tv_item_start);
            viewHolder.tv_item_end = (TextView) view.findViewById(R.id.tv_item_end);
            viewHolder.tv_item_end.setSelected(true);
            viewHolder.tv_item_start.setSelected(true);

            view.setTag(viewHolder);
        }

        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        if (model.isData() == true){
            if(model.isLecture()){
                viewHolder.tv_item_start.setText(model.getCourseName() + "");
                viewHolder.tv_item_description.setText(model.getCourseProfessor() + "");
                viewHolder.tv_item_end.setText(model.getCourseRoom() + "");

                view.setBackgroundColor(context.getResources().getColor(model.getBackgroundColor()));

            }else {
                viewHolder.tv_item_start.setText("    ");
                viewHolder.tv_item_description.setText("    ");
                viewHolder.tv_item_end.setText("    ");
                view.setBackgroundColor(context.getResources().getColor(R.color.colorWhiteFA));
            }
        }else {
            String[] arr_string_lecture_time = context.getResources().getStringArray(R.array.time_title);
            viewHolder.tv_item_start.setText(arr_string_lecture_time[UtilCheck.checkTimeNameNumber(model.getId())]);
            viewHolder.tv_item_description.setText(model.getName_value());
            viewHolder.tv_item_end.setText("    ");
            view.setBackgroundColor(context.getResources().getColor(R.color.colorWhiteFA));
        }
        return view;
    }

}
