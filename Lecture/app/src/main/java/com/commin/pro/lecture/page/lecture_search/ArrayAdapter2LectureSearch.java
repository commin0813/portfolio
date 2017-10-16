package com.commin.pro.lecture.page.lecture_search;

import android.content.Context;
import android.content.DialogInterface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.TextView;


import com.commin.pro.lecture.R;
import com.commin.pro.lecture.model.Model2Course;
import com.commin.pro.lecture.model.Model2LectureTime;
import com.commin.pro.lecture.page.ApplicationProperty;
import com.commin.pro.lecture.util.DBException;

import com.commin.pro.lecture.util.UtilDialog;
import com.commin.pro.lecture.util.UtilTime;

import java.util.ArrayList;


public class ArrayAdapter2LectureSearch extends ArrayAdapter<Model2Course> {
    private Context context;
    private int resource;
    private ArrayList<Model2Course> items;
    private Page2LectureSearchAdvisor advisor = null;


    public ArrayAdapter2LectureSearch(Context context, int resource, ArrayList<Model2Course> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
    }

    class ViewHolder {
        TextView tv_courseID = null;
        TextView tv_courseName = null;
        TextView tv_courseProfessor = null;
        TextView tv_courseTime = null;
        Button btn_item_save = null;
    }


    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        final Model2Course model = items.get(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tv_courseID = (TextView) view.findViewById(R.id.tv_courseID);
            viewHolder.tv_courseName = (TextView) view.findViewById(R.id.tv_courseName);
            viewHolder.tv_courseProfessor = (TextView) view.findViewById(R.id.tv_courseProfessor);
            viewHolder.tv_courseTime = (TextView) view.findViewById(R.id.tv_courseTime);
            viewHolder.btn_item_save = (Button) view.findViewById(R.id.btn_item_save);

            view.setTag(viewHolder);
        }

        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.tv_courseID.setText(model.getCourseID());
        viewHolder.tv_courseName.setText(model.getCourseName());
        viewHolder.tv_courseProfessor.setText(model.getCourseProfessor());
        viewHolder.tv_courseTime.setText(model.getCourseTime());

        viewHolder.btn_item_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    advisor = new Page2LectureSearchAdvisor();

                    ArrayList<Model2Course> registered_list = ApplicationProperty.getRegisteredList();
                    if (registered_list.size() != 0) {
                        for (Model2Course model2 : registered_list) {
                            /************************************
                             * 시간표 중복을 확인하는 부분입니다.
                             * 일단 등록하려는 강의모델과 등록되어있는
                             * 강의모델 중에 요일을 먼저 비교해서 같은 요일인 모델들을 다시 확인합니다.
                             * 이번엔 A1~14A까지의 이름을 서로 비교합니다.
                             * 결과적으로 같은 요일인데 시간 이름까지 같으면 시간이 겹치므로
                             * 디비에 저장하지않고 Exception을 발생시켜 다이얼로그를 띄우도록 합니다.
                             * */
                            ArrayList<Model2LectureTime> time_items = UtilTime.getTimeValue(model.getCourseTime());
                            for (Model2LectureTime time_model : time_items) {
                                if (time_model.getDay().equalsIgnoreCase(model2.getDay_name())) {
                                    for (String lecture_time : time_model.getLecture_times()) {
                                        if (lecture_time.equalsIgnoreCase(model2.getTime_name())) {
                                            throw new DBException("시간이 중복 됩니다.");
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // 중복되는 시간이없으면, 디비에 insert시킵니다.
                    advisor.insertCourse(model);
                    advisor.setLectureData(ApplicationProperty.model2User.getUser_id());
                    registered_list.add(model); //등록 성공

                    UtilDialog.openDialog((Page2LectureSearch) context, "등록 하였습니다.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    UtilDialog.openError((Page2LectureSearch) context, e.getMessage(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                }
            }
        });

        return view;
    }
}
