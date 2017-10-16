package commin.pro.lectureschedule.model;

import java.io.Serializable;

/**
 * Created by user on 2016-12-13.
 */
public class Model2Lecture implements Serializable {
    /*
    Common Datas
     */
    private boolean isData;
    private boolean isEvents;
    private String name_value;
    private String id;
    private int groupid;

    /*
    Lecture Datas..
     */
    private String position;
    private int row_index;
    private int column_index;
    private String class_name;
    private String professor_name;
    private String classroom_name;
    private String start_time;
    private String end_time;

    /*
    Memo Datas..
     */
    private boolean isMemo;
    private String memo_title;
    private String memo;


    public boolean isData() {
        return isData;
    }

    public void setData(boolean data) {
        isData = data;
    }

    public String getName_value() {
        return name_value;
    }

    public void setName_value(String name_value) {
        this.name_value = name_value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getProfessor_name() {
        return professor_name;
    }

    public void setProfessor_name(String professor_name) {
        this.professor_name = professor_name;
    }

    public String getClassroom_name() {
        return classroom_name;
    }

    public void setClassroom_name(String classroom_name) {
        this.classroom_name = classroom_name;
    }

    public int getRow_index() {
        return row_index;
    }

    public void setRow_index(int row_index) {
        this.row_index = row_index;
    }

    public int getColumn_index() {
        return column_index;
    }

    public void setColumn_index(int column_index) {
        this.column_index = column_index;
    }

    public boolean isEvents() {
        return isEvents;
    }

    public void setEvents(boolean events) {
        isEvents = events;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public boolean isMemo() {
        return isMemo;
    }

    public void setMemo(boolean memo) {
        isMemo = memo;
    }

    public String getMemo_title() {
        return memo_title;
    }

    public void setMemo_title(String memo_title) {
        this.memo_title = memo_title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
