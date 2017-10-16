package commin.pro.lectureschedule.page.lecture;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;


import java.util.ArrayList;

import commin.pro.lectureschedule.R;
import commin.pro.lectureschedule.model.Model2Lecture;
import commin.pro.lectureschedule.util.UtilCheck;
import commin.pro.lectureschedule.util.UtilShare;

/**
 * Created by user on 2016-12-15.
 */
public class Adapter2GridContent extends ArrayAdapter<Model2Lecture> {

    private Context context;
    private int resource;
    private ArrayList item;
    public SharedPreferences.Editor editor;
    public SharedPreferences sharedPreferences;
    public GridView gridView;

    public Adapter2GridContent(Context context, int resource, GridView gridView, ArrayList<Model2Lecture> item) {
        super(context, resource, item);
        this.context = context;
        this.resource = resource;
        this.gridView = gridView;
        this.item = item;
    }

    class ViewHolder {
        TextView tv_item_description = null;
        TextView tv_item_start_time = null;
        TextView tv_item_end_time = null;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        final Model2Lecture model = (Model2Lecture) item.get(position);
        int view_height = 0;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);

            //셋팅정보를 sharedPreferences를 통해 가져와 Time Value에 따라 view의 크기를 조절합니다.
            sharedPreferences = UtilShare.getSharedPreferences(UtilShare.SAHRE_STATUS, context);
            editor = UtilShare.getEditor(sharedPreferences);
            int time_resource = sharedPreferences.getInt(UtilShare.KEY_VALUE_TIME_RESOURCE, R.array.time_08_18);
            String[] arr_string_time = context.getResources().getStringArray(time_resource);
            view_height = gridView.getHeight() / arr_string_time.length;
            view.setMinimumHeight(view_height);
            ViewHolder viewHolder = new ViewHolder();

            //자원 할당.
            viewHolder.tv_item_description = (TextView) view.findViewById(R.id.tv_item_description);
            viewHolder.tv_item_start_time = (TextView) view.findViewById(R.id.tv_item_start_time);
            viewHolder.tv_item_end_time = (TextView) view.findViewById(R.id.tv_item_end_time);
            viewHolder.tv_item_end_time.setSelected(true);
            viewHolder.tv_item_start_time.setSelected(true);

            view.setTag(viewHolder);
        }

        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        if (model.isData() == true) {//시간영역인지 작업영역인지
            if (model.isMemo()) {//메모데이터가 담긴 모델인지 아닌지
                viewHolder.tv_item_description.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                viewHolder.tv_item_description.setTextSize(12.0f);
                viewHolder.tv_item_description.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                viewHolder.tv_item_description.setSingleLine();
                viewHolder.tv_item_description.setSelected(true);
                view.setBackgroundColor(context.getResources().getColor(R.color.colorGrayD4));

                viewHolder.tv_item_description.setText(model.getMemo_title());
            } else if (model.isEvents()) {//강의데이터가 담긴 모델인지 아닌지
                viewHolder.tv_item_start_time.setTextColor(context.getResources().getColor(R.color.colorWhiteFA));
                viewHolder.tv_item_description.setTextColor(context.getResources().getColor(R.color.colorWhiteFA));
                viewHolder.tv_item_end_time.setTextColor(context.getResources().getColor(R.color.colorWhiteFA));
                if (model.getPosition().equals("top")) {
                    if (UtilCheck.checkPeriod(model.getStart_time(), model.getEnd_time()) <= 1) {
                        viewHolder.tv_item_start_time.setText(UtilCheck.checkTime(model.getStart_time()) + " " + model.getStart_time() + " : 00");
                        viewHolder.tv_item_description.setText(
                                model.getClass_name() + "\n" +
                                        model.getClassroom_name() + "\n");
                        viewHolder.tv_item_end_time.setText(UtilCheck.checkTime(model.getEnd_time()) + " " + model.getEnd_time() + " : 00" + "\n");
                    } else {
                        viewHolder.tv_item_start_time.setText(UtilCheck.checkTime(model.getStart_time()) + " " + model.getStart_time() + " : 00");
                        viewHolder.tv_item_description.setText(model.getClass_name() + "\n" +
                                model.getClassroom_name() + "\n"

                        );
                    }
                }
                if (model.getPosition().equals("bottom")) {
                    viewHolder.tv_item_description.setText(model.getProfessor_name());
                    viewHolder.tv_item_end_time.setText(UtilCheck.checkTime(model.getEnd_time()) + " " + model.getEnd_time() + " : 00" + "\n");
                }

                switch (UtilCheck.checkDay(model.getId())) {//이 영역은 월 화 수 목 금 토 일 영역의 바탕 색을 지정.
                    case "월":
                        view.setBackgroundColor(context.getResources().getColor(R.color.colorWhiteBlue));
                        break;
                    case "화":
                        view.setBackgroundColor(context.getResources().getColor(R.color.colorBrown));
                        break;
                    case "수":
                        view.setBackgroundColor(context.getResources().getColor(R.color.colorDarkOrange));
                        break;
                    case "목":
                        view.setBackgroundColor(context.getResources().getColor(R.color.colorRed36));
                        break;
                    case "금":
                        view.setBackgroundColor(context.getResources().getColor(R.color.colorWhiteBlue));
                        break;
                    case "토":
                        view.setBackgroundColor(context.getResources().getColor(R.color.colorBrown));
                        break;
                    case "일":
                        view.setBackgroundColor(context.getResources().getColor(R.color.colorDarkOrange));
                        break;
                }

            }
        } else {
            viewHolder.tv_item_start_time.setText(model.getName_value());
        }
        return view;
    }

}
